package eu.ydp.empiria.player.client.module.audioplayer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallback;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallforward;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.listener.MediaModuleInteractionListener;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;
import eu.ydp.empiria.player.client.util.xml.XMLUtils;

public class AudioPlayerModule implements ISimpleModule,Factory<AudioPlayerModule> {

	public AudioPlayerModule(){
		button = new PushButton();
	}

	protected PushButton button;
	protected String address;
	protected MediaModuleInteractionListener mediaListener;
	protected boolean playing;
	protected boolean enabled = true;

	protected MediaInteractionSoundEventCallforward callforward;

	@Override
	public void initModule(Element element, ModuleSocket ms, ModuleInteractionListener mil) {

		mediaListener = mil;

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
		mediaListener.onMediaSoundPlay(address, new MediaInteractionSoundEventCallback() {

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
		});
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

	@Override
	public AudioPlayerModule getNewInstance() {
		return new AudioPlayerModule();
	}
}
