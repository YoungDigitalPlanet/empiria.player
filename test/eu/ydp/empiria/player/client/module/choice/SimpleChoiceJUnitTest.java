package eu.ydp.empiria.player.client.module.choice;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import eu.ydp.empiria.player.client.AbstractJAXBTestBase;
import eu.ydp.empiria.player.client.module.choice.structure.SimpleChoice;
import eu.ydp.gwtutil.client.StringUtils;

public class SimpleChoiceJUnitTest extends AbstractJAXBTestBase<SimpleChoice>{
	
	@Test
	public void shouldReturnSimpleChoice(){
		String nodeString = "<simpleChoice identifier=\"CHOICE_RESPONSE_1_2\">The Pyrenees</simpleChoice>";
		SimpleChoice simpleChoice = createBeanFromXMLString(nodeString);
		
		assertThat(simpleChoice.getIdentifier(), is(equalTo("CHOICE_RESPONSE_1_2")));
		assertThat(simpleChoice.getContent(), is(equalTo("The Pyrenees")));
	}
	
	@Test
	public void shouldReturnSimpleChoiceWhen_emptyValues(){
		String nodeString = "<simpleChoice/>";
		SimpleChoice simpleChoice = createBeanFromXMLString(nodeString);
		
		assertThat(simpleChoice.getIdentifier(), is(equalTo(StringUtils.EMPTY_STRING)));
		assertThat(simpleChoice.getContent(), is(equalTo(StringUtils.EMPTY_STRING)));
	}
}
