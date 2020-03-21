package com.coding2go.runners;

import com.coding2go.Population;
import com.coding2go.PopulationSamplingExperiment;
import com.coding2go.results.SelectionExperimentResult;
import org.apache.log4j.Logger;

import java.security.InvalidParameterException;
import java.util.Objects;
import java.util.Random;

public class SampleRunner {
    private static final Logger logger = Logger.getLogger(SampleRunner.class);

    private final Random random;
    private final int numSamples;
    private final PopulationSamplingExperiment populationSamplingExperiment;

    public SampleRunner(Random random, int numSamples, Population population) {
        Objects.requireNonNull(random);
        Objects.requireNonNull(population);

        if(numSamples <= 0) {
            throw new InvalidParameterException("numSamples must be > 0");
        }

        this.random = random;
        this.numSamples = numSamples;

        populationSamplingExperiment = new PopulationSamplingExperiment(population);
    }

    public SelectionExperimentResult run() {
        logger.info("Starts experiment with " + numSamples + " samples.");
        for(int i = 0; i < numSamples; i++) {
            populationSamplingExperiment.select(random.nextDouble());
        }
        logger.info("Finishes experiment with " + numSamples + " samples.");

        return populationSamplingExperiment.getResult();
    }
}
