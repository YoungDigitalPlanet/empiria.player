package eu.ydp.empiria.player.client.module.connection.structure;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.base.Optional;

import eu.ydp.empiria.player.client.AbstractModuleStructureTestBase;

public class ConnectionModuleStructureJUnitTest extends
		AbstractModuleStructureTestBase<ConnectionModuleStructure, MatchInteractionBean, ConnectionModuleJAXBParser> {

	@Override
	protected ConnectionModuleStructure createModuleStructure() {
		return new ConnectionModuleStructureMock();
	}

	@Test
	public void shouldInitializeStructure() {
		MatchInteractionBean bean = createFromXML(ConnectionModuleStructureMock.CONNECTION_XML, Mockito.mock(Optional.class));
		assertThat(bean.getId(), is(equalTo("dummy1")));
	}
}
