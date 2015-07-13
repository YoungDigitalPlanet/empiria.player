package eu.ydp.empiria.player.client.module.dragdrop;

import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SourcelistGroupTest {

    SourcelistGroup group;

    @Before
    public void setUp() throws Exception {
        group = new SourcelistGroup();
    }

    @Test
    public void shouldReturnClientById() {
        // given
        SourcelistClient client1 = mockClient("client1");
        SourcelistClient client2 = mockClient("client2");
        SourcelistClient client3 = mockClient("client3");
        group.addClient(client1);
        group.addClient(client2);
        group.addClient(client3);

        // when
        Optional<SourcelistClient> optionalClient = group.getClientById("client2");

        // then
        assertThat(optionalClient.isPresent()).isTrue();
        assertThat(optionalClient.get()).isEqualTo(client2);
    }

    private SourcelistClient mockClient(String clientId) {
        SourcelistClient client = mock(SourcelistClient.class);
        when(client.getIdentifier()).thenReturn(clientId);
        return client;
    }

}
