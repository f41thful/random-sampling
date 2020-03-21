package com.coding2go.common;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RealHelperTest {
    private RealHelper realHelper;

    @Before
    public void init() {
        realHelper = new RealHelper();
    }

    @Test
    public void givenEqualsV0_thenOk() {
        assertTrue(realHelper.equals(0.1, 0.1001));
    }

    @Test
    public void givenEqualsV1_thenOk() {
        assertTrue(realHelper.equals(0.1, 0.09999));
    }

    @Test
    public void givenEqualsV2_thenOk() {
        assertTrue(realHelper.equals(-0.1, -0.09999));
    }

    @Test
    public void givenEqualsV3_thenOk() {
        assertTrue(realHelper.equals(-0.1, -0.1001));
    }

    @Test
    public void givenNotEqualsV0_thenOk() {
        assertFalse(realHelper.equals(0.1, 0.11));
    }

    @Test
    public void givenNotEqualsV1_thenOk() {
        assertFalse(realHelper.equals(0.1, 0.09));
    }

    @Test
    public void givenNotEqualsV2_thenOk() {
        assertFalse(realHelper.equals(-0.1, -0.11));
    }

    @Test
    public void givenNotEqualsV3_thenOk() {
        assertFalse(realHelper.equals(-0.1, -0.09));
    }

    @Test
    public void givenReference0_whenPDiff_thenNull() {
        assertNull(realHelper.pDiff(3.0, 0));
    }

    @Test
    public void givenValue0_whenPDiff_then0() {
        assertEquals(-1, realHelper.pDiff(0, 1), RealHelper.BIAS);
    }

    @Test
    public void givenPositiveValues_whenPDiff_thenOk() {
        assertEquals(-0.5, realHelper.pDiff(3, 6), RealHelper.BIAS);
    }

    @Test
    public void givenPositiveValuesV1_whenPDiff_thenOk() {
        assertEquals(1, realHelper.pDiff(6, 3), RealHelper.BIAS);
    }
}