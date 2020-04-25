package com.dnu.ffeks.modelingofsystems.var11;

import com.dnu.ffeks.modelingofsystems.distributions.ExponentialDistribution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ComputingSystem {

    private final int modelingTime;  // время "прогона" модели
    private int tn; // системное время (время поступления заявок)
    private int totalTime;  // текущее время работы системы
    private int Nout;   // число обработанных заявок

    private Processor processor1;
    private Processor processor2;
    private Processor processor3;

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

    private int currentModelingIteration;

    ComputingSystem(int modelingTime) {
        this.modelingTime = modelingTime;
        prepareInitialization();
    }

    private void prepareInitialization() {
        init();
        generateDistributionsForProcessingDetails();
        tn = getNextSystemTime();
    }

    //заранее используем датчик псевдослучайных чисел с экспоненциальным законом распределения
    //времени прихода/трансляции/редактирования/решения заданий
    private void generateDistributionsForProcessingDetails() {
        int maxDetails = 2000;
        ExponentialDistribution uniformDistribution = new ExponentialDistribution(maxDetails);
        uniformDistribution.setLambda(0.5); // среднее время прихода заданий из источника: ~5 min
        tm.addAll(Arrays.asList(uniformDistribution.getDistribution()));
        uniformDistribution.setLambda(0.5); // среднее время трансляции задания первым процессором: ~5 min
        t11.addAll(Arrays.asList(uniformDistribution.getDistribution()));
        uniformDistribution.setLambda(0.25); // среднее время редактирования задания вторым процессором: ~2.5 min
        t21.addAll(Arrays.asList(uniformDistribution.getDistribution()));
        uniformDistribution.setLambda(0.15); // среднее время решения задания третьим процессором: ~1.5 min
        t31.addAll(Arrays.asList(uniformDistribution.getDistribution()));
    }

    private void init() {
        processor1 = new Processor(Processor.State.FREE);
        processor2 = new Processor(Processor.State.FREE);
        processor3 = new Processor(Processor.State.FREE);
        t11 = new ArrayList<>();
        t21 = new ArrayList<>();
        t31 = new ArrayList<>();
        tm = new ArrayList<>();
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

    void startWork() {
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
        if (processor3.getProcessorState() == Processor.State.FREE) {
            if (zh31 > 0) {
                processor3.setProcessorState(Processor.State.BUSY);
                zh31 -= 3;
            } else {
                return;
            }
        }
        if (t31.get(currentModelingIteration) <= tn) {
            Nout++;     // or Nout += 3;
            processor3.setProcessorState(Processor.State.FREE);
        }
    }

    // блок 5 - переход заявки из 2-й фазы на 3-ю фазу
    private void transferRequestFromPhase2ToPhase3() {
        if (processor2.getProcessorState() == Processor.State.BUSY) {
            if (t21.get(currentModelingIteration) <= tn) {
                if (processor3.getProcessorState() == Processor.State.FREE) {
                    processor3.setProcessorState(Processor.State.BUSY);
                } else {
                    zh31 += 3;
                }
                processor2.setProcessorState(Processor.State.FREE);
            }
        }
    }

    // блок 6 - обслуживание заявки каналом 2-й фазы
    private void processRequestByChannelOnPhase2() {
        if (processor2.getProcessorState() == Processor.State.FREE && zh21 > 0) {
            processor2.setProcessorState(Processor.State.BUSY);
            zh21 -= 2;
        }
    }

    // блок 7 - переход заявки из 1-й фазы на 2-ю фазу
    private void transferRequestFromPhase1ToPhase2() {
        if (processor1.getProcessorState() == Processor.State.BUSY) {
            if (t11.get(currentModelingIteration) <= tn) {
                if (processor2.getProcessorState() == Processor.State.FREE) {
                    processor2.setProcessorState(Processor.State.BUSY);
                } else {
                    zh21 += 2;
                }
                processor1.setProcessorState(Processor.State.FREE);
            }
        }
    }

    // блок 8 - обслуживание заявки каналом 1-й фазы
    private void processRequestByChannelOnPhase1() {
        if (processor1.getProcessorState() == Processor.State.FREE && zh11 > 0) {
            processor1.setProcessorState(Processor.State.BUSY);
            zh11--;
        }
    }

    // блок 9 - поступление заявки на вход системы
    private void supplyRequestOnSystemInput() {
        if (tm.get(currentModelingIteration) <= totalTime) {
            if (processor1.getProcessorState() == Processor.State.FREE) {
                processor1.setProcessorState(Processor.State.BUSY);
            } else {
                zh11++;
            }
        }
    }

    // блок 10 - переход к следующему системному времени
    private void generateTimeForNewRequest() {
        totalTime += tn;
        currentModelingIteration++;
        tn = getNextSystemTime();
    }

    private void outputResults() {
        System.out.println("Total time of modeling: " + totalTime);
        System.out.println("Count of processed details: " + Nout);
        System.out.println("--------------------------------------------");
        System.out.println("State of processor 1: " + processor1.getProcessorState().name());
        System.out.println("State of processor 2: " + processor2.getProcessorState().name());
        System.out.println("State of processor 3: " + processor3.getProcessorState().name());
        System.out.println("--------------------------------------------");
        System.out.println("Count of details in queue 11: " + zh11);
        System.out.println("Count of details in queue 21: " + zh21);
        System.out.println("Count of details in queue 22: " + zh22);
        System.out.println("Count of details in queue 31: " + zh31);
    }
}
