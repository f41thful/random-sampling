package com.coding2go;

import com.coding2go.common.DistributionValidation;
import com.coding2go.common.RealHelper;
import com.coding2go.exceptions.DistributionException;
import org.apache.log4j.Logger;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class DistributionGenerator {
    private static final Logger logger = Logger.getLogger(DistributionGenerator.class);
    private static final RealHelper realHelper = new RealHelper();
    private static final DistributionValidation distributionValidation = new DistributionValidation();
    private final int numClasses;

    public DistributionGenerator(int numClasses) {
        if(numClasses <= 0) {
            throw new InvalidParameterException("The number of classes must be possitive not " + numClasses + ".");
        }

        logger.info("Created a distribution generation with " + numClasses + " classes.");

        this.numClasses = numClasses;
    }

    public List<Double> generate() {
        logger.info("Generating a distribution with " + numClasses + " classes.");
        List<Double> distribution = new ArrayList<>();

        for(int i = 0; i < numClasses; i++) {
            distribution.add(getDistribution(i));
        }
        logger.info("Generated distribution: " + distribution);

        distributionValidation.validate(distribution);
        logger.info("Distribution successfully generated.");

        return distribution;
    }

    /*
        distribution must be given over 1 (not 100)
     */
    double getDistribution(int klass) {
        return ((double) 1) / numClasses;
    }
}
