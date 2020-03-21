package com.coding2go.common;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ListHelperTest {
    private ListHelper listHelper;

    @Before
    public void init() {
        listHelper = new ListHelper();
    }

    @Test(expected = NullPointerException.class)
    public void givenNullValueThenException() {
        listHelper.pDiff(null, new ArrayList<>());
    }

    @Test(expected = NullPointerException.class)
    public void givenNullReferenceThenException() {
        listHelper.pDiff(new ArrayList<>(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenDifferentSizeThenException() {
        listHelper.pDiff(Arrays.asList(1.0), Arrays.asList(1.0, 2.0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenDifferentSizeThenExceptionV2() {
        listHelper.pDiff(Arrays.asList(1.0), new ArrayList<>());
    }

    @Test
    public void givenNullValuesThenNullOutputOnlyInTheSamePosition() {
        List<Double> value = Arrays.asList(1.0, null, 3.0);
        List<Double> reference = Arrays.asList(1.0, 2.0, 3.0);

        List<Double> result = listHelper.pDiff(value, reference);
        assertNotNull(result.get(0));
        assertNull(result.get(1));
        assertNotNull(result.get(2));
    }

    @Test
    public void givenNullReferenceThenNullOutputOnlyInTheSamePosition() {
        List<Double> value = Arrays.asList(1.0, 2.0, 3.0);
        List<Double> reference = Arrays.asList(1.0, null, 3.0);

        List<Double> result = listHelper.pDiff(value, reference);
        assertNotNull(result.get(0));
        assertNull(result.get(1));
        assertNotNull(result.get(2));
    }

    @Test
    public void givenOkInputThenPDiffCalculated() {
        List<Double> value = Arrays.asList(1.0, 2.0, 3.0);
        List<Double> reference = Arrays.asList(1.0, 2.1, 2.9);

        List<Double> result = listHelper.pDiff(value, reference);

        assertEquals(0, result.get(0), RealHelper.BIAS);
        assertEquals(-0.0476, result.get(1), RealHelper.BIAS);
        assertEquals(0.0344, result.get(2), RealHelper.BIAS);
    }
}