package eu.ydp.empiria.player.client.module.connection.presenter;

import java.util.Map;

import javax.inject.Inject;

import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.xml.XMLParser;

public class ConnectionModuleViewStyles {
	private final StyleSocket styleSocket;
	private final ConnectionStyleXMLElementCache cache;

	@Inject
	public ConnectionModuleViewStyles(StyleSocket styleSocket, StyleNameConstants styleNames, XMLParser xmlParser) {
		this.styleSocket = styleSocket;
		cache = new ConnectionStyleXMLElementCache(styleNames, xmlParser);
	}

	public Map<String, String> getStyles(MultiplePairModuleConnectType type) {
		return styleSocket.getOrgStyles(cache.getOrCreateAndPut(type));
	}
}
