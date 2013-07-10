package eu.ydp.empiria.player.client.module.dragdrop;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class SourcelistManagerModelTest {

	SourcelistManagerModel model;

	SourcelistClient client1;
	SourcelistClient client2;
	SourcelistClient client3;
	Sourcelist sourcelist1;
	Sourcelist sourcelist2;

	@Before
	public void setUp() {
		model = new SourcelistManagerModel();

		client1 = mockClien("id1");
		client2 = mockClien("id2");
		client3 = mockClien("id3");

		sourcelist1 = mock(Sourcelist.class);
		sourcelist2 = mock(Sourcelist.class);
	}

	@Test
	public void shouldRegisterSingleClient() {
		// when
		model.addRelation(sourcelist1, client1);

		// then
		assertThat(model.getClients(sourcelist1).size(), is(equalTo(1)));
	}

	@Test
	public void shouldRegisterMultipleClients() {
		// when
		model.addRelation(sourcelist1, client1);
		model.addRelation(sourcelist1, client2);

		// then
		assertThat(model.getClients(sourcelist1).size(), is(equalTo(2)));
	}

	@Test
	public void shouldRegisterClientsForDiffrentSourcelists() {
		// when
		model.addRelation(sourcelist1, client1);
		model.addRelation(sourcelist1, client2);
		model.addRelation(sourcelist2, client3);

		// then
		assertThat(model.getClients(sourcelist1), hasItems(client1, client2));
		assertThat(model.getClients(sourcelist2), hasItem(client3));
	}

	@Test
	public void shouldRegisterRelationOnlyOnce() {
		// when
		model.addRelation(sourcelist1, client1);
		model.addRelation(sourcelist1, client1);

		// then
		assertThat(model.getClients().size(), is(equalTo(1)));
	}

	@Test
	public void shouldRegisterClientOnlyForOneSourcelist() {
		// when
		model.addRelation(sourcelist1, client1);
		model.addRelation(sourcelist1, client2);

		model.addRelation(sourcelist2, client1);
		model.addRelation(sourcelist2, client3);

		// then
		assertThat(model.getClients(sourcelist1).size(), is(equalTo(1)));
		assertThat(model.getClients(sourcelist2).size(), is(equalTo(2)));
	}

	@Test
	public void shouldLockGivenGroup() {
		// given
		model.addRelation(sourcelist1, client1);
		model.addRelation(sourcelist1, client2);
		model.addRelation(sourcelist2, client3);

		// when
		model.lockGroup(sourcelist1);

		// then
		assertThat(model.isGroupLocked(sourcelist1), is(true));
		assertThat(model.isGroupLocked(sourcelist2), is(false));
	}

	@Test
	public void shouldUnlockGivenGroup() {
		// given
		model.addRelation(sourcelist1, client1);
		model.addRelation(sourcelist1, client2);
		model.addRelation(sourcelist2, client3);

		// when
		model.lockGroup(sourcelist1);
		model.unlockGroup(sourcelist1);

		// then
		assertThat(model.isGroupLocked(sourcelist1), is(false));
		assertThat(model.isGroupLocked(sourcelist2), is(false));
	}

	private SourcelistClient mockClien(String clientId) {
		SourcelistClient client = mock(SourcelistClient.class);
		when(client.getIdentifier()).thenReturn(clientId);
		return client;
	}
}
