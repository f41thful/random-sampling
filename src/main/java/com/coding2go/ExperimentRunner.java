package com.coding2go;

import org.apache.log4j.Logger;

import java.security.InvalidParameterException;
import java.util.Objects;
import java.util.Random;

public class ExperimentRunner {
    private static final Logger logger = Logger.getLogger(ExperimentRunner.class);

    private final Random random;
    private final int numTimes;
    private final PopulationSelectionExperiment populationSelectionExperiment;

    public ExperimentRunner(Random random, int numTimes, Population population) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(population);

        if(numTimes <= 0) {
            throw new InvalidParameterException("numTimes must be > 0");
        }

        this.random = random;
        this.numTimes = numTimes;

        populationSelectionExperiment = new PopulationSelectionExperiment(population);
    }

    public SelectionExperimentResult run() {
        logger.info("Starts experiment with " + numTimes + " iterations.");
        for(int i = 0; i < numTimes; i++) {
            populationSelectionExperiment.select(random.nextDouble());
        }
        logger.info("Finishes experiment with " + numTimes + " iterations.");

        return populationSelectionExperiment.getResult();
    }
}
