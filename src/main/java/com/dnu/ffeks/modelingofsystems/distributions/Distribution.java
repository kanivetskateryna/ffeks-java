package com.dnu.ffeks.modelingofsystems.distributions;

public interface Distribution<T extends Number> {

    T[] getDistribution();

    DistributionType getDistributionType();
}
