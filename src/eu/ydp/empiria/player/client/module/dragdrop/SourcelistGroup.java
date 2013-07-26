package eu.ydp.empiria.player.client.module.dragdrop;

import java.util.List;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

class SourcelistGroup {

	private Sourcelist sourcelist;
	private List<SourcelistClient> clients = Lists.newArrayList();
	private boolean locked;

	public void addClient(SourcelistClient client) {
		clients.add(client);
	}

	public List<SourcelistClient> getClients() {
		return clients;
	}

	public void setSourcelist(Sourcelist sourcelist) {
		this.sourcelist = sourcelist;
	}

	public Sourcelist getSourcelist() {
		return sourcelist;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isLocked() {
		return locked;
	}

	public Optional<SourcelistClient> getClientById(String clientid) {
		Predicate<SourcelistClient> idPredicates = getClientIdPredicates(clientid);
		return Iterables.tryFind(clients, idPredicates);
	}

	private Predicate<SourcelistClient> getClientIdPredicates(final String clientId) {
		return new Predicate<SourcelistClient>() {

			@Override
			public boolean apply(SourcelistClient client) {
				return client.getIdentifier().equals(clientId);
			}
		};
	}
}