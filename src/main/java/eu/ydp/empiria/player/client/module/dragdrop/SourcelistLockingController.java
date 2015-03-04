package eu.ydp.empiria.player.client.module.dragdrop;

import java.util.Collection;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;

public class SourcelistLockingController {

	@Inject
	@PageScoped
	private SourcelistManagerModel model;

	public void lockOthers(Sourcelist sourcelist) {
		for (Sourcelist src : model.getSourceLists()) {
			if (!sourcelist.equals(src)) {
				src.lockSourceList();
				lockClients(src);
			}
		}
	}

	private void lockClients(Sourcelist src) {
		Collection<SourcelistClient> clients = model.getClients(src);
		for (SourcelistClient client : clients) {
			client.lockDropZone();
		}
	}

	public void unlockAll() {
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

	public void unlockClients(Sourcelist sourcelist) {
		Collection<SourcelistClient> clients = model.getClients(sourcelist);
		for (SourcelistClient client : clients) {
			client.unlockDropZone();
		}
	}

	public void lockGroup(String clientId) {
		if (model.containsClient(clientId)) {
			Sourcelist sourcelist = model.getSourcelistByClientId(clientId);
			lockGroup(sourcelist);
			model.lockGroup(sourcelist);
		}
	}

	private void lockGroup(Sourcelist sourcelist) {
		sourcelist.lockSourceList();
		lockClients(sourcelist);
	}

	public void unlockGroup(String clientId) {
		if (model.containsClient(clientId)) {
			Sourcelist sourcelist = model.getSourcelistByClientId(clientId);
			unlockGroup(sourcelist);
			model.unlockGroup(sourcelist);
		}
	}

	private void unlockGroup(Sourcelist sourcelist) {
		sourcelist.unlockSourceList();
		unlockClients(sourcelist);
	}
}
