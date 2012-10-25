package eu.ydp.empiria.player.client.module.connection.structure;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import eu.ydp.empiria.player.client.AbstractModuleStructureTestBase;

public class ConnectionModuleStructureJUnitTest extends AbstractModuleStructureTestBase<ConnectionModuleStructure, MatchInteractionBean, ConnectionModuleJAXBParser> {

	@Override
	protected ConnectionModuleStructure createModuleStructure() {
		return new ConnectionModuleStructureMock();
	}

	@Test
	public void shouldInitializeStructure() {
		MatchInteractionBean bean = createFromXML(ConnectionModuleStructureMock.CONNECTION_XML);
		assertThat(bean.getId(), is(equalTo("dummy1")));
	}
 }
