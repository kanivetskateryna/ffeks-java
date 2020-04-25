package com.dnu.ffeks.modelingofsystems.distributions;

import java.util.Arrays;

//TODO
public class UniformDistribution implements Distribution<Integer> {

    private Integer experiments;
    private Integer average;
    private DistributionType distributionType;

    public UniformDistribution(Integer experiments) {
        this.experiments = experiments;
        distributionType = DistributionType.UNIFORM;
    }

    @Override
    public Integer[] getDistribution() {
        return generateDistribution();
    }

    @Override
    public DistributionType getDistributionType() {
        return distributionType;
    }

    //Получите n случайных чисел, вычислите их сумму и нормализуйте сумму до 1, разделив каждое число на сумму.
    public Integer[] generateDistribution() {
      return new Integer[0];
    }

    private Double uniformFunction(Double num) {
        return Math.log(num);
    }

    public void setAverage(Integer average) {
        this.average = average;
    }

    public static void main(String[] args) {
        UniformDistribution uniformDistribution = new UniformDistribution(100);
        Arrays.stream(uniformDistribution.generateDistribution()).forEach(System.out::println);
    }
}
