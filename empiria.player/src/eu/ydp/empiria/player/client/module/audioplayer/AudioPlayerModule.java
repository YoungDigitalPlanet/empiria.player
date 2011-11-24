package eu.ydp.empiria.player.client.module.audioplayer;

import java.util.Vector;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.allen_sauer.gwt.voices.client.handler.PlaybackCompleteEvent;
import com.allen_sauer.gwt.voices.client.handler.SoundHandler;
import com.allen_sauer.gwt.voices.client.handler.SoundLoadStateChangeEvent;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.events.internal.InternalEvent;
import eu.ydp.empiria.player.client.controller.events.internal.InternalEventTrigger;
import eu.ydp.empiria.player.client.module.IBrowserEventHandler;
import eu.ydp.empiria.player.client.util.xml.XMLUtils;

public class AudioPlayerModule extends Composite implements IBrowserEventHandler {

	public AudioPlayerModule(Element element){

		String address = XMLUtils.getAttributeAsString(element, "data");
		
		SoundController soundCtrl = new SoundController();
		sound = soundCtrl.createSound(Sound.MIME_TYPE_AUDIO_MPEG, address);
		sound.addEventHandler(new SoundHandler() {
			public void onSoundLoadStateChange(SoundLoadStateChangeEvent event) {
			}
			
			public void onPlaybackComplete(PlaybackCompleteEvent event) {
				setStopped();	
			}
		});
		
		playing = false;
		
		button = new PushButton();
		button.setStyleName("qp-audioplayer-button");
		button.getElement().setId(com.google.gwt.dom.client.Document.get().createUniqueId());
		
		initWidget(button);
	}
	
	private Sound sound;
	private boolean playing;
	private PushButton button;
	
	private void play(){
		sound.play();
		setPlaying();
	}
	
	private void setPlaying(){
		playing = true;
		button.setStyleName("qp-audioplayer-button-playing");
	}
	
	private void stop(){
		sound.stop();
		setStopped();
	}
	
	private void setStopped(){
		playing = false;
		button.setStyleName("qp-audioplayer-button");	
	}

	@Override
	public Vector<InternalEventTrigger> getTriggers() {
		Vector<InternalEventTrigger> triggers = new Vector<InternalEventTrigger>();
		triggers.add(new InternalEventTrigger(button.getElement().getId(), Event.ONMOUSEUP));
		return triggers;
	}

	@Override
	public void handleEvent(String tagID, InternalEvent event) {
		if (playing){
			stop();
		} else {
			play();
		}
		
	}

}
