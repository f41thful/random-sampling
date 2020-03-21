package com.coding2go.results;

import com.coding2go.Population;
import com.coding2go.common.DistributionValidation;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class RepetitionExperimentResultTest {
    private static final DistributionValidation distributionValidation = new DistributionValidation();
    private static final double BIAS = 0.05;

    private RepetitionExperimentResult repetitionExperimentResult;

    @Before
    public void init() {
        repetitionExperimentResult = new RepetitionExperimentResult(5, BIAS, mock(Population.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenInvalidNumTimesV0_thenException() {
        new RepetitionExperimentResult(-1, BIAS, mock(Population.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenInvalidNumTimesV1_thenException() {
        new RepetitionExperimentResult(0, BIAS, mock(Population.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenInvalidBias_thenException() {
        new RepetitionExperimentResult(2, -0.5, mock(Population.class));
    }

    @Test
    public void givenResultsAreAdded_thenTheyAreReturned() {
        SelectionExperimentResult selectionExperimentResult0 = mock(SelectionExperimentResult.class);
        SelectionExperimentResult selectionExperimentResult1 = mock(SelectionExperimentResult.class);

        repetitionExperimentResult.addExperimentResult(selectionExperimentResult0);
        repetitionExperimentResult.addExperimentResult(selectionExperimentResult1);

        List<SelectionExperimentResult> experiments = repetitionExperimentResult.getExperiments();

        assertEquals(2, experiments.size());
        assertTrue(experiments.contains(selectionExperimentResult0));
        assertTrue(experiments.contains(selectionExperimentResult1));
    }

    @Test
    public void givenCalculate0() {
        repetitionExperimentResult = new RepetitionExperimentResult(2, BIAS, mock(Population.class));
        repetitionExperimentResult.addExperimentResult(createSamplingMock(distribution0()));
        repetitionExperimentResult.addExperimentResult(createSamplingMock(distribution1()));

        repetitionExperimentResult.calculate();
        assertEquals(Arrays.asList(1.0, 0.0, null, null, null), repetitionExperimentResult.getMeanSamplingDistribution());
        assertEquals(Arrays.asList(0.0, 0.0, null, null, null), repetitionExperimentResult.getStdSamplingDistribution());
    }

    @Test
    public void givenCalculate1() {
        repetitionExperimentResult = new RepetitionExperimentResult(2, BIAS, mock(Population.class));
        repetitionExperimentResult.addExperimentResult(createSamplingMock(distribution0()));
        repetitionExperimentResult.addExperimentResult(createSamplingMock(distribution2()));

        repetitionExperimentResult.calculate();
        assertEquals(Arrays.asList(0.6, 0.1, 0.1, null, null), repetitionExperimentResult.getMeanSamplingDistribution());
        assertEquals(Arrays.asList(0.565685424949238, 0.14142135623730953, 0.14142135623730953, null, null), repetitionExperimentResult.getStdSamplingDistribution());
    }

    private SelectionExperimentResult createSamplingMock(List<Double> samplingDistribution) {
        SelectionExperimentResult experiment = mock(SelectionExperimentResult.class);
        doReturn(samplingDistribution).when(experiment).getSelectionDistribution();

        return experiment;
    }

    private List<Double> distribution0() {
        return Arrays.asList(1.0, 0.0, 0.0, null, null);
    }

    private List<Double> distribution1() {
        return Arrays.asList(1.0, 0.0, null, 0.0, null);
    }

    private List<Double> distribution2() {
        return Arrays.asList(0.2, 0.2, 0.2, 0.2, 0.2);
    }
}