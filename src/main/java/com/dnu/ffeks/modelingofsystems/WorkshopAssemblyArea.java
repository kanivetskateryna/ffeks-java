package com.dnu.ffeks.modelingofsystems;

import com.dnu.ffeks.modelingofsystems.distributions.ExponentialDistribution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class WorkshopAssemblyArea {

    private final int modelingTime;  // время "прогона" модели
    private int tn; // системное время (время поступления заявок)
    private int totalTime;  // текущее время работы системы
    private int Nout;   // число обработанных заявок

    private Channel channel1;
    private Channel channel2;
    private Channel channel3;

    // число заявок в накопителях
    private int zh11;
    private int zh21;
    private int zh22;
    private int zh31;

    private List<Integer> tm; // время поступления заявки из источника
    // время окончания обслуживания каналом заявки
    private List<Integer> t11;
    private List<Integer> t21;
    private List<Integer> t31;
    private List<Integer> tnAll;

    private int timeOfWorkingChannel1;
    private int timeOfWorkingChannel2;
    private int timeOfWorkingChannel3;

    private int currentModelingIteration;
    private Queue<Integer> defectIterations;

    WorkshopAssemblyArea(int modelingTime) {
        this.modelingTime = modelingTime;
        prepareInitialization();
    }

    private void prepareInitialization() {
        init();
        generateAndSortDefectDistribution();
        generateDistributionsForProcessingDetails();
        tn = getNextSystemTime();
        tnAll.add(tn);
    }

    //заранее используем датчик псевдослучайных чисел с экспоненциальным законом распределения
    //времени поступления/обработки/сборки/регулировки деталей
    private void generateDistributionsForProcessingDetails() {
        int maxDetails = 2000;
        ExponentialDistribution exponentialDistribution = new ExponentialDistribution(maxDetails);
        exponentialDistribution.setLambda(0.1); // среднее время прихода заявки из источника: ~10 min
        tm.addAll(Arrays.asList(exponentialDistribution.getDistribution()));
        exponentialDistribution.setLambda(0.7); // среднее время обработки: ~7 min
        t11.addAll(Arrays.asList(exponentialDistribution.getDistribution()));
        exponentialDistribution.setLambda(0.6); // среднее время сборки: ~6 min
        t21.addAll(Arrays.asList(exponentialDistribution.getDistribution()));
        exponentialDistribution.setLambda(0.8); // среднее время регулировки: ~8 min
        t31.addAll(Arrays.asList(exponentialDistribution.getDistribution()));
    }

    private void init() {
        channel1 = new Channel(Channel.State.FREE);
        channel2 = new Channel(Channel.State.FREE);
        channel3 = new Channel(Channel.State.FREE);
        t11 = new ArrayList<>();
        t21 = new ArrayList<>();
        t31 = new ArrayList<>();
        tnAll = new ArrayList<>();
        tm = new ArrayList<>();
        defectIterations = new LinkedList<>();
    }

    // возможность возникновения дефекта: на 216 деталей - 8 будут с дефектом
    private void generateAndSortDefectDistribution() {
        Set<Integer> buffer = IntStream.range(0, 8).mapToObj(i -> new Random().nextInt((217))).collect(Collectors.toCollection(TreeSet::new));
        defectIterations.addAll(buffer);
    }

    // прирост времени при использовании АЦМА по формуле: min (min(tki); min(tm))
    private int getNextSystemTime() {
        return minimum(
                minimum(
                        t11.get(currentModelingIteration),
                        t21.get(currentModelingIteration),
                        t31.get(currentModelingIteration)),
                tm.get(currentModelingIteration));
    }

    private static int minimum(int... nums) {
        return Arrays.stream(nums).min().getAsInt();
    }

    public void startModeling() {
        while (notEndOfModeling()) {
            processRequestByChannelOnPhase3();
            transferRequestFromPhase2ToPhase3();
            processRequestByChannelOnPhase2();
            transferRequestFromPhase1ToPhase2();
            processRequestByChannelOnPhase1();
            supplyRequestOnSystemInput();
            generateTimeForNewRequest();
        }
        outputResults();
    }

    //блок 3 - проверка окончания моделирования
    private boolean notEndOfModeling() {
        return totalTime < modelingTime;
    }

    // блок 4 - обслуживание заявки каналом 3-й фазы
    private void processRequestByChannelOnPhase3() {
        if (channel3.getChannelState() == Channel.State.FREE) {
            if (zh31 > 0) {
                channel3.setChannelState(Channel.State.BUSY);
                zh31--;
            } else {
                return;
            }
        }
        if (t31.get(currentModelingIteration) <= tn) {
            Nout++;
            channel3.setChannelState(Channel.State.FREE);
        }
    }

    // блок 5 - переход заявки из 2-й фазы на 3-ю фазу
    private void transferRequestFromPhase2ToPhase3() {
        if (channel2.getChannelState() == Channel.State.BUSY || channel2.getChannelState() == Channel.State.LOCKED) {
            if (t21.get(currentModelingIteration) <= tn) {
                if (defect4()) {
                    zh11 += 2;
                } else {
                    if (channel3.getChannelState() == Channel.State.FREE) {
                        channel3.setChannelState(Channel.State.BUSY);
                    } else {
                        zh31++;
                    }
                }
                channel2.setChannelState(Channel.State.FREE);
            }
        }
    }

    // true, если на текущей итерации произошел брак
    private boolean defect4() {
        Integer head = defectIterations.peek();
        if (Objects.nonNull(head)
                && head == currentModelingIteration) {
            defectIterations.remove();
            return true;
        }
        return false;
    }

    // блок 6 - обслуживание заявки каналом 2-й фазы
    private void processRequestByChannelOnPhase2() {
        if (channel2.getChannelState() == Channel.State.FREE) {
            int counter = 0;
            if (zh21 > 0) {
                counter++;
                if (counter == 2) {
                    channel2.setChannelState(Channel.State.BUSY);
                    zh21--;
                    zh22--;
                }
            }
            if (zh22 > 0) {
                counter++;
                if (counter == 2) {
                    channel2.setChannelState(Channel.State.BUSY);
                    zh21--;
                    zh22--;
                }
            }
        }
    }

    // блок 7 - переход заявки из 1-й фазы на 2-ю фазу
    private void transferRequestFromPhase1ToPhase2() {
        if ((channel1.getChannelState() == Channel.State.BUSY || channel1.getChannelState() == Channel.State.LOCKED)) {
            if (t11.get(currentModelingIteration) <= tn) {
                if (channel2.getChannelState() == Channel.State.FREE) {
                    if (zh22 > 0) {
                        channel2.setChannelState(Channel.State.BUSY);
                        zh22--;
                    } else {
                        zh21++;
                    }
                } else {
                    zh21++;
                }
                channel1.setChannelState(Channel.State.FREE);
            }
        }
    }

    // блок 8 - обслуживание заявки каналом 1-й фазы
    private void processRequestByChannelOnPhase1() {
        if (channel1.getChannelState() == Channel.State.FREE && zh11 > 0) {
            channel1.setChannelState(Channel.State.BUSY);
            zh11--;
        }
    }

    // блок 9 - поступление заявки на вход системы
    private void supplyRequestOnSystemInput() {
        if (tm.get(currentModelingIteration) <= totalTime) {
            if (channel1.getChannelState() == Channel.State.FREE) {
                channel1.setChannelState(Channel.State.BUSY);
            } else {
                if (gen05().get()) {
                    zh11 += 2;
                    zh22++;
                } else {
                    zh22 += 2;
                    zh11++;
                }
            }
        }
    }

    // блок 10 - переход к следующему системному времени
    private void generateTimeForNewRequest() {
        totalTime += tn;
        currentModelingIteration++;
        tn = getNextSystemTime();
        tnAll.add(tn);
    }

    // true, тогда 2 детали отправляются в Н11 и 1 деталь в Н22
    // false, тогда 2 детали отправляются в Н22 и 1 деталь в Н11
    private Supplier<Boolean> gen05() {
        return () -> Math.random() < 0.5;
    }

    private void outputResults() {
        calculateTimeOfChannelWork();
        System.out.println("============================================");
        System.out.println("|| Total time of modeling: " + totalTime + "            ||");
        System.out.println("|| Count of processed details: " + Nout + "         ||");
        System.out.println("--------------------------------------------");
        System.out.println("|| State of channel 1: " + channel1.getChannelState().name() + "                ||");
        System.out.println("|| State of channel 2: " + channel2.getChannelState().name() + "                ||");
        System.out.println("|| State of channel 3: " + channel3.getChannelState().name() + "                ||");
        System.out.println("--------------------------------------------");
        System.out.println("Channel 1 - time of working: " + timeOfWorkingChannel1 + " min");
        System.out.println("Channel 2 - time of working: " + timeOfWorkingChannel2 + " min");
        System.out.println("Channel 3 - time of working: " + timeOfWorkingChannel3 + " min");
        System.out.println("--------------------------------------------");
        System.out.println("Channel 1 was working " + timeOfChannelWork(timeOfWorkingChannel1) + "%" +
                            " and was idled " + timeOfChannelIdle(timeOfWorkingChannel1) + "%");
        System.out.println("Channel 2 was working " + timeOfChannelWork(timeOfWorkingChannel2) + "%" +
                            " and was idled " + timeOfChannelIdle(timeOfWorkingChannel2) + "%");
        System.out.println("Channel 3 was working " + timeOfChannelWork(timeOfWorkingChannel3) + "%" +
                            " and was idled " + timeOfChannelIdle(timeOfWorkingChannel3) + "%");
        System.out.println("--------------------------------------------");
        System.out.println("|| Count of details in queue 11: " + zh11);
        System.out.println("|| Count of details in queue 21: " + zh21);
        System.out.println("|| Count of details in queue 22: " + zh22);
        System.out.println("|| Count of details in queue 31: " + zh31);
        System.out.println("============================================");
    }

    private void calculateTimeOfChannelWork() {
        Collections.sort(tnAll);
        Collections.sort(t11);
        Collections.sort(t21);
        Collections.sort(t31);

        for (int i = 0; i < currentModelingIteration; i++) {
            if (t11.get(i) <= tnAll.get(i)) {
                timeOfWorkingChannel1 += tnAll.get(i);
            }
            if (t21.get(i) <= tnAll.get(i)) {
                timeOfWorkingChannel2 += tnAll.get(i);
            }
            if (t31.get(i) <= tnAll.get(i)) {
                timeOfWorkingChannel3 += tnAll.get(i);
            }
        }
    }

    private double timeOfChannelWork(int timeOfWorking) {
        return ((double) timeOfWorking / totalTime) * 100.;
    }

    private double timeOfChannelIdle(int timeOfWorking) {
        return ((totalTime - (double) timeOfWorking) / totalTime) * 100.;
    }
}