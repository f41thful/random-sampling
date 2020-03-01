package com.coding2go;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PopulationTest {
    private static final Logger logger = Logger.getLogger(PopulationTest.class);
    private Population population;

    @Test(expected = Exception.class)
    public void givenNullDistribution_thenFails() {
        new Population(1, null);
    }

    @Test(expected = Exception.class)
    public void givenEmptyDistribution_thenFails() {
        new Population(1, new ArrayList<>());
    }

    @Test(expected = InvalidParameterException.class)
    public void given0Population_thenFails() {
        new Population(0, Collections.singletonList(1.0));
    }

    @Test(expected = InvalidParameterException.class)
    public void givenNegativePopulation_thenFails() {
        new Population(-2, Collections.singletonList(1.0));
    }

    @Test
    public void givenValidPopulation_thenGettersWork() {
        Population population = new Population(3, Arrays.asList(0.33, 0.67));

        assertEquals(2, population.getNumDiffClasses());
        assertEquals(3, population.getPopulationSize());
        assertEquals(0.33 * 3, population.getPopulationForClass(0), 0.0001);
    }

    @Test(expected = InvalidParameterException.class)
    public void givenInvalidClassNegative_thenFails() {
        Population population = new Population(3, Arrays.asList(0.33, 0.67));
        population.getPopulationForClass(-2);
    }

    @Test(expected = InvalidParameterException.class)
    public void givenInvalidClassPossitive_thenFails() {
        Population population = new Population(3, Arrays.asList(0.33, 0.67));
        population.getPopulationForClass(7);
    }

    @Test(expected = InvalidParameterException.class)
    public void givenNegativeIndividual_thenThrowsException() {
        Population population = new Population(3, Arrays.asList(0.33, 0.67));
        population.getClassForIndividual(-0.1);
    }

    @Test(expected = InvalidParameterException.class)
    public void givenAbove1Individual_thenThrowsException() {
        Population population = new Population(3, Arrays.asList(0.33, 0.67));
        population.getClassForIndividual(1.01);
    }

    @Test
    public void given0_thenReturnsClass0() {
        Population population = new Population(3, Arrays.asList(0.33, 0.67));
        int klass = population.getClassForIndividual(0);
        assertEquals(0, klass);
    }

    @Test
    public void given1_thenReturnsTheLastClass() {
        Population population = new Population(3, Arrays.asList(0.33, 0.67));
        int klass = population.getClassForIndividual(0.999);
        assertEquals(population.getNumDiffClasses() - 1, klass);
    }

    @Test
    public void givenExtremeIndividual0_thenTheRightClassIsReturned() {
        Population population = new Population(3, Arrays.asList(0.33, 0.67));
        int klass = population.getClassForIndividual(0.32);
        assertEquals(0, klass);
    }

    @Test
    public void givenExtremeIndividual1_thenTheRightClassIsReturned() {
        Population population = new Population(3, Arrays.asList(0.33, 0.40, 0.27));
        int klass = population.getClassForIndividual(0.40);
        assertEquals(1, klass);
    }

    @Test
    public void givenMidleIndividual0_thenTheRightClassIsReturned() {
        Population population = new Population(3, Arrays.asList(0.33, 0.40, 0.27));
        int klass = population.getClassForIndividual(0.1);
        assertEquals(0, klass);
    }

    @Test
    public void givenMidleIndividual1_thenTheRightClassIsReturned() {
        Population population = new Population(3, Arrays.asList(0.33, 0.40, 0.27));
        int klass = population.getClassForIndividual(0.5);
        assertEquals(1, klass);
    }

    @Test
    public void given0DistributionBeginning_thenTheRightClassIsReturned() {
        Population population = new Population(3, Arrays.asList(0.0, 0.73, 0.27));
        int klass = population.getClassForIndividual(0);
        assertEquals(1, klass);
    }

    @Test
    public void given0DistributionBeginning_thenTheRightClassIsReturned1() {
        Population population = new Population(3, Arrays.asList(0.0, 0.73, 0.27));
        int klass = population.getClassForIndividual(0.2);
        assertEquals(1, klass);
    }

    @Test
    public void given0DistributionMiddle_thenTheRightClassIsReturned() {
        Population population = new Population(3, Arrays.asList(0.23, 0.0, 0.77));
        int klass = population.getClassForIndividual(0.22999);
        assertEquals(0, klass);
    }

    @Test
    public void given0DistributionMiddle_thenTheRightClassIsReturned1() {
        Population population = new Population(3, Arrays.asList(0.23, 0.0, 0.77));
        int klass = population.getClassForIndividual(0.5);
        assertEquals(2, klass);
    }

    @Test
    public void given0DistributionMiddle_thenItNeverGetsReturned() {
        long seed = System.currentTimeMillis();
        logger.debug("Seed: " + seed);

        List<Integer> zeroFreqClasses = Arrays.asList(0, 2, 3, 5, 7, 9);
        Random random = new Random(seed);
        //                                                          0    1    2    3    4     5    6    7    8     9
        Population population = new Population(1000, Arrays.asList(0.0, 0.1, 0.0, 0.0, 0.33, 0.0, 0.4, 0.0, 0.17, 0.0));

        int klass;
        for(int i = 0; i < 30000; i++) {
            klass = population.getClassForIndividual(random.nextDouble());
            assertFalse(zeroFreqClasses.contains(klass));
        }
    }


}