package eu.ydp.empiria.player.client.module.core.base;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;

import java.util.List;

public interface IMultiViewModule extends IModule {
    void initModule(ModuleSocket moduleSocket, EventsBus eventsBus);

    void addElement(Element element);

    void installViews(List<HasWidgets> placeholders);
}
