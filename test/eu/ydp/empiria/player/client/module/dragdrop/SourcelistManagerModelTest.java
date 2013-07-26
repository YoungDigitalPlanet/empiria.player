package eu.ydp.empiria.player.client.module.dragdrop;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class SourcelistManagerModelTest {

	private static final String CLIENT_1_ID = "id1";
	private static final String CLIENT_2_ID = "id2";
	private static final String CLIENT_3_ID = "id3";
	private static final String SOURCELIST_1_ID = "SRC_1";
	private static final String SOURCELIST_2_ID = "SRC_2";

	SourcelistManagerModel model;

	SourcelistClient client1;
	SourcelistClient client2;
	SourcelistClient client3;
	Sourcelist sourcelist1;
	Sourcelist sourcelist2;

	@Before
	public void setUp() {
		model = new SourcelistManagerModel();

		client1 = mockClien(CLIENT_1_ID, SOURCELIST_1_ID);
		client2 = mockClien(CLIENT_2_ID, SOURCELIST_1_ID);
		client3 = mockClien(CLIENT_3_ID, SOURCELIST_2_ID);

		sourcelist1 = mockSourcelist(SOURCELIST_1_ID);
		sourcelist2 = mockSourcelist(SOURCELIST_2_ID);
	}

	@Test
	public void shouldRegisterOnlyClientsWithSourcelist() {
		// given
		model.registerClient(client1);
		model.registerClient(client2);
		model.registerSourcelist(sourcelist1);
		model.registerClient(client3);

		// when
		model.bind();

		// then
		assertThat(model.containsClient(CLIENT_1_ID)).isTrue();
		assertThat(model.containsClient(CLIENT_2_ID)).isTrue();
		assertThat(model.containsClient(CLIENT_3_ID)).isFalse();
	}

	@Test
	public void shouldNotRegisterSourcelistWithoutClients() {
		// given
		model.registerSourcelist(sourcelist1);

		// when
		model.bind();

		// then
		assertThat(model.getSourceLists()).isEmpty();
	}

	@Test
	public void shouldReturnAllBindedSourcelists() {
		// given
		model.registerClient(client1);
		model.registerSourcelist(sourcelist1);
		model.registerClient(client3);
		model.registerSourcelist(sourcelist2);
		model.bind();

		// when
		Set<Sourcelist> sourceLists = model.getSourceLists();

		// then
		assertThat(sourceLists).containsOnly(sourcelist1, sourcelist2);
	}

	@Test
	public void shouldReturnClientsForSourcelist() {
		// given
		buildModel();

		// when
		Collection<SourcelistClient> clients = model.getClients(sourcelist1);

		// then
		assertThat(clients).containsOnly(client1, client2);
	}

	@Test
	public void shouldReturnClientById() {
		// given
		buildModel();

		// when
		SourcelistClient client = model.getClientById(CLIENT_2_ID);

		// then
		assertThat(client).isEqualTo(client2);
	}

	@Test
	public void shouldReturnSourcelistByClientId() {
		// given
		buildModel();

		// when
		Sourcelist sourcelist = model.getSourcelistByClientId(CLIENT_2_ID);

		// then
		assertThat(sourcelist).isEqualTo(sourcelist1);
	}

	@Test
	public void shouldReturnSourcelistById() {
		// given
		buildModel();

		// when
		Sourcelist sourcelist = model.getSourcelistById(SOURCELIST_2_ID);

		// then
		assertThat(sourcelist).isEqualTo(sourcelist2);
	}

	@Test
	public void shouldLockGivenGroup() {
		// given
		buildModel();

		// when
		model.lockGroup(sourcelist1);

		// then
		assertThat(model.isGroupLocked(sourcelist1)).isTrue();
		assertThat(model.isGroupLocked(sourcelist2)).isFalse();
	}

	@Test
	public void shouldUnlockGivenGroup() {
		// given
		buildModel();

		// when
		model.lockGroup(sourcelist1);
		model.unlockGroup(sourcelist1);

		// then
		assertThat(model.isGroupLocked(sourcelist1)).isFalse();
		assertThat(model.isGroupLocked(sourcelist2)).isFalse();
	}

	private void buildModel() {
		model.registerClient(client1);
		model.registerClient(client2);
		model.registerSourcelist(sourcelist1);
		model.registerClient(client3);
		model.registerSourcelist(sourcelist2);
		model.bind();
	}

	private SourcelistClient mockClien(String clientId, String sourcelistId) {
		SourcelistClient client = mock(SourcelistClient.class);
		when(client.getIdentifier()).thenReturn(clientId);
		when(client.sourceListId()).thenReturn(sourcelistId);
		return client;
	}

	private Sourcelist mockSourcelist(String sourcelistId) {
		Sourcelist sourcelist = mock(Sourcelist.class);
		when(sourcelist.getIdentifier()).thenReturn(sourcelistId);
		return sourcelist;
	}

}
