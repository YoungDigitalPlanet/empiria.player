package eu.ydp.empiria.player.client.module;

import com.google.gwt.xml.client.Element;

public abstract class SingleViewModuleBase extends ModuleBase implements ISingleViewModule {

    protected final void initModuleInternal(Element element, ModuleSocket ms) {
        initModule(ms);
        readAttributes(element);
        initModule(element);
        applyIdAndClassToView(getView());
    }

    protected abstract void initModule(Element element);
}
