package com.coding2go.common;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    @Test(expected = NullPointerException.class)
    public void givenIllegalArgV0_thenException() {
        listHelper.reGroupByIndex(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenIllegalArgV1_thenException() {
        listHelper.reGroupByIndex(Collections.emptyList());
    }

    @Test(expected = NullPointerException.class)
    public void givenIllegalArgV2_thenException() {
        listHelper.reGroupByIndex(Arrays.asList(null, Arrays.asList()));
    }

    @Test
    public void givenValidInput_thenOk() {
        List<List<Integer>> result = listHelper.reGroupByIndex(Arrays.asList(
            Arrays.asList(1,     2,   3, 4,  5),
            Arrays.asList(1,     2,   3, 4,  5),
            Arrays.asList(7,     8,   9, 10, 11),
            Arrays.asList(12,   13, 14),
            Arrays.asList(1,     2,  3),
            Arrays.asList((Integer) null),
            Arrays.asList((Integer) null),
            Collections.emptyList(),
            Arrays.asList(15,    16, 17, 18, 19, 20, 21))
        );

        assertEquals(7, result.size());
        assertEquals(Arrays.asList(1, 1, 7, 12, 1, null, null, 15), result.get(0));
        assertEquals(Arrays.asList(2, 2, 8, 13, 2, 16), result.get(1));
        assertEquals(Arrays.asList(3, 3, 9, 14, 3, 17), result.get(2));
        assertEquals(Arrays.asList(4, 4, 10, 18), result.get(3));
        assertEquals(Arrays.asList(5, 5, 11, 19), result.get(4));
        assertEquals(Arrays.asList(20), result.get(5));
        assertEquals(Arrays.asList(21), result.get(6));
    }
}