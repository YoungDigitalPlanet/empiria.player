package eu.ydp.empiria.player.client.module.connection.structure;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import eu.ydp.empiria.player.client.AbstractJAXBTestBase;

public class MatchInteractionBeanJUnitTest extends AbstractJAXBTestBase<MatchInteractionBean> {
	
	@Test
	public void shouldReturnMartchInteraction(){
		String xmlString = 
				"<matchInteraction id=\"dummy1\" responseIdentifier=\"CONNECTION_RESPONSE_1\" shuffle=\"true\">" +
				"<simpleMatchSet>" +
					"<simpleAssociableChoice fixed=\"false\" identifier=\"CONNECTION_RESPONSE_1_0\" matchMax=\"2\">" +
						"a	</simpleAssociableChoice>" +
					"<simpleAssociableChoice fixed=\"false\" identifier=\"CONNECTION_RESPONSE_1_3\" matchMax=\"2\">" +
						"b	</simpleAssociableChoice>" +
					"</simpleMatchSet>" +
					"<simpleMatchSet>" +
						"<simpleAssociableChoice fixed=\"false\" identifier=\"CONNECTION_RESPONSE_1_4\" matchMax=\"2\">" +
							"c	</simpleAssociableChoice>" +
						"<simpleAssociableChoice fixed=\"false\" identifier=\"CONNECTION_RESPONSE_1_1\" matchMax=\"2\">" +
							"d	</simpleAssociableChoice>" +
						"</simpleMatchSet>" +
				"</matchInteraction>";

		MatchInteractionBean matchInteractionBean = createBeanFromXMLString(xmlString);	
				
		assertThat(matchInteractionBean.getId(), is(equalTo("dummy1")));
		assertThat(matchInteractionBean.isShuffle(), is(equalTo(true)));
		assertThat(matchInteractionBean.getResponseIdentifier(), is(equalTo("CONNECTION_RESPONSE_1")));
		
		assertThat(matchInteractionBean.getSimpleMatchSets().size(), is(equalTo(2)));
		
		assertThat(matchInteractionBean.getSourceChoicesSet(), notNullValue());
		assertThat(matchInteractionBean.getTargetChoicesSet(), notNullValue());
		
		assertThat(matchInteractionBean.getSourceChoicesSet().get(0).getIdentifier(), is(equalTo("CONNECTION_RESPONSE_1_0")));
		assertThat(matchInteractionBean.getSourceChoicesSet().get(0).isFixed(), is(equalTo(false)));
		assertThat(matchInteractionBean.getSourceChoicesSet().get(0).getMatchMax(), is(equalTo(2)));
		assertThat(matchInteractionBean.getSourceChoicesSet().get(1).getIdentifier(), is(equalTo("CONNECTION_RESPONSE_1_3")));
	}	
}
