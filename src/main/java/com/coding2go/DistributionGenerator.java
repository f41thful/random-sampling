package com.coding2go;

import com.coding2go.common.RealHelper;
import com.coding2go.exceptions.DistributionException;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class DistributionGenerator {
    private static final RealHelper realHelper = new RealHelper();
    private final int numClasses;

    public DistributionGenerator(int numClasses) {
        if(numClasses <= 0) {
            throw new InvalidParameterException();
        }

        this.numClasses = numClasses;
    }

    public List<Double> generate() {
        List<Double> distribution = new ArrayList<>();

        for(int i = 0; i < numClasses; i++) {
            distribution.add(getDistribution(i));
        }

        check(distribution);

        return distribution;
    }

    /*
        distribution must be given over 1 (not 100)
     */
    double getDistribution(int klass) {
        return ((double) 1) / numClasses;
    }

    private void check(List<Double> distribution) {
        checkDistributionSums1(distribution);
        checkOnlyGreaterOrEqualsThan0(distribution);
    }

    private void checkDistributionSums1(List<Double> distribution) {
        double sum = 0;
        for(Double d : distribution) {
            sum += d;
        }

        if(!realHelper.equals(sum, 1)) {
            throw new DistributionException("The distribution adds up to " + sum + " instead of 1.");
        }
    }

    private void checkOnlyGreaterOrEqualsThan0(List<Double> distribution) {
        for(Double d : distribution) {
            if(d < 0) {
                throw new DistributionException("There are negative values like " + d);
            }
        }
    }
}
