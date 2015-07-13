package eu.ydp.empiria.player.client.module.connection.structure;

import eu.ydp.empiria.player.client.AbstractModuleStructureTestBase;
import eu.ydp.gwtutil.client.json.YJsonArray;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
