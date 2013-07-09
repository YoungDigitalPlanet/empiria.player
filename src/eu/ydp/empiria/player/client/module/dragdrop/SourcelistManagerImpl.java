package eu.ydp.empiria.player.client.module.dragdrop;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class SourcelistManagerImpl implements SourcelistManager {

	@Inject private SourcelistManagerModel model;
	@Inject private SourcelistManagerHelper helper;

	private final Function<SourcelistClient, String> clientToItemid = new Function<SourcelistClient, String>() {

		@Override
		public String apply(SourcelistClient client) {
			return client.getDragItemId();
		}
	};

	@Override
	public void registerModule(SourcelistClient client) {
		Optional<Sourcelist> sourcelist = helper.findSourcelist(client);
		if (sourcelist.isPresent()) {
			model.addRelation(sourcelist.get(), client);
		}
	}

	@Override
	public void registerSourcelist(Sourcelist sourcelist) {
		List<SourcelistClient> clients = helper.findClients(sourcelist);
		for (SourcelistClient client : clients) {
			model.addRelation(sourcelist, client);
		}
	}

	@Override
	public void dragStart(String sourceModuleId) {
		Sourcelist sourcelist;
		if (model.containsClient(sourceModuleId)) {
			sourcelist = model.getSourcelistByClientId(sourceModuleId);
		} else {
			sourcelist = model.getSourcelistById(sourceModuleId);
		}
		lockOthers(sourcelist);
	}

	private void lockGroup(Sourcelist sourcelist) {
		sourcelist.lockSourceList();
		lockClients(sourcelist);
	}

	private void lockOthers(Sourcelist sourcelist) {
		for (Sourcelist src : model.getSourceLists()) {
			if (!sourcelist.equals(src)) {
				src.lockSourceList();
				lockClients(src);
			}
		}
	}

	private void lockClients(Sourcelist sourcelist) {
		Collection<SourcelistClient> clients = model.getClients(sourcelist);
		for (SourcelistClient client : clients) {
			client.lockDropZone();
		}
	}

	private void unlockGroup(Sourcelist sourcelist) {
		sourcelist.unlockSourceList();
		unlockClients(sourcelist);
	}

	private void unlockAll() {
		for (Sourcelist sourcelist : model.getSourceLists()) {
			unlockGroupIfNotBlocked(sourcelist);
		}
	}

	private void unlockGroupIfNotBlocked(Sourcelist sourcelist) {
		if (!model.isGroupLocked(sourcelist)) {
			sourcelist.unlockSourceList();
			unlockClients(sourcelist);
		}
	}

	private void unlockClients(Sourcelist sourcelist) {
		Collection<SourcelistClient> clients = model.getClients(sourcelist);
		for (SourcelistClient client : clients) {
			client.unlockDropZone();
		}
	}

	@Override
	public void dragEnd(String itemId, String sourceModuleId, String targetModuleId) {
		if (!sourceModuleId.equals(targetModuleId)) {
			moveItemFromSourceToTarget(itemId, sourceModuleId, targetModuleId);
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
	public String getValue(String itemId, String targetModuleId) {
		Sourcelist sourcelist = model.getSourcelistByClientId(targetModuleId);
		String value = sourcelist.getItemValue(itemId);

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
		if (model.containsClient(clientId)) {
			Sourcelist sourcelist = model.getSourcelistByClientId(clientId);
			lockGroup(sourcelist);
			model.lockGroup(sourcelist);
		}
	}

	@Override
	public void unlockGroup(String clientId) {
		if (model.containsClient(clientId)) {
			Sourcelist sourcelist = model.getSourcelistByClientId(clientId);
			unlockGroup(sourcelist);
			model.unlockGroup(sourcelist);
		}
	}

}
