package eu.ydp.empiria.player.client.module.connection.presenter;

import java.util.Set;

import com.google.common.collect.Sets;

import eu.ydp.gwtutil.client.collections.KeyValue;

public class ConnectionManager {

	ConnectionItems connectionItems;
	ConnectionSurfacesManager connectionSurfacesManager;

	private final Set<KeyValue<String, String>> connections = Sets.newHashSet();

	private final KeyValue<String, String> lastPair = new KeyValue<String, String>();

	public void setStartItem(String startItemId){
		lastPair.setKey(startItemId);
	}

	public void setEndItemAndCreateConnection(String endItem){
		lastPair.setValue(endItem);
	}

	public void setConnectionItems(ConnectionItems connectionItems) {
		this.connectionItems = connectionItems;
	}

	public void setConnectionSurfacesManager(ConnectionSurfacesManager connectionSurfacesManager) {
		this.connectionSurfacesManager = connectionSurfacesManager;
	}




}
