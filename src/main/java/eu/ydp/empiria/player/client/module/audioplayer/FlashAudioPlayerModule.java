package eu.ydp.empiria.player.client.module.audioplayer;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.object.impl.FlashLocalAudioImpl;
import eu.ydp.gwtutil.client.xml.XMLUtils;

import java.util.List;

/**
 * KLASA DO REFAKTORYZACJI - MODUL NIE POWINIEN BYC WIDGETEM
 */
public class FlashAudioPlayerModule extends FlashLocalAudioImpl implements AudioPlayerModule {

    private ModuleSocket moduleSocket;

    @Override
    public void initModule(Element element, ModuleSocket moduleSocket, InteractionEventsListener iel) {
        this.moduleSocket = moduleSocket;
        String address = XMLUtils.getAttributeAsString(element, "src");
        if (address == null || "".equals(address)) {
            address = XMLUtils.getAttributeAsString(element, "data");
        }

        String[] type = address.split("[.]");
        addSrc(address, "audio/" + type[type.length - 1]);

    }

    @Override
    public Widget getView() {
        return this;
    }

    @Override
    public HasChildren getParentModule() {
        return moduleSocket.getParent(this);
    }

    @Override
    public List<IModule> getChildren() {
        return moduleSocket.getChildren(this);
    }

}
