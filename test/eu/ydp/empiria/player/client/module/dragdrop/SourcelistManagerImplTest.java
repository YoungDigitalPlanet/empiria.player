package eu.ydp.empiria.player.client.module.dragdrop;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@RunWith(MockitoJUnitRunner.class)
public class SourcelistManagerImplTest {

	@InjectMocks
	SourcelistManagerImpl manager;
	@Mock
	SourcelistManagerHelper helper;
	@Mock
	SourcelistManagerModel model;
	@Mock
	SourcelistClient client1;
	@Mock
	SourcelistClient client2;
	@Mock
	SourcelistClient client3;
	@Mock
	Sourcelist sourcelist1;
	@Mock
	Sourcelist sourcelist2;
	private final String CLIENT_1_ID = "id1";
	private final String CLIENT_2_ID = "id2";
	private final String CLIENT_3_ID = "id3";
	
	private final String ITEM_1_ID = "item1";
	private final String ITEM_2_ID = "item2";
	private final String ITEM_3_ID = "item3";
	
	private final String SOURCELIST_1_ID = "SOURCELIST_1_ID";
	private final String SOURCELIST_2_ID = "SOURCELIST_2_ID";

	@Before
	public void setUp() {
		client1 = mockClient(CLIENT_1_ID);
		client2 = mockClient(CLIENT_2_ID);
		client3 = mockClient(CLIENT_3_ID);

		prepareModel();
	}

	@Test
	public void shouldNotRegisterClientWithoutSourcelist() {
		// given
		when(helper.findSourcelist(client1)).thenReturn(
				Optional.<Sourcelist> absent());

		// when
		manager.registerModule(client1);

		// then
		verify(model, never()).addRelation(any(Sourcelist.class), eq(client1));
	}

	@Test
	public void shouldRegisterClientWithSourcelist() {
		// given

		when(helper.findSourcelist(client1)).thenReturn(
				Optional.of(sourcelist1));

		// when
		manager.registerModule(client1);

		// then
		verify(model).addRelation(sourcelist1, client1);
	}

	@Test
	public void shouldNotRegisterSourcelistWithoutClients() {
		// given
		when(helper.findClients(sourcelist1)).thenReturn(
				Lists.<SourcelistClient> newArrayList());

		// when
		manager.registerSourcelist(sourcelist1);

		// then
		verify(model, never()).addRelation(eq(sourcelist1),
				any(SourcelistClient.class));
	}

	@Test
	public void shouldRegisterSourcelistWithClients() {
		// given
		when(helper.findClients(sourcelist1)).thenReturn(
				Lists.newArrayList(client1, client2));

		// when
		manager.registerSourcelist(sourcelist1);

		// then
		verify(model, times(2)).addRelation(eq(sourcelist1),
				any(SourcelistClient.class));
	}

	@Test
	public void shouldLockUnrelatedClientsOnDragStart() {
		// when
		manager.dragStart(CLIENT_1_ID);

		// then
		verify(sourcelist1, never()).lockSourceList();
		verify(sourcelist2).lockSourceList();
		verify(client1, never()).lockDropZone();
		verify(client2).lockDropZone();
	}

	@Test
	public void shouldUnlockAllClientsOnDragCanceled() {
		// when
		manager.dragCanceled();

		// then
		verify(sourcelist1).unlockSourceList();
		verify(sourcelist2).unlockSourceList();
		verify(client1).unlockDropZone();
		verify(client2).unlockDropZone();
	}

	@Test
	public void shouldMoveItemBetweenClients() {
		// given
		final String newId = "item_2";
		final String oldId = "item_3";

		when(client3.getDragItemId()).thenReturn(oldId);
		
		// when
		manager.dragEnd(newId, CLIENT_2_ID, CLIENT_3_ID);

		// then
		verify(client3).setDragItem(newId);
		verify(client2).removeDragItem();
		verify(sourcelist2).restockItem(oldId);
	}

	@Test
	public void shouldMoveItemFromSourcelist() {
		// when
		manager.dragEnd(ITEM_2_ID, SOURCELIST_2_ID, CLIENT_3_ID);

		// then
		verify(client3).setDragItem(ITEM_2_ID);
		verify(sourcelist2).useItem(ITEM_2_ID);
		verify(sourcelist2).restockItem(ITEM_3_ID);
	}

	@Test
	public void shouldMoveItemToSourcelist() {
		// when
		manager.dragEndSourcelist(ITEM_2_ID, CLIENT_2_ID);

		// then
		verify(sourcelist2).restockItem(ITEM_2_ID);
		verify(client2).removeDragItem();
	}
	
	@Test
	public void shouldMoveItemFromSourcelistToItself(){
		//when 
		manager.dragStart(SOURCELIST_2_ID);
		manager.dragEndSourcelist(ITEM_2_ID, SOURCELIST_2_ID);
		
		//then
		verify(sourcelist2).useItem(ITEM_2_ID);
		verify(sourcelist2).restockItem(ITEM_2_ID);
	}

	@Test
	public void shouldReturnValueFromSourcelist() {
		// when
		manager.getValue(ITEM_2_ID, CLIENT_3_ID);

		// then
		verify(sourcelist2).getItemValue(ITEM_2_ID);
		verifyNoMoreInteractions(sourcelist1);
	}

	private SourcelistClient mockClient(String string) {
		SourcelistClient client = mock(SourcelistClient.class);
		when(client.getIdentifier()).thenReturn(string);
		return client;
	}

	@Test
	public void shouldRestockAllItems() {
		// given
		ArrayList<String> sourcelist1ItemsIds = Lists.newArrayList(ITEM_1_ID);
		ArrayList<String> sourcelist2ItemsIds = Lists.newArrayList(ITEM_2_ID, ITEM_3_ID);
		
		// when
		manager.onUserValueChanged();

		// then
		verify(sourcelist1).useAndRestockItems(sourcelist1ItemsIds);
		verify(sourcelist2).useAndRestockItems(sourcelist2ItemsIds);
	}

	private void prepareModel() {
		when(sourcelist1.getIdentifier()).thenReturn(SOURCELIST_1_ID);
		when(sourcelist2.getIdentifier()).thenReturn(SOURCELIST_2_ID);
		when(client1.getDragItemId()).thenReturn(ITEM_1_ID);
		when(client2.getDragItemId()).thenReturn(ITEM_2_ID);
		when(client3.getDragItemId()).thenReturn(ITEM_3_ID);
		when(model.containsClient(CLIENT_1_ID)).thenReturn(true);
		when(model.containsClient(CLIENT_2_ID)).thenReturn(true);
		when(model.containsClient(CLIENT_3_ID)).thenReturn(true);
		when(model.getClientById(CLIENT_1_ID)).thenReturn(client1);
		when(model.getClientById(CLIENT_2_ID)).thenReturn(client2);
		when(model.getClientById(CLIENT_3_ID)).thenReturn(client3);
		when(model.getSourcelistById(SOURCELIST_1_ID)).thenReturn(sourcelist1);
		when(model.getSourcelistById(SOURCELIST_2_ID)).thenReturn(sourcelist2);
		when(model.getSourceLists()).thenReturn(
				Sets.newHashSet(sourcelist1, sourcelist2));
		when(model.getSourcelistByClientId(CLIENT_1_ID))
				.thenReturn(sourcelist1);
		when(model.getSourcelistByClientId(CLIENT_2_ID))
				.thenReturn(sourcelist2);
		when(model.getSourcelistByClientId(CLIENT_3_ID))
				.thenReturn(sourcelist2);
		when(model.getClients(sourcelist1)).thenReturn(
				Lists.newArrayList(client1));
		when(model.getClients(sourcelist2)).thenReturn(
				Lists.newArrayList(client2, client3));
		when(model.getClients()).thenReturn(Sets.newHashSet(client1, client2));
	}
}
