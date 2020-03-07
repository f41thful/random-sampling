package com.coding2go;

import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);
    public static void main(String[] args){
        logger.debug("--- Main ---");
        runExperiment();
    }

    private static void runExperiment() {
        long seed = System.nanoTime();
        logger.debug("Using seed: " + seed);
        Random random = new Random(seed);

        int numTimes = 1000;

        Population population = testPopulation();
        ExperimentRunner experimentRunner = new ExperimentRunner(random, numTimes, population);

        SelectionExperimentResult result = experimentRunner.run();

        List<Double> populationDistribution = population.getClassDistribution();
        System.out.println("Population distribution: " + populationDistribution);
        System.out.println("Sampling distribution:   " + result.getSelectionDistribution());
        System.out.println("Repetition distribution: " + result.getRepetitionDistribution());
    }

    private static Population spanishElections() {
        List<Double> populationDistribution = Arrays.asList(0.3986, 0.2657, 0.1727, 0.0863, 0.0431, 0.0332, 0.0004);
        return new Population(24_507_715, populationDistribution);
    }

    private static Population testPopulation() {
        List<Double> populationDistribution = Arrays.asList(0.3986, 0.5678, 0.0332, 0.0004);
        return new Population(1000, populationDistribution);
    }
}
