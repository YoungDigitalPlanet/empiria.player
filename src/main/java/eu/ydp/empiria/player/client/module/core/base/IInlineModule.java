package eu.ydp.empiria.player.client.module.core.base;

import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;

public interface IInlineModule extends ISingleViewModule {

    void initModule(Element element, ModuleSocket ms, EventsBus eventsBus);
}
