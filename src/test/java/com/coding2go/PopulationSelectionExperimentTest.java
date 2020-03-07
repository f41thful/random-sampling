package com.coding2go;

import com.coding2go.common.DistributionValidation;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class PopulationSelectionExperimentTest {
    private static final Logger logger = Logger.getLogger(PopulationSelectionExperimentTest.class);
    private PopulationSelectionExperiment populationSelectionExperiment;
    private DistributionValidation distributionValidation;

    @Before
    public void init() {
        distributionValidation = new DistributionValidation();
        Population population = new Population(2, Arrays.asList(0.5, 0.5));
        populationSelectionExperiment = new PopulationSelectionExperiment(population);
    }

    @Test
    public void given0Selections_thenNumSelectionsOk() {
        assertEquals(0, populationSelectionExperiment.getNumSelections());
    }

    @Test
    public void given3Selections_thenNumSelectionsOk() {
        populationSelectionExperiment.select(0);
        populationSelectionExperiment.select(0.2);
        populationSelectionExperiment.select(0.22);

        assertEquals(3, populationSelectionExperiment.getNumSelections());
    }

    @Test
    public void givenNoSelection_whenSelect_thenBelongsToTheRightClassAndNotSelected() {
        selectAndAssert(populationSelectionExperiment, 0.4999, 0, false);
    }

    @Test
    public void givenNoSelection_whenSelect_thenBelongsToTheRightClassAndNotSelected1() {
        selectAndAssert(populationSelectionExperiment, 0.8, 1, false);
    }

    @Test
    public void givenElementHasAlreadyBeenSelected_thenBelongsToTheRightClassAndIsMarkedAsSelected() {
        populationSelectionExperiment.select(0.1);
        selectAndAssert(populationSelectionExperiment, 0.1, 0, true);
    }

    @Test
    public void givenElementsHasNotBeenSelected_thenBelongsToTheRightClassAndIsNotMarkedAsSelected() {
        selectAndAssert(populationSelectionExperiment, 0.1, 0, false);
        selectAndAssert(populationSelectionExperiment, 0.8, 1, false);
    }

    @Test
    public void givenAllElementsHaveBeenSelected_thenMarkedAsAlreadySelected() {
        populationSelectionExperiment.select(0.1);
        populationSelectionExperiment.select(0.8);

        selectAndAssert(populationSelectionExperiment, 0.8, 1, true);
    }

    @Test
    public void givenSelection_thenOk() {
        PopulationSelectionExperiment experiment = createExperiment(3, Arrays.asList(0.67, 0.33));

        // first element
        selectAndAssert(experiment, 0.0, 0, false);

        // second element
        selectAndAssert(experiment, 0.5, 0, false);

        // all elements of the class selected, thus repeting selection.
        selectAndAssert(experiment, 0.0, 0, true);
        selectAndAssert(experiment, 0.5, 0, true);
        selectAndAssert(experiment, 0.6669, 0, true);

        // element from the other class. None selected yet.
        selectAndAssert(experiment, 0.7, 1, false);

        // element from the second class again, thus already selected.
        selectAndAssert(experiment, 0.7, 1, true);

        // selection again to check state is kept
        selectAndAssert(experiment, 0.5, 0, true);
        selectAndAssert(experiment, 0.7, 1, true);
    }

    @Test
    public void givenClassHasDistribution0_thenItNeverGetsSelected() {
        long seed = System.currentTimeMillis();
        logger.debug("Seed: " + seed);

        List<Integer> zeroFreqClasses = Arrays.asList(0, 2, 3, 5, 7, 9);
        Random random = new Random(seed);
        //                                                                                0    1    2    3    4     5    6    7    8     9
        PopulationSelectionExperiment experiment = createExperiment(10000, Arrays.asList(0.0, 0.1, 0.0, 0.0, 0.33, 0.0, 0.4, 0.0, 0.17, 0.0));


        for(int i = 0; i < 30000; i++) {
            experiment.select(random.nextDouble());
        }

        SelectionExperimentResult result = experiment.getResult();
        List<Double> distribution = result.getSelectionDistribution();
        distributionValidation.validate(distribution);

        for(int klass : zeroFreqClasses) {
            assertEquals(0, distribution.get(klass), 0.0001);
        }
    }

    @Test
    public void givenSelectedOnlyFromOneClass_thenThatGets1AndTheOther0() {
        PopulationSelectionExperiment experiment = createExperiment(4, Arrays.asList(0.5, 0.5));
        for(int i = 0; i < 10; i++) {
            experiment.select(0.1);
        }

        List<Double> distribution = experiment.getResult().getSelectionDistribution();
        distributionValidation.validate(distribution);
        assertEquals(1, distribution.get(0), 0.0001);
        assertEquals(0, distribution.get(1), 0.0001);
    }

    @Test
    public void givenSelectedOnlyFromOneClass1_thenThatGets1AndTheOther0() {
        PopulationSelectionExperiment experiment = createExperiment(4, Arrays.asList(0.5, 0.5));
        for(int i = 0; i < 10; i++) {
            experiment.select(0.7);
        }

        List<Double> distribution = experiment.getResult().getSelectionDistribution();
        distributionValidation.validate(distribution);
        assertEquals(0, distribution.get(0), 0.0001);
        assertEquals(1, distribution.get(1), 0.0001);
    }

    @Test
    public void givenSelectedEvenly_thenClassesDistributionAreEven() {
        PopulationSelectionExperiment experiment = createExperiment(4, Arrays.asList(0.5, 0.5));

        for(int i = 0; i < 5; i++) {
            experiment.select(0.1);
        }

        for(int i = 0; i < 5; i++) {
            experiment.select(0.7);
        }

        List<Double> distribution = experiment.getResult().getSelectionDistribution();
        distributionValidation.validate(distribution);
        assertEquals(5.0 / 10, distribution.get(0), 0.0001);
        assertEquals(5.0 / 10, distribution.get(1), 0.0001);
    }

    @Test
    public void givenCustomSelection_thenOk0() {
        PopulationSelectionExperiment experiment = createExperiment(4, Arrays.asList(0.5, 0.5));

        for(int i = 0; i < 1; i++) {
            experiment.select(0.1);
        }

        for(int i = 0; i < 3; i++) {
            experiment.select(0.7);
        }

        List<Double> distribution = experiment.getResult().getSelectionDistribution();
        distributionValidation.validate(distribution);
        assertEquals(1.0 / 4, distribution.get(0), 0.0001);
        assertEquals(3.0 / 4, distribution.get(1), 0.0001);
    }

    @Test
    public void givenCustomSelection_thenOk1() {
        PopulationSelectionExperiment experiment = createExperiment(4, Arrays.asList(0.5, 0.5));

        for(int i = 0; i < 90; i++) {
            experiment.select(0.1);
        }

        for(int i = 0; i < 1; i++) {
            experiment.select(0.7);
        }

        List<Double> distribution = experiment.getResult().getSelectionDistribution();
        distributionValidation.validate(distribution);
        assertEquals(90.0 / 91.0, distribution.get(0), 0.0001);
        assertEquals(1.0 / 91.0, distribution.get(1), 0.0001);
    }

    @Test
    public void givenNoSelection_whenSelect_thenRepetionDistributionIsZero0() {
        populationSelectionExperiment.select(0.5);
        List<Double> repetition = getRepetionDistribution(populationSelectionExperiment);

        assertEquals(0, repetition.get(0), 0.0001);
        assertEquals(0, repetition.get(1), 0.0001);
    }

    @Test
    public void givenNoSelection_whenSelect_thenRepetionDistributionIsZero1() {
        populationSelectionExperiment.select(0.8);
        List<Double> repetition = getRepetionDistribution(populationSelectionExperiment);

        assertEquals(0, repetition.get(0), 0.0001);
        assertEquals(0, repetition.get(1), 0.0001);
    }

    @Test
    public void givenElementHasAlreadyBeenSelected_thenRepetitionDistributionOk() {
        populationSelectionExperiment.select(0.1);
        populationSelectionExperiment.select(0.1);

        List<Double> repetition = getRepetionDistribution(populationSelectionExperiment);
        assertEquals(1.0 / 2, repetition.get(0), 0.0001);
    }

    @Test
    public void givenElementsHasNotBeenSelected_thenRepetitionDistributionIsZero2() {
        populationSelectionExperiment.select(0.1);
        populationSelectionExperiment.select(0.8);
        List<Double> repetition = getRepetionDistribution(populationSelectionExperiment);
        assertEquals(0, repetition.get(0), 0.0001);
        assertEquals(0, repetition.get(1), 0.0001);
    }

    @Test
    public void givenAllElementsHaveBeenSelected_thenRepetitionDistributionOk() {
        populationSelectionExperiment.select(0.1);
        populationSelectionExperiment.select(0.8);
        populationSelectionExperiment.select(0.8);

        List<Double> repetition = getRepetionDistribution(populationSelectionExperiment);
        assertEquals(0, repetition.get(0), 0.0001);
        assertEquals(1.0 / 2, repetition.get(1), 0.0001);
    }

    @Test
    public void givenSelection_thenRepetionOk() {
        PopulationSelectionExperiment experiment = createExperiment(3, Arrays.asList(0.67, 0.33));

        // first element
        selectAndAssert(experiment, 0.0, 0, false);

        // second element
        selectAndAssert(experiment, 0.5, 0, false);

        // all elements of the class selected, thus repeting selection.
        selectAndAssert(experiment, 0.0, 0, true);
        selectAndAssert(experiment, 0.5, 0, true);
        selectAndAssert(experiment, 0.6669, 0, true);

        // element from the other class. None selected yet.
        selectAndAssert(experiment, 0.7, 1, false);

        // element from the second class again, thus already selected.
        selectAndAssert(experiment, 0.7, 1, true);

        // selection again to check state is kept
        selectAndAssert(experiment, 0.5, 0, true);
        selectAndAssert(experiment, 0.7, 1, true);

        List<Double> distribution = getRepetionDistribution(experiment);
        assertEquals(4.0 / 6, distribution.get(0), 0.0001);
        assertEquals(2.0 / 3, distribution.get(1), 0.0001);
    }

    private List<Double> getRepetionDistribution(PopulationSelectionExperiment experiment) {
        return experiment.getResult().getRepetitionDistribution();
    }

    private PopulationSelectionExperiment createExperiment(int populationSize, List<Double> distribution) {
        Population population = new Population(populationSize, distribution);
        return new PopulationSelectionExperiment(population);
    }

    private void selectAndAssert(PopulationSelectionExperiment experiment, double individual, int klass, boolean hasAlreadyBeenSelected) {
        PopulationSelection populationSelection = experiment.select(individual);
        assertEquals(individual, populationSelection.getIndividual(), 0.0001);
        assertEquals(klass, populationSelection.getKlass());
        assertEquals(hasAlreadyBeenSelected, populationSelection.hasAlreadyBeenSelected());
    }
}