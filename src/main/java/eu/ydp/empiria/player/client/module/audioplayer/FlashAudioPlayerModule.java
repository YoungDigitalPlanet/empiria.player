package eu.ydp.empiria.player.client.module.audioplayer;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ParentedModuleBase;
import eu.ydp.empiria.player.client.module.object.impl.flash.FlashLocalAudioImpl;
import eu.ydp.gwtutil.client.xml.XMLUtils;

import java.util.List;

public class FlashAudioPlayerModule extends ParentedModuleBase implements AudioPlayerModule {

    @Inject
    private FlashLocalAudioImpl view;

    @Override
    public void initModule(Element element, ModuleSocket moduleSocket, InteractionEventsListener iel) {
        initModule(moduleSocket);
        String address = XMLUtils.getAttributeAsString(element, "src");
        if (address == null || "".equals(address)) {
            address = XMLUtils.getAttributeAsString(element, "data");
        }

        String[] type = address.split("[.]");
        view.addSrc(address, "audio/" + type[type.length - 1]);

    }

    @Override
    public Widget getView() {
        return view;
    }
}
