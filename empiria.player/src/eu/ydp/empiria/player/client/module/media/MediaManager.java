package eu.ydp.empiria.player.client.module.media;

import java.util.HashSet;
import java.util.Set;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.controller.extensions.Extension;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.AbstractMediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.gwtutil.client.debug.Debug;

/**
 * Klasa zarzadcy mediow.
 *
 */
public class MediaManager extends AbstractMediaEventHandler implements Extension {

	Set<MediaWrapper<?>> mediaSet = new HashSet<MediaWrapper<?>>();
	protected EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();
	public MediaManager() {
		eventsBus.addAsyncHandler(MediaEvent.getType(MediaEventTypes.MEDIA_ATTACHED), this);
		eventsBus.addAsyncHandler(MediaEvent.getType(MediaEventTypes.PLAY), this);
	}

	@Override
	public void onMediaEvent(MediaEvent event) {
		switch(event.getType()){
		case MEDIA_ATTACHED:
			mediaSet.add(event.getMediaWrapper());
			break;
		case PLAY:
			for(MediaWrapper<?> media : mediaSet){
				if(!media.equals(event.getMediaWrapper())){
					eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.PAUSE),media);
				}
			}
			break;
		}
	}

	@Override
	public ExtensionType getType() {
		return ExtensionType.MULTITYPE;
	}

	@Override
	public void init() {


	}

}
