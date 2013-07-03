package eu.ydp.empiria.player.client.module.dragdrop;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

public class SourcelistManagerModel {

	private Map<String, SourcelistClient> clientIdCache = Maps.newHashMap();
	private Map<String, Sourcelist> sourcelistIdCache = Maps.newHashMap();
	private Map<SourcelistClient, Sourcelist> mapClientToSourcelist = Maps
			.newHashMap();
	private Multimap<Sourcelist, SourcelistClient> mapSourceListToClients = HashMultimap
			.create();

	public void addRelation(Sourcelist sourcelist, SourcelistClient client) {
		Sourcelist currentSourcelist = getSourcelistByClientId(client
				.getIdentifier());
		if (currentSourcelist != null) {
			mapSourceListToClients.get(currentSourcelist).remove(client);
		}
		mapClientToSourcelist.put(client, sourcelist);
		clientIdCache.put(client.getIdentifier(), client);
		sourcelistIdCache.put(sourcelist.getIdentifier(), sourcelist);
		mapSourceListToClients.put(sourcelist, client);
	}

	public Set<Sourcelist> getSourceLists() {
		return mapSourceListToClients.keySet();
	}

	public Collection<SourcelistClient> getClients(Sourcelist sourcelist) {
		return mapSourceListToClients.get(sourcelist);
	}

	public Collection<SourcelistClient> getClients() {
		return clientIdCache.values();
	}

	public SourcelistClient getClientById(String clientId) {
		return clientIdCache.get(clientId);
	}

	public Sourcelist getSourcelistByClientId(String clientId) {
		SourcelistClient client = clientIdCache.get(clientId);

		return mapClientToSourcelist.get(client);
	}

	public boolean containsClient(String clientId) {
		return clientIdCache.containsKey(clientId);
	}

	public Sourcelist getSourcelistById(String sourcelistId) {
		return sourcelistIdCache.get(sourcelistId);
	}
}
