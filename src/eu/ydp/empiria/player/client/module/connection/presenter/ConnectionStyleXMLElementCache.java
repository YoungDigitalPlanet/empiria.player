package eu.ydp.empiria.player.client.module.connection.presenter;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.view.player.AbstractCache;
import eu.ydp.gwtutil.client.xml.XMLParser;

public class ConnectionStyleXMLElementCache extends AbstractCache<MultiplePairModuleConnectType, Element> {

	Map<MultiplePairModuleConnectType, String> styles = new HashMap<MultiplePairModuleConnectType, String>();
	private final XMLParser xmlParser;

	public ConnectionStyleXMLElementCache(StyleNameConstants styleNames, XMLParser xmlParser) {
		styles.put(MultiplePairModuleConnectType.WRONG, styleNames.QP_CONNECTION_WRONG());
		styles.put(MultiplePairModuleConnectType.CORRECT, styleNames.QP_CONNECTION_CORRECT());
		styles.put(MultiplePairModuleConnectType.NONE, styleNames.QP_CONNECTION_DISABLED());
		styles.put(MultiplePairModuleConnectType.NORMAL, styleNames.QP_CONNECTION_NORMAL());
		this.xmlParser = xmlParser;
	}

	String template = "<root><connection class=\"$class\" /></root>";

	private String getTemplate(String className) {
		return template.replaceAll("\\$class", className);
	}

	private Element getXMLElement(String className) {
		return (Element) xmlParser.parse(getTemplate(className)).getDocumentElement().getFirstChild();
	}

	@Override
	protected Element getElement(MultiplePairModuleConnectType index) {
		return getXMLElement(styles.get(index));
	}

}
