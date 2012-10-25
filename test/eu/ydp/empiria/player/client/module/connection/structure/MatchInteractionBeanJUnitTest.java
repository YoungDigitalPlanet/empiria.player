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
		MatchInteractionBean matchInteractionBean = createBeanFromXMLString(ConnectionModuleStructureMock.CONNECTION_XML);

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
