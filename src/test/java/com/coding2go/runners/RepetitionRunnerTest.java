package com.coding2go.runners;

import com.coding2go.Population;
import com.coding2go.results.RepetitionExperimentResult;
import com.coding2go.results.SelectionExperimentResult;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RepetitionRunnerTest {
    private static final double BIAS = 0.05;
    private RepetitionRunner repetitionRunner;

    @Test(expected = IllegalArgumentException.class)
    public void givenInvalidNumTimes_thenException() {
        new RepetitionRunner(0, 2.0, true, mock(Random.class), 3, mock(Population.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenInvalidBias_thenException() {
        new RepetitionRunner(2, -2.0, true, mock(Random.class), 3, mock(Population.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenInvalidNumSamples_thenException() {
        new RepetitionRunner(2, 2.0, true, mock(Random.class), 0, mock(Population.class));
    }

    @Test
    public void givenDoesNotStopFast_thenNumTimesExperiments() {
        repetitionRunner = spy(new RepetitionRunner(5, BIAS, false, mock(Random.class), 3, mock(Population.class)));
        doReturn(mockExperimentResultLessThanBias(), mockExperimentResultGreaterThanBias()).when(repetitionRunner).runSamplingExperiment();
        RepetitionExperimentResult result = repetitionRunner.run();

        assertEquals(5, result.getExperiments().size());
        assertFalse(result.isLessOrEqualTheBias());
    }

    @Test
    public void givenStopFast_thenWhenGreaterThanBiasStops() {
        repetitionRunner = spy(new RepetitionRunner(5, BIAS, true, mock(Random.class), 3, mock(Population.class)));
        doReturn(mockExperimentResultLessThanBias(), mockExperimentResultGreaterThanBias()).when(repetitionRunner).runSamplingExperiment();
        RepetitionExperimentResult result = repetitionRunner.run();

        assertEquals(2, result.getExperiments().size());
        assertFalse(result.isLessOrEqualTheBias());
    }

    @Test
    public void givenAllLessThanBias_thenLessThanBiasFalse() {
        repetitionRunner = spy(new RepetitionRunner(5, BIAS, false, mock(Random.class), 3, mock(Population.class)));
        doReturn(mockExperimentResultLessThanBias()).when(repetitionRunner).runSamplingExperiment();
        RepetitionExperimentResult result = repetitionRunner.run();

        assertEquals(5, result.getExperiments().size());
        assertTrue(result.isLessOrEqualTheBias());
    }

    private SelectionExperimentResult mockExperimentResultGreaterThanBias() {
        SelectionExperimentResult result = mock(SelectionExperimentResult.class);
        List<Double> selectionDistribution = new ArrayList<>();
        selectionDistribution.add(0.07);
        selectionDistribution.add(0.08);
        doReturn(selectionDistribution).when(result).getSelectionDistribution();
        return result;
    }

    private SelectionExperimentResult mockExperimentResultLessThanBias() {
        SelectionExperimentResult result = mock(SelectionExperimentResult.class);
        List<Double> selectionDistribution = new ArrayList<>();
        selectionDistribution.add(0.01);
        selectionDistribution.add(0.02);
        doReturn(selectionDistribution).when(result).getSelectionDistribution();
        return result;
    }
}