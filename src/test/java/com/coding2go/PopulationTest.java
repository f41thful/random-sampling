package com.coding2go;

import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class PopulationTest {
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
        Population population = new Population(3, Arrays.asList(0.33, 0.66));

        assertEquals(2, population.getNumDiffClasses());
        assertEquals(3, population.getPopulationSize());
        assertEquals(0.33 * 3, population.getPopulationForClass(0), 0.0001);
    }

    @Test(expected = InvalidParameterException.class)
    public void givenInvalidClassNegative_thenFails() {
        Population population = new Population(3, Arrays.asList(0.33, 0.66));
        population.getPopulationForClass(-2);
    }

    @Test(expected = InvalidParameterException.class)
    public void givenInvalidClassPossitive_thenFails() {
        Population population = new Population(3, Arrays.asList(0.33, 0.66));
        population.getPopulationForClass(7);
    }
}