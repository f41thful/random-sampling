package com.coding2go.common;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ListHelperTest {
    private static final double IS_LESS_OR_EQUAL_BIAS = 0.05;
    private ListHelper listHelper;

    @Before
    public void init() {
        listHelper = new ListHelper();
    }

    @Test(expected = NullPointerException.class)
    public void givenNullValue_thenException() {
        listHelper.pDiff(null, new ArrayList<>());
    }

    @Test(expected = NullPointerException.class)
    public void givenNullReference_thenException() {
        listHelper.pDiff(new ArrayList<>(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenDifferentSize_thenException() {
        listHelper.pDiff(Arrays.asList(1.0), Arrays.asList(1.0, 2.0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenDifferentSize_thenExceptionV2() {
        listHelper.pDiff(Arrays.asList(1.0), new ArrayList<>());
    }

    @Test
    public void givenNullValues_thenNullOutputOnlyInTheSamePosition() {
        List<Double> value = Arrays.asList(1.0, null, 3.0);
        List<Double> reference = Arrays.asList(1.0, 2.0, 3.0);

        List<Double> result = listHelper.pDiff(value, reference);
        assertNotNull(result.get(0));
        assertNull(result.get(1));
        assertNotNull(result.get(2));
    }

    @Test
    public void givenNullReference_thenNullOutputOnlyInTheSamePosition() {
        List<Double> value = Arrays.asList(1.0, 2.0, 3.0);
        List<Double> reference = Arrays.asList(1.0, null, 3.0);

        List<Double> result = listHelper.pDiff(value, reference);
        assertNotNull(result.get(0));
        assertNull(result.get(1));
        assertNotNull(result.get(2));
    }

    @Test
    public void givenOkInput_thenPDiffCalculated() {
        List<Double> value = Arrays.asList(1.0, 2.0, 3.0);
        List<Double> reference = Arrays.asList(1.0, 2.1, 2.9);

        List<Double> result = listHelper.pDiff(value, reference);

        assertEquals(0, result.get(0), RealHelper.BIAS);
        assertEquals(-0.0476, result.get(1), RealHelper.BIAS);
        assertEquals(0.0344, result.get(2), RealHelper.BIAS);
    }

    @Test(expected = NullPointerException.class)
    public void givenNullList_whenIsLessOrEqual_thenException() {
        listHelper.isLessOrEqual(null, IS_LESS_OR_EQUAL_BIAS);
    }

    @Test
    public void givenNullValuesV0_whenIsLessOrEqual_thenTheyAreIgnored() {
        List<Double> values = new ArrayList<>();
        values.add(null);
        assertTrue(listHelper.isLessOrEqual(values, IS_LESS_OR_EQUAL_BIAS) );
    }

    @Test
    public void givenNullValuesV1_whenIsLessOrEqual_thenTheyAreIgnored() {
        List<Double> values = new ArrayList<>();
        values.add(null);
        values.add(0.06);
        assertFalse(listHelper.isLessOrEqual(values, IS_LESS_OR_EQUAL_BIAS) );
    }

    @Test
    public void givenNullValuesV2_whenIsLessOrEqual_thenTheyAreIgnored() {
        List<Double> values = new ArrayList<>();
        values.add(null);
        values.add(0.04);
        assertTrue(listHelper.isLessOrEqual(values, IS_LESS_OR_EQUAL_BIAS) );
    }

    @Test
    public void givenAllGreaterThanBias_whenIsLessOrEqual_thenFalse() {
        List<Double> values = new ArrayList<>();
        values.add(0.06);
        values.add(0.07);
        assertFalse(listHelper.isLessOrEqual(values, IS_LESS_OR_EQUAL_BIAS) );
    }

    @Test
    public void givenAllLessOrEqualThanBias_whenIsLessOrEqual_thenTrue() {
        List<Double> values = new ArrayList<>();
        values.add(0.04);
        values.add(0.05);
        assertTrue(listHelper.isLessOrEqual(values, IS_LESS_OR_EQUAL_BIAS) );
    }

    @Test
    public void givenMixedValues_whenIsLessOrEqual_thenFalse() {
        List<Double> values = new ArrayList<>();
        values.add(0.04);
        values.add(0.05);
        values.add(0.06);
        values.add(null);
        assertFalse(listHelper.isLessOrEqual(values, IS_LESS_OR_EQUAL_BIAS) );
    }
}