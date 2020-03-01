package com.coding2go.common;

import com.coding2go.exceptions.DistributionException;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Objects;

public class DistributionValidation {
    private static final Logger logger = Logger.getLogger(DistributionValidation.class);
    private static final RealHelper realHelper = new RealHelper();

    public void validate(List<Double> distribution) {
        validateHasElements(distribution);
        validateDistributionSums1(distribution);
        validateOnlyGreaterOrEqualsThan0(distribution);
    }

    private void validateHasElements(List<Double> distribution) {
        Objects.requireNonNull(distribution);

        if(distribution.isEmpty()) {
            throw new DistributionException("The distribution frequencies must have at least one frequency.");
        }
    }

    private void validateDistributionSums1(List<Double> distribution) {
        logger.info("Checking distribution sums 1.");
        double sum = 0;
        for(Double d : distribution) {
            sum += d;
        }

        if(!realHelper.equals(sum, 1)) {
            throw new DistributionException("The distribution adds up to " + sum + " instead of 1. Distribution " + distribution + ".");
        }
    }

    private void validateOnlyGreaterOrEqualsThan0(List<Double> distribution) {
        logger.info("Checking there are only natural values.");
        for(Double d : distribution) {
            if(d < 0) {
                throw new DistributionException("There are negative values like " + d + " . Distribution " + distribution + ".");
            }
        }
    }
}
