package com.coding2go;

import com.coding2go.exceptions.DistributionException;
import org.apache.log4j.Logger;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;

public class Population {
    private static final Logger logger = Logger.getLogger(Population.class);

    private final int populationSize;
    private final List<Double> classDistribution;

    public Population(int populationSize, List<Double> classDistribution) {
        Objects.requireNonNull(classDistribution);
        if(classDistribution.isEmpty()) {
            throw new DistributionException("The distribution frequencies must have at least one frequency.");
        }

        if(populationSize <= 0) {
            throw new InvalidParameterException("The population size must be positibe but is " + populationSize);
        }

        this.populationSize = populationSize;
        this.classDistribution = classDistribution;

        logger.info("Created population with distribution " + classDistribution + " and size " + populationSize + ".");
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
}
