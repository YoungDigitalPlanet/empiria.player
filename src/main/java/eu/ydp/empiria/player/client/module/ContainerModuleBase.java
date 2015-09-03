package eu.ydp.empiria.player.client.module;

import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;

import java.util.List;

public abstract class ContainerModuleBase extends ModuleBase implements ISingleViewWithBodyModule {

    private BodyGeneratorSocket bodyGenerator;

    @Override
    public void initModule(Element element, ModuleSocket ms, BodyGeneratorSocket bodyGenerator, EventsBus eventsBus) {
        this.bodyGenerator = bodyGenerator;
        initModule(ms, eventsBus);
        readAttributes(element);
        initModule(element);
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

    protected BodyGeneratorSocket getBodyGenerator() {
        return bodyGenerator;
    }

    public abstract void initModule(Element element);
}
