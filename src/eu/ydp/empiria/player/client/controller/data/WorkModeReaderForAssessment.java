package eu.ydp.empiria.player.client.controller.data;

import com.google.gwt.xml.client.Node;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;

public class WorkModeReaderForAssessment {

	public String read(XmlData data) {
		Node node = data.getDocument()
		                .getFirstChild()
		                .getAttributes()
		                .getNamedItem("mode");
		return node == null ? "" : node.getNodeValue();
	}
}
