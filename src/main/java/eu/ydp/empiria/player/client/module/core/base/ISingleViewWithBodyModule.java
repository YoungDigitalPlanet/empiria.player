package eu.ydp.empiria.player.client.module.core.base;

import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;

public interface ISingleViewWithBodyModule extends ISingleViewModule, HasChildren {

    void initModule(Element element, ModuleSocket ms, BodyGeneratorSocket bodyGeneratorSocket, EventsBus eventsBus);
}
