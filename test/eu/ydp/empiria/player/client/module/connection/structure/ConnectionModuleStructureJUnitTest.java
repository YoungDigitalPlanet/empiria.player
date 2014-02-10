package eu.ydp.empiria.player.client.module.connection.structure;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mockito.Mockito;

import eu.ydp.empiria.player.client.AbstractModuleStructureTestBase;
import eu.ydp.gwtutil.client.json.YJsonArray;

public class ConnectionModuleStructureJUnitTest extends
		AbstractModuleStructureTestBase<ConnectionModuleStructure, MatchInteractionBean, ConnectionModuleJAXBParser> {

	@Override
	protected ConnectionModuleStructure createModuleStructure() {
		return new ConnectionModuleStructureMock();
	}

	@Test
	public void shouldInitializeStructure() {

		MatchInteractionBean bean = createFromXML(ConnectionModuleStructureMock.CONNECTION_XML, Mockito.mock(YJsonArray.class));
		assertThat(bean.getId(), is(equalTo("dummy1")));
	}
}
