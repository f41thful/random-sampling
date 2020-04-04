package com.coding2go.distribution_examples;

import com.coding2go.Population;

import java.util.Arrays;
import java.util.List;

public class DistributionExamples {
    public Population spanishElections2019_1() {
        List<Double> populationDistribution = Arrays.asList(0.28, 0.2082, 0.1509, 0.098, 0.0361, 0.0679, 0.1496, 0.009300000000000086);
        return new Population(37_001_379, populationDistribution);
    }

    public Population spanishElections2019_0() {
        List<Double> populationDistribution = Arrays.asList(0.2867, 0.1669, 0.1586, 0.1197, 0.1026, 0.0389, 0.1326);
        return new Population(36_898_883, populationDistribution);
    }

    public Population spanishElections2016() {
        List<Double> populationDistribution = Arrays.asList(0.3301, 0.2263, 0.1342, 0.1306, 0.0355, 0.1433);
        return new Population(36_520_913, populationDistribution);
    }

    public Population spanishElections2015() {
        List<Double> populationDistribution = Arrays.asList(0.2871, 0.2200, 0.1394, 0.1267, 0.0369, 0.0368, 0.1531);
        return new Population(36_511_848, populationDistribution);
    }

    public Population spanishElections2011() {
        List<Double> populationDistribution = Arrays.asList(0.4463, 0.2876, 0.0692, 0.0470, 0.0417, 0.1082);
        return new Population(35_779_491, populationDistribution);
    }

    public Population testPopulation() {
        List<Double> populationDistribution = Arrays.asList(0.3986, 0.5678, 0.0332, 0.0004);
        return new Population(1000, populationDistribution);
    }

    public Population testSmallDistributions() {
        List<Double> populationDistribution = Arrays.asList(0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1);
        return new Population(1000, populationDistribution);
    }

    public Population testCoin() {
        List<Double> populationDistribution = Arrays.asList(0.5, 0.5);
        return new Population(2, populationDistribution);
    }

    public Population testDice() {
        List<Double> populationDistribution = Arrays.asList(1.0 / 6, 1.0 / 6, 1.0 / 6, 1.0 / 6, 1.0 / 6, 1.0 / 6);
        return new Population(6, populationDistribution);
    }
}
