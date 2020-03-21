package com.coding2go.common;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class StatisticsHelperTest {
    private StatisticsHelper statisticsHelper;

    @Before
    public void init() {
        statisticsHelper = new StatisticsHelper();
    }

    @Test
    public void givenNulls_thenNull() {
        StatisticsHelper.Statistics statistics = statisticsHelper.statistics(distribution0());
        assertNull(statistics.getMean());
        assertNull(statistics.getStd());
    }

    @Test
    public void givenValues_thenOk() {
        StatisticsHelper.Statistics statistics = statisticsHelper.statistics(distribution1());
        assertEquals(0.2, statistics.getMean(), RealHelper.BIAS);
        assertEquals(0.44721, statistics.getStd(), RealHelper.BIAS);
    }

    @Test
    public void givenAllEqualValues_thenOk() {
        StatisticsHelper.Statistics statistics = statisticsHelper.statistics(distribution2());
        assertEquals(0.2, statistics.getMean(), RealHelper.BIAS);
        assertEquals(0, statistics.getStd(), RealHelper.BIAS);
    }

    @Test
    public void givenDifferentValues_thenOk() {
        StatisticsHelper.Statistics statistics = statisticsHelper.statistics(distribution3());
        assertEquals(0.2, statistics.getMean(), RealHelper.BIAS);
        assertEquals(0.14577, statistics.getStd(), RealHelper.BIAS);
    }

    private List<Double> distribution0() {
        return Arrays.asList(1.0, 0.0, 0.0, null, null);
    }

    private List<Double> distribution1() {
        return Arrays.asList(1.0, 0.0, 0.0, 0.0, 0.0);
    }

    private List<Double> distribution2() {
        return Arrays.asList(0.2, 0.2, 0.2, 0.2, 0.2);
    }

    private List<Double> distribution3() {
        return Arrays.asList(0.4, 0.3, 0.15, 0.1, 0.05);
    }
}