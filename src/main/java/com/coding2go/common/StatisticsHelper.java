package com.coding2go.common;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StatisticsHelper {
    private DistributionValidation distributionValidation = new DistributionValidation();
    private Mean mean = new Mean();
    private StandardDeviation standardDeviation = new StandardDeviation();

    public static class Statistics {
        private Double mean;
        private Double std;

        public Statistics(Double mean, Double std) {
            this.mean = mean;
            this.std = std;
        }

        public Double getMean() {
            return mean;
        }

        public Double getStd() {
            return std;
        }
    }

    public Statistics statistics(List<Double> valuesCouldHaveNulls) {
        if(valuesCouldHaveNulls.contains(null)) {
            return new Statistics(null, null);
        }

        List<Double> distribution = valuesCouldHaveNulls.stream().filter(Objects::nonNull).collect(Collectors.toList());

        double[] array = new double[distribution.size()];
        for(int i = 0; i < distribution.size(); i++) {
            array[i] = distribution.get(i);
        }

        double meanValue = mean.evaluate(array, 0, array.length);
        double stdValue = standardDeviation.evaluate(array, meanValue);

        return new Statistics(meanValue, stdValue);
    }
}
