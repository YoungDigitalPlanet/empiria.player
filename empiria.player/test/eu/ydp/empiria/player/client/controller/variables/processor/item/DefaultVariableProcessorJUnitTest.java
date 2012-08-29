package eu.ydp.empiria.player.client.controller.variables.processor.item;

import java.util.List;
import java.util.Vector;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import static org.mockito.Mockito.*;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.gwtutil.client.collections.ListCreator;

public class DefaultVariableProcessorJUnitTest {

	private DefaultVariableProcessor dvp = new DefaultVariableProcessor();
	private Response response = mock(Response.class);
	
	@Test
	public void testFindSingleResponseErrors(){
		verifyErrorsCount(0, 0, Cardinality.SINGLE, ListCreator.create(new Vector<String>()).add("").build());
		verifyErrorsCount(1, 1, Cardinality.SINGLE, ListCreator.create(new Vector<String>()).add("x").build());
		verifyErrorsCount(0, 0, Cardinality.MULTIPLE, ListCreator.create(new Vector<String>()).build());
		verifyErrorsCount(1, 1, Cardinality.MULTIPLE, ListCreator.create(new Vector<String>()).add("x").add("y").build());
		verifyErrorsCount(0, 0, Cardinality.COMMUTATIVE, ListCreator.create(new Vector<String>()).build());
		verifyErrorsCount(1, 1, Cardinality.COMMUTATIVE, ListCreator.create(new Vector<String>()).add("x").add("y").build());
		verifyErrorsCount(0, 0, Cardinality.ORDERED, ListCreator.create(new Vector<String>()).build());
		verifyErrorsCount(0, 1, Cardinality.ORDERED, ListCreator.create(new Vector<String>()).add("x").add("y").build());
	}

	private void verifyErrorsCount(int expectedErrorsCountNoInteraction, int expectedErrorsCountWithInteraction, Cardinality card, List<String> values){
		response.values = (Vector<String>)values;
		response.cardinality = card;
		assertThat( dvp.findSingleResponseErrors(response, false, false), equalTo( expectedErrorsCountNoInteraction ));
		assertThat( dvp.findSingleResponseErrors(response, false, true), equalTo( expectedErrorsCountWithInteraction ));
		assertThat( dvp.findSingleResponseErrors(response, true, false), equalTo(0));
		assertThat( dvp.findSingleResponseErrors(response, true, true), equalTo(0));
	}
}
