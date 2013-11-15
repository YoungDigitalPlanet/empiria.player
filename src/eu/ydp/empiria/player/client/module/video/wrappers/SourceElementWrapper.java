package eu.ydp.empiria.player.client.module.video.wrappers;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.SourceElement;

public class SourceElementWrapper {

	private final SourceElement sourceElement;

	public SourceElementWrapper(SourceElement sourceElement) {
		this.sourceElement = sourceElement;
	}

	public void setSrc(String url) {
		sourceElement.setSrc(url);
	}

	public void setType(String type) {
		sourceElement.setType(type);
	}

	public Node asNode() {
		return sourceElement;
	}
}
