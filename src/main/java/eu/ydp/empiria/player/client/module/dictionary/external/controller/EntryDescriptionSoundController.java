package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.gin.factory.DictionaryModuleFactory;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;
import eu.ydp.empiria.player.client.module.dictionary.external.view.ExplanationView;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.callback.CallbackReceiver;
import eu.ydp.empiria.player.client.util.events.media.AbstractMediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;

public class EntryDescriptionSoundController {

	private final DescriptionSoundController descriptionSoundController;
	private final ExplanationView explanationView;

	@Inject
	public EntryDescriptionSoundController(@Assisted ExplanationView explanationView,
										   DictionaryModuleFactory dictionaryModuleFactory) {

		this.descriptionSoundController = dictionaryModuleFactory.getDescriptionSoundController(explanationView);
		this.explanationView = explanationView;

	}

	public void playOrStopEntrySound(String filename) {
		if(descriptionSoundController.isPlaying()){
			stop();
		}else {
			descriptionSoundController.playDescriptionSound(filename, getCallbackReceiver());
		}
	}

	private CallbackReceiver<MediaWrapper<Widget>> getCallbackReceiver(){
		return new CallbackReceiver<MediaWrapper<Widget>>(){

			@Override
			public void setCallbackReturnObject(MediaWrapper<Widget> mw) {
				onEntryMediaWrapperCallback(mw);
			}
		};
	}

	private void onEntryMediaWrapperCallback(MediaWrapper<Widget> mw) {
		explanationView.setEntryPlayButtonStyle();
		AbstractMediaEventHandler handler = createEntrySoundMediaHandler();
		descriptionSoundController.playFromMediaWrapper(handler, mw);
	}

	private AbstractMediaEventHandler createEntrySoundMediaHandler() {
		return new AbstractMediaEventHandler() {
			@Override
			public void onMediaEvent(MediaEvent event) {
				if (descriptionSoundController.isMediaEventNotOnPlay(event)) {
					explanationView.setEntryStopButtonStyle();
					descriptionSoundController.stopPlaying();
				}
			}
		};
	}

	public void stop() {
		descriptionSoundController.stopPlaying();
		explanationView.setEntryStopButtonStyle();
		descriptionSoundController.stopMediaWrapper();
	}
}