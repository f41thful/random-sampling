package com.coding2go;

import com.coding2go.common.DistributionValidation;
import org.apache.log4j.Logger;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Population {
    private static final Logger logger = Logger.getLogger(Population.class);
    private final DistributionValidation distributionValidation = new DistributionValidation();

    private final int populationSize;
    private final List<Double> classDistribution;
    private List<Double> accumulatedDistribution;

    public Population(int populationSize, List<Double> classDistribution) {
        distributionValidation.validate(classDistribution);

        if(populationSize <= 0) {
            throw new InvalidParameterException("The population size must be positibe but is " + populationSize);
        }

        this.populationSize = populationSize;
        this.classDistribution = classDistribution;

        logger.info("Created population with distribution " + classDistribution + " and size " + populationSize + ".");
        createAccumulatedDistribution();
        logger.debug("Created accumulated distribution " + accumulatedDistribution + ".");
    }

    public int getNumDiffClasses() {
        return classDistribution.size();
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public double getPopulationForClass(int klass) {
        try {
            return classDistribution.get(klass) * populationSize;
        } catch(IndexOutOfBoundsException ex) {
            throw new InvalidParameterException("No class " + klass + " exists. Existing classes are [0 to " + (classDistribution.size() - 1) + "].");
        }
    }

    public int getClassForIndividual(double individual) {
        if(individual < 0 || individual > 1) {
            throw new InvalidParameterException("The individual must be within the range [0, 1]. Individual provided: " + individual + ".");
        }

        for(int i = 0; i < accumulatedDistribution.size(); i++) {
            if(individual < accumulatedDistribution.get(i)) {
                return i;
            }
        }

        throw new RuntimeException("Unknown error. Class cannot be provided for individual " + individual + " using class distribution " + classDistribution + ".");
    }

    private void createAccumulatedDistribution() {
        accumulatedDistribution = new ArrayList<>();
        accumulatedDistribution.add(classDistribution.get(0));

        for(int i = 1; i < classDistribution.size(); i++) {
            accumulatedDistribution.add(accumulatedDistribution.get(i - 1) + classDistribution.get(i));
        }
    }
}
