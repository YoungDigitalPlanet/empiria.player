package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.gin.factory.DictionaryModuleFactory;
import eu.ydp.empiria.player.client.module.dictionary.external.view.ExplanationView;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.callback.CallbackReceiver;
import eu.ydp.empiria.player.client.util.events.media.AbstractMediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;

public class ExplanationDescriptionSoundController {

	private final DescriptionSoundController descriptionSoundController;

	private final ExplanationView explanationView;

	@Inject
	public ExplanationDescriptionSoundController(@Assisted ExplanationView explanationView,
												 DictionaryModuleFactory dictionaryModuleFactory) {
		this.explanationView = explanationView;
		this.descriptionSoundController = dictionaryModuleFactory.getDescriptionSoundController();
	}

	public void playOrStopExplanationSound(String filename) {
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
				onExplanationMediaWrapperCallback(mw);
			}
		};
	}

	private void onExplanationMediaWrapperCallback(MediaWrapper<Widget> mw) {
		explanationView.setExplanationPlayButtonStyle();
		AbstractMediaEventHandler handler = createExplanationSoundMediaHandler();
		descriptionSoundController.playFromMediaWrapper(handler, mw);
	}

	private AbstractMediaEventHandler createExplanationSoundMediaHandler() {
		return new AbstractMediaEventHandler() {
			@Override
			public void onMediaEvent(MediaEvent event) {
				if (descriptionSoundController.isMediaEventNotOnPlay(event)) {
					explanationView.setExplanationStopButtonStyle();
					descriptionSoundController.stopPlaying();
				}
			}
		};
	}

	public void stop() {
		descriptionSoundController.stopPlaying();
		explanationView.setExplanationStopButtonStyle();
		descriptionSoundController.stopMediaWrapper();
	}
}