package com.dnu.ffeks.modelingofsystems.distributions;

import com.dnu.ffeks.modelingofsystems.distributions.Distribution;
import com.dnu.ffeks.modelingofsystems.distributions.DistributionType;

public class ExponentialDistribution implements Distribution<Integer> {

    private Integer experiments;
    private Double lambda;
    private DistributionType distributionType;

    public ExponentialDistribution(Integer experiments) {
        this.experiments = experiments;
        distributionType = DistributionType.EXPONENTIAL;
    }

    public DistributionType getDistributionType() {
        return distributionType;
    }

    @Override
    public Integer[] getDistribution() {
        return generateDistribution();
    }

    private Integer[] generateDistribution() {
        Integer[] distribution = new Integer[experiments];
        for (int i = 0; i < experiments; i++) {
            distribution[i] = Math.toIntExact(Math.round(exponentialFunction(Math.random())));
        }
        return distribution;
    }

    private Double exponentialFunction(Double num) {
        return (-(1. / lambda)) * Math.log(num) + 1;
    }

    public void setLambda(Double lambda) {
        this.lambda = lambda;
    }
}
