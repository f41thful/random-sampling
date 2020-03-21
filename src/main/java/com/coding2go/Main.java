package com.coding2go;

import com.coding2go.common.ListHelper;
import com.coding2go.runners.SampleRunner;
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

        int numTimes = 30_000;

        Population population = spanishElections2011();
        SampleRunner sampleRunner = new SampleRunner(random, numTimes, population);

        SelectionExperimentResult result = sampleRunner.run();

        List<Double> populationDistribution = population.getClassDistribution();
        System.out.println("Population distribution: " + populationDistribution);
        System.out.println("Sampling distribution:   " + result.getSelectionDistribution());
        System.out.println("Repetition distribution: " + result.getRepetitionDistribution());

        ListHelper listHelper = new ListHelper();
        System.out.println("pDiff: " + listHelper.pDiff(result.getSelectionDistribution(), populationDistribution));
    }

    private static Population spanishElections2019_1() {
        List<Double> populationDistribution = Arrays.asList(0.28, 0.2082, 0.1509, 0.098, 0.0361, 0.0679, 0.1496, 0.009300000000000086);
        return new Population(37_001_379, populationDistribution);
    }

    private static Population spanishElections2019_0() {
        List<Double> populationDistribution = Arrays.asList(0.2867, 0.1669, 0.1586, 0.1197, 0.1026, 0.0389, 0.1326);
        return new Population(36_898_883, populationDistribution);
    }

    private static Population spanishElections2016() {
        List<Double> populationDistribution = Arrays.asList(0.3301, 0.2263, 0.1342, 0.1306, 0.0355, 0.1433);
        return new Population(36_520_913, populationDistribution);
    }

    private static Population spanishElections2015() {
        List<Double> populationDistribution = Arrays.asList(0.2871, 0.2200, 0.1394, 0.1267, 0.0369, 0.0368, 0.1531);
        return new Population(36_511_848, populationDistribution);
    }

    private static Population spanishElections2011() {
        List<Double> populationDistribution = Arrays.asList(0.4463, 0.2876, 0.0692, 0.0470, 0.0417, 0.1082);
        return new Population(35_779_491, populationDistribution);
    }

    private static Population testPopulation() {
        List<Double> populationDistribution = Arrays.asList(0.3986, 0.5678, 0.0332, 0.0004);
        return new Population(1000, populationDistribution);
    }

    private static Population testCoin() {
        List<Double> populationDistribution = Arrays.asList(0.5, 0.5);
        return new Population(2, populationDistribution);
    }

    private static Population testDice() {
        List<Double> populationDistribution = Arrays.asList(1.0 / 6, 1.0 / 6, 1.0 / 6, 1.0 / 6, 1.0 / 6, 1.0 / 6);
        return new Population(6, populationDistribution);
    }
}
