package eu.ydp.empiria.player.client.module;

import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;

import java.util.List;

public abstract class ContainerModuleBase extends ModuleBase implements ISingleViewWithBodyModule {

    @Override
    public void initModule(Element element, ModuleSocket ms, BodyGeneratorSocket bgs) {
        initModule(ms);
        readAttributes(element);
        applyIdAndClassToView(getView());
    }

    @Override
    public List<IModule> getChildrenModules() {
        return getModuleSocket().getChildren(this);
    }

    @Override
    public List<HasParent> getNestedChildren() {
        return getModuleSocket().getNestedChildren(this);
    }
}
