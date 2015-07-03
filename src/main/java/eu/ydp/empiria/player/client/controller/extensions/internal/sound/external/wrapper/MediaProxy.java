package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.wrapper;

import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.MediaExecutor;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public interface MediaProxy {

    MediaWrapper<Widget> getMediaWrapper();

    MediaExecutor<Widget> getMediaExecutor();

}
