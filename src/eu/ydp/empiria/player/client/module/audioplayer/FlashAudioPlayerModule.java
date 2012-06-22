package eu.ydp.empiria.player.client.module.audioplayer;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.object.impl.FlashLocalAudioImpl;
import eu.ydp.empiria.player.client.util.XMLUtils;

public class FlashAudioPlayerModule extends FlashLocalAudioImpl implements
		AudioPlayerModule {

	@Override
	public void initModule(Element element, ModuleSocket ms, InteractionEventsListener iel) {
		
		String address = XMLUtils.getAttributeAsString(element, "src");
		if (address == null  ||  "".equals(address))
			address = XMLUtils.getAttributeAsString(element, "data");
		
		String[] type = address.split("[.]");
		addSrc(address, "audio/" + type[type.length - 1]);

	}

	@Override
	public Widget getView() {
		return this;
	}

}
