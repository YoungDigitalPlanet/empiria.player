package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external;

import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public interface MediaWrapperSupplier {

    MediaWrapper<Widget> getById(String id);
}
