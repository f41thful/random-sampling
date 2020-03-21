package com.coding2go;

import com.coding2go.common.DistributionValidation;
import com.coding2go.common.ListHelper;
import com.coding2go.distribution_examples.DistributionExamples;
import com.coding2go.results.SelectionExperimentResult;
import com.coding2go.runners.SampleRunner;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Random;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);
    public static void main(String[] args){
        logger.debug("--- Main ---");
        runExperiment();
    }

    private static void runExperiment() {
        DistributionExamples distributionExamples = new DistributionExamples();

        long seed = System.nanoTime();
        logger.debug("Using seed: " + seed);
        Random random = new Random(seed);

        int numTimes = 30_000;

        Population population = distributionExamples.spanishElections2011();
        SampleRunner sampleRunner = new SampleRunner(random, numTimes, population);

        SelectionExperimentResult result = sampleRunner.run();

        List<Double> populationDistribution = population.getClassDistribution();
        System.out.println("Population distribution: " + populationDistribution);
        System.out.println("Sampling distribution:   " + result.getSelectionDistribution());
        System.out.println("Repetition distribution: " + result.getRepetitionDistribution());

        ListHelper listHelper = new ListHelper();
        System.out.println("pDiff: " + listHelper.pDiff(result.getSelectionDistribution(), populationDistribution));
    }
}
