package eu.ydp.empiria.player.client.module.dragdrop;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IModule;

public class SourcelistManagerHelperTest {

	SourcelistManagerHelper helper;

	@Before
	public void setUp() {
		helper = new SourcelistManagerHelper();
	}

	@Test
	public void shouldNotFindSourcelist() {
		// given
		SourcelistClient client = mockClient("id1");
		mockClientParent(client);

		// when
		Optional<Sourcelist> foundSourcelist = helper.findSourcelist(client);

		// then
		assertThat(foundSourcelist.isPresent(), is(equalTo(false)));
	}

	@Test
	public void shouldFindSourcelistInDirectParent() {
		// given
		SourcelistClient client = mockClient("id1");
		HasChildren parentModule = mockClientParent(client);
		Sourcelist sourcelist = mock(Sourcelist.class);

		when(parentModule.getChildrenModules()).thenReturn(
				Lists.<IModule> newArrayList(client, sourcelist));

		// when
		Optional<Sourcelist> foundSourcelist = helper.findSourcelist(client);

		// then
		assertThat(foundSourcelist.isPresent(), is(equalTo(true)));
		assertThat(foundSourcelist.get(), is(equalTo(sourcelist)));
	}

	@Test
	public void shouldFindSourcelistInIndirectParent() {
		// given
		SourcelistClient client = mockClient("id1");
		HasChildren parentModule = mockClientParent(client);
		HasChildren parentModule2 = mock(HasChildren.class);
		Sourcelist sourcelist = mock(Sourcelist.class);

		when(parentModule.getParentModule()).thenReturn(parentModule2);
		when(parentModule2.getChildrenModules()).thenReturn(
				Lists.<IModule> newArrayList(parentModule, sourcelist));

		// when
		Optional<Sourcelist> foundSourcelist = helper.findSourcelist(client);

		// then
		assertThat(foundSourcelist.isPresent(), is(equalTo(true)));
		assertThat(foundSourcelist.get(), is(equalTo(sourcelist)));
	}

	@Test
	public void shouldFindOnlyFirstSourcelist() {
		// given
		SourcelistClient client = mockClient("id1");
		HasChildren parentModule = mockClientParent(client);
		Sourcelist sourcelist1 = mock(Sourcelist.class);
		Sourcelist sourcelist2 = mock(Sourcelist.class);

		when(parentModule.getChildrenModules()).thenReturn(
				Lists.<IModule> newArrayList(client, sourcelist1, sourcelist2));

		// when
		Optional<Sourcelist> foundSourcelist = helper.findSourcelist(client);

		// then
		assertThat(foundSourcelist.isPresent(), is(equalTo(true)));
		assertThat(foundSourcelist.get(), is(sourcelist1));
	}

	@Test
	public void shouldFindSingleClient() {
		// given
		Sourcelist sourcelist = mock(Sourcelist.class);
		SourcelistClient client = mockClient("id1");

		HasChildren parentModule = mockSourcelistParent(sourcelist);
		when(parentModule.getChildrenModules()).thenReturn(
				Lists.<IModule> newArrayList(sourcelist, client));

		// when
		List<SourcelistClient> clients = helper.findClients(sourcelist);

		// then
		assertThat(clients.size(), is(equalTo(1)));
	}

	@Test
	public void shouldFindMultipleClients() {
		// given
		Sourcelist sourcelist = mock(Sourcelist.class);
		SourcelistClient client = mockClient("id1");
		SourcelistClient client2 = mockClient("id2");

		HasChildren parentModule = mockSourcelistParent(sourcelist);
		when(parentModule.getChildrenModules()).thenReturn(
				Lists.<IModule> newArrayList(sourcelist, client, client2));

		// when
		List<SourcelistClient> clients = helper.findClients(sourcelist);

		// then
		assertThat(clients.size(), is(equalTo(2)));
	}

	@Test
	public void shouldNotFindClients() {
		// given
		Sourcelist sourcelist = mock(Sourcelist.class);
		HasChildren parentModule = mockSourcelistParent(sourcelist);

		when(parentModule.getChildrenModules()).thenReturn(
				Lists.<IModule> newArrayList(sourcelist));

		// when
		List<SourcelistClient> clients = helper.findClients(sourcelist);

		// then
		assertThat(clients.size(), is(equalTo(0)));
	}

	private SourcelistClient mockClient(String id) {
		SourcelistClient client = mock(SourcelistClient.class);
		when(client.getIdentifier()).thenReturn(id);
		return client;
	}

	private HasChildren mockClientParent(SourcelistClient client) {
		HasChildren parentModule = mock(HasChildren.class);
		when(client.getParentModule()).thenReturn(parentModule);
		when(parentModule.getChildrenModules()).thenReturn(
				Lists.<IModule> newArrayList(client));
		return parentModule;
	}

	private HasChildren mockSourcelistParent(Sourcelist sourcelist) {
		HasChildren parentModule = mock(HasChildren.class);
		when(sourcelist.getParentModule()).thenReturn(parentModule);
		when(parentModule.getChildrenModules()).thenReturn(
				Lists.<IModule> newArrayList(sourcelist));
		return parentModule;
	}

}
