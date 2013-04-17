package eu.ydp.empiria.player.client.module.connection.structure;

import eu.ydp.empiria.player.client.AbstractModuleStructureTestBase;

public class ConnectionModuleStructureJUnitTest extends
		AbstractModuleStructureTestBase<ConnectionModuleStructure, MatchInteractionBean, ConnectionModuleJAXBParser> {

	@Override
	protected ConnectionModuleStructure createModuleStructure() {
		return new ConnectionModuleStructureMock();
	}

	// @Test
	// public void shouldInitializeStructure() {
	// MatchInteractionBean bean =
	// createFromXML(ConnectionModuleStructureMock.CONNECTION_XML,
	// Mockito.mock(Optional.class));
	// assertThat(bean.getId(), is(equalTo("dummy1")));
	// }
}
