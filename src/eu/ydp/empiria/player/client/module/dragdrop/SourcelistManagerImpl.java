package eu.ydp.empiria.player.client.module.dragdrop;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.time.TemporaryFlag;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

public class SourcelistManagerImpl implements SourcelistManager, PlayerEventHandler {

	private final static int DRAG_END_LOCKED_TIME = 50;

	@Inject
	@PageScoped
	private SourcelistManagerModel model;
	@Inject
	private EventsBus eventsBus;

	@Inject
	@PageScoped
	private SourcelistLockingController sourcelistLockingController;

	@Inject
	private TemporaryFlag dragEndLocked;

	private final Function<SourcelistClient, String> clientToItemid = new Function<SourcelistClient, String>() {

		@Override
		public String apply(SourcelistClient client) {
			return client.getDragItemId();
		}
	};

	@PostConstruct
	public void init() {
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_CONTENT_RESIZED), this, new CurrentPageScope());
	}

	@Override
	public void registerModule(SourcelistClient client) {
		model.registerClient(client);
	}

	@Override
	public void registerSourcelist(Sourcelist sourcelist) {
		model.registerSourcelist(sourcelist);
	}

	@Override
	public void dragStart(String sourceModuleId) {
		Sourcelist sourcelist;
		if (model.containsClient(sourceModuleId)) {
			sourcelist = model.getSourcelistByClientId(sourceModuleId);
		} else {
			sourcelist = model.getSourcelistById(sourceModuleId);
		}
		sourcelistLockingController.lockOthers(sourcelist);
	}

	@Override
	public void dragEnd(String itemId, String sourceModuleId, String targetModuleId) {
		if (!dragEndLocked.isSet() && !sourceModuleId.equals(targetModuleId)) {
			moveItemFromSourceToTarget(itemId, sourceModuleId, targetModuleId);
			dragEndLocked.setFor(DRAG_END_LOCKED_TIME);
		}
	}

	private void moveItemFromSourceToTarget(String itemId, String sourceModuleId, String targetModuleId) {
		Sourcelist sourcelist = model.getSourcelistByClientId(targetModuleId);
		SourcelistClient targetClient = model.getClientById(targetModuleId);

		String previousItemid = targetClient.getDragItemId();
		sourcelist.restockItem(previousItemid);

		targetClient.setDragItem(itemId);

		if (model.containsClient(sourceModuleId)) {
			SourcelistClient sourceClient = model.getClientById(sourceModuleId);
			sourceClient.removeDragItem();
		} else {
			sourcelist.useItem(itemId);
		}

		unlockAll();
	}

	private void unlockAll() {
		sourcelistLockingController.unlockAll();
	}

	@Override
	public void dragEndSourcelist(String itemId, String sourceModuleId) {
		if (model.containsClient(sourceModuleId)) {
			SourcelistClient sourceClient = model.getClientById(sourceModuleId);
			Sourcelist sourcelist = model.getSourcelistByClientId(sourceModuleId);

			sourceClient.removeDragItem();
			sourcelist.restockItem(itemId);
		} else {
			Sourcelist sourcelist = model.getSourcelistById(sourceModuleId);

			sourcelist.useItem(itemId);
			sourcelist.restockItem(itemId);
		}

		unlockAll();
	}

	@Override
	public void dragFinished() {
		unlockAll();
	}

	@Override
	public SourcelistItemValue getValue(String itemId, String targetModuleId) {
		Sourcelist sourcelist = model.getSourcelistByClientId(targetModuleId);
		SourcelistItemValue value = sourcelist.getItemValue(itemId);
		return value;
	}

	@Override
	public void onUserValueChanged() {
		for (Sourcelist sourcelist : model.getSourceLists()) {
			Collection<SourcelistClient> clients = model.getClients(sourcelist);

			List<String> clientsToItemsIds = clientsToItemsIds(clients);
			sourcelist.useAndRestockItems(clientsToItemsIds);
		}
	}

	private List<String> clientsToItemsIds(Collection<SourcelistClient> clients) {
		Collection<String> items = Collections2.transform(clients, clientToItemid);
		return Lists.newArrayList(items);
	}

	@Override
	public void lockGroup(String clientId) {
		sourcelistLockingController.lockGroup(clientId);
	}

	@Override
	public void unlockGroup(String clientId) {
		sourcelistLockingController.unlockGroup(clientId);
	}

	@Override
	public void onPlayerEvent(PlayerEvent event) {
		resizeSourcelists();
		restoreSourcelistsState();
	}

	private void resizeSourcelists() {
		for (Sourcelist sourcelist : model.getSourceLists()) {
			HasDimensions size = sourcelist.getItemSize();

			if (size != null) {
				resizeClients(sourcelist, size);
			}
		}
	}

	private void resizeClients(Sourcelist sourcelist, HasDimensions size) {
		for (SourcelistClient client : model.getClients(sourcelist)) {
			client.setSize(size);
		}
	}

	private void restoreSourcelistsState() {
		for (Sourcelist sourcelist : model.getSourceLists()) {
			restoreSourcelistStateFromClients(sourcelist);
		}
	}

	private void restoreSourcelistStateFromClients(Sourcelist sourcelist) {
		Collection<SourcelistClient> clients = model.getClients(sourcelist);
		for (SourcelistClient client : clients) {
			String itemId = client.getDragItemId();
			sourcelist.useItem(itemId);
		}
	}
}
