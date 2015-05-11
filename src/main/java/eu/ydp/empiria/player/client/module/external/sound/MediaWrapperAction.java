package eu.ydp.empiria.player.client.module.external.sound;

import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public interface MediaWrapperAction {
	void execute(MediaWrapper<Widget> wrapper);
}
