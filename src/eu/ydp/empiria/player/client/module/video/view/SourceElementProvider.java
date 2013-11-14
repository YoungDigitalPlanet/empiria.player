package eu.ydp.empiria.player.client.module.video.view;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.SourceElement;
import com.google.inject.Provider;

public class SourceElementProvider implements Provider<SourceElement> {

	@Override
	public SourceElement get() {
		SourceElement sourceElement = Document.get().createSourceElement();
		return sourceElement;
	}

}
