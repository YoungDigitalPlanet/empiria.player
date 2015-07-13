package eu.ydp.empiria.player.client.module.video.wrappers;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.SourceElement;
import com.google.inject.Provider;

public class SourceElementWrapperProvider implements Provider<SourceElementWrapper> {

    @Override
    public SourceElementWrapper get() {
        SourceElement sourceElement = Document.get().createSourceElement();
        return new SourceElementWrapper(sourceElement);
    }
}
