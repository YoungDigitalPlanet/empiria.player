package eu.ydp.empiria.player.client.controller.variables.objects.outcome;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName;
import eu.ydp.empiria.player.client.controller.variables.storage.item.ItemOutcomeStorageImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class OutcomeControllerTest {

    private OutcomeController testObj;

    @Before
    public void before() {
        testObj = new OutcomeController();
    }

    @Test
    public void getAllMistakesTest() {
        // given
        ItemOutcomeStorageImpl outcomeStorage = new ItemOutcomeStorageImpl();
        outcomeStorage.putVariable(VariableName.MISTAKES.toString(), createOutcomes(5));

        outcomeStorage.putVariable(null, null);
        outcomeStorage.putVariable(OutcomeController.MISTAKE_SUFIX, createOutcomes(5));
        outcomeStorage.putVariable("id" + OutcomeController.MISTAKE_SUFIX, createOutcomes(10));
        outcomeStorage.putVariable("id1", createOutcomes(15));
        outcomeStorage.putVariable("id2" + OutcomeController.MISTAKE_SUFIX, createOutcomes("wrong"));
        outcomeStorage.putVariable("id3" + OutcomeController.MISTAKE_SUFIX, null);
        outcomeStorage.putVariable("id4" + OutcomeController.MISTAKE_SUFIX, createNullOutcome());
        outcomeStorage.putVariable("id5" + OutcomeController.MISTAKE_SUFIX, createEmptyOutcome());

        // when
        Map<String, Integer> result = testObj.getAllMistakes(outcomeStorage);

        // then
        assertEquals(6, result.size());
        assertEquals(new Integer(5), result.get(""));
        assertEquals(new Integer(10), result.get("id"));
        assertEquals(new Integer(0), result.get("id2"));
        assertEquals(new Integer(0), result.get("id3"));
        assertEquals(new Integer(0), result.get("id4"));
        assertEquals(new Integer(0), result.get("id5"));
    }

    private Outcome createOutcomes(int value) {
        Outcome result = new Outcome();

        result.values = Lists.newArrayList(String.valueOf(value));
        return result;

    }

    private Outcome createOutcomes(String value) {
        Outcome result = new Outcome();

        result.values = Lists.newArrayList(value);
        return result;
    }

    private Outcome createEmptyOutcome() {
        Outcome result = new Outcome();

        result.values = new ArrayList<String>();
        return result;

    }

    private Outcome createNullOutcome() {
        Outcome result = new Outcome();

        result.values = null;
        return result;

    }
}
