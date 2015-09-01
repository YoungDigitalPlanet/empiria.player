package eu.ydp.empiria.player.client.module;

import com.google.gwt.xml.client.Element;

public abstract class SimpleModuleBase extends ModuleBase implements ISimpleModule, IInlineModule {

    @Override
    public final void initModule(Element element, ModuleSocket ms) {
        initModule(ms);
        readAttributes(element);
        initModule(element);
        applyIdAndClassToView(getView());
    }

    protected abstract void initModule(Element element);
}
