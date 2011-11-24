package eu.ydp.empiria.player.client.module.vocabox;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.allen_sauer.gwt.voices.client.handler.PlaybackCompleteEvent;
import com.allen_sauer.gwt.voices.client.handler.SoundHandler;
import com.allen_sauer.gwt.voices.client.handler.SoundLoadStateChangeEvent;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.xml.client.Element;

public class VocaItem extends InlineLabel {

	private String soundAddress;
	private String id;
	private Sound sound;
	
	public VocaItem(Element element){
		setText(element.getAttribute("text"));
		soundAddress = element.getAttribute("src");
		
		id = Document.get().createUniqueId();
		getElement().setId(id);
		
		setStyleName("qp-vocabox-item");
		
		SoundController soundCtrl = new SoundController();
		sound = soundCtrl.createSound(Sound.MIME_TYPE_AUDIO_MPEG, soundAddress);
		sound.addEventHandler(new SoundHandler() {
			
			@Override
			public void onSoundLoadStateChange(SoundLoadStateChangeEvent event) {
			}
			
			@Override
			public void onPlaybackComplete(PlaybackCompleteEvent event) {
				setStyleName("qp-vocabox-item-played");
			}
		});
	}
	
	public String getId(){
		return id;
	}
	
	public void play(){
		sound.play();
		setStyleName("qp-vocabox-item-playing");
	}
}
