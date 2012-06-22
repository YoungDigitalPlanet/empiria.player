package eu.ydp.empiria.player.client.module.audioplayer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEvent;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallback;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallforward;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.util.XMLUtils;

public class DefaultAudioPlayerModule implements AudioPlayerModule {

	public DefaultAudioPlayerModule(){
		button = new PushButton();
	}

	protected PushButton button;
	protected String address;
	protected InteractionEventsListener mediaListener;
	protected boolean playing;
	protected boolean enabled = true;

	protected MediaInteractionSoundEventCallforward callforward;

	@Override
	public void initModule(Element element, ModuleSocket ms, InteractionEventsListener iel) {

		mediaListener = iel;

		address = XMLUtils.getAttributeAsString(element, "src");
		if (address == null  ||  "".equals(address))
			address = XMLUtils.getAttributeAsString(element, "data");

		playing = false;

		button.setStyleName("qp-audioplayer-button");
		button.getElement().setId(com.google.gwt.dom.client.Document.get().createUniqueId());

		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				onButtonClick();
			}
		});
	}

	@Override
	public Widget getView() {
		return button;
	}

	private void play(){
		enabled = false;
		mediaListener.onMediaSound(new MediaInteractionSoundEvent(address, new MediaInteractionSoundEventCallback() {

			@Override
			public void onStop() {
				setStopped();
			}

			@Override
			public void onPlay() {
				setPlaying();
			}

			@Override
			public void setCallforward(MediaInteractionSoundEventCallforward cf) {
				callforward = cf;
			}
		}));
	}

	protected void stop(){
		enabled = false;
		if (callforward != null)
			callforward.stop();
	}

	protected void setPlaying(){
		enabled = true;
		playing = true;
		button.setStyleName("qp-audioplayer-button-playing");
	}


	protected void setStopped(){
		enabled = true;
		playing = false;
		button.setStyleName("qp-audioplayer-button");
	}


	protected void onButtonClick() {
		if (enabled){
			if (!playing)
				play();
			else
				stop();
		}
	}

}
