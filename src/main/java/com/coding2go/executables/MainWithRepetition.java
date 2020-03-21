package com.coding2go.executables;

import com.coding2go.Population;
import com.coding2go.common.ListHelper;
import com.coding2go.distribution_examples.DistributionExamples;
import com.coding2go.results.RepetitionExperimentResult;
import com.coding2go.results.SelectionExperimentResult;
import com.coding2go.runners.RepetitionRunner;
import com.coding2go.runners.SampleRunner;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Random;

public class MainWithRepetition {
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args){
        logger.debug("--- Main With Repetition ---");
        runExperiment();
    }

    private static void runExperiment() {
        DistributionExamples distributionExamples = new DistributionExamples();

        long seed = System.nanoTime();
        logger.debug("Using seed: " + seed);
        Random random = new Random(seed);

        int numSamples = 30_000;
        int numTimes = 1;
        double bias = 0.05;

        Population population = distributionExamples.spanishElections2011();
        RepetitionRunner repetitionRunner = new RepetitionRunner(numTimes, bias, false, random, numSamples, population);

        RepetitionExperimentResult result = repetitionRunner.run();

        List<Double> populationDistribution = population.getClassDistribution();
        System.out.println();
        System.out.println("Every result less or equal than bias (" + bias + "): " + result.isLessOrEqualTheBias());
        System.out.println("Population distribution: " + populationDistribution);
        System.out.println("Sampling distribution (mean):   " + result.getMeanSamplingDistribution());
        System.out.println("Sampling distribution (std):   " + result.getStdSamplingDistribution());
        System.out.println("Repetition distribution (mean): " + result.getMeanRepetitionDistribution());
        System.out.println("Repetition distribution (std): " + result.getStdRepetitionDistribution());

        ListHelper listHelper = new ListHelper();
        System.out.println("pDiff: " + listHelper.pDiff(result.getMeanSamplingDistribution(), populationDistribution));

    }
}
