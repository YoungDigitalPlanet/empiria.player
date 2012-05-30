package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.Sound.LoadState;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.allen_sauer.gwt.voices.client.handler.PlaybackCompleteEvent;
import com.allen_sauer.gwt.voices.client.handler.SoundHandler;
import com.allen_sauer.gwt.voices.client.handler.SoundLoadStateChangeEvent;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;

public class SoundExecutorSwf implements SoundExecutor {

	protected Sound currSound;
	protected SoundHandler currSoundHandler;
	protected boolean playing = false;
	protected SoundExecutorListener listener;
	protected String playerPathDir = null;
	
	@Override
	public void play(String src) {

		if (playing)
			stop();
		
		SoundController ctrl = new SoundController();
		ctrl.setGwtVoicesSwfLocation(getPlayerPathDir());
		currSound = ctrl.createSound(Sound.MIME_TYPE_AUDIO_MPEG_MP3, src);					
		currSoundHandler = new SoundHandler() {
			
			@Override
			public void onSoundLoadStateChange(SoundLoadStateChangeEvent event) {
				if (event.getLoadState() == LoadState.LOAD_STATE_SUPPORTED_AND_READY  ||  event.getLoadState() == LoadState.LOAD_STATE_SUPPORTED_MAYBE_READY){
					if (listener != null)
						listener.onPlay();
					playing = true;
				}
			}
			
			@Override
			public void onPlaybackComplete(PlaybackCompleteEvent event) {
				playing = false;
				listener.onSoundFinished();
			}
		};
		
		currSound.addEventHandler(currSoundHandler);
		currSound.play();

	}

	@Override
	public void stop() {
		if (playing){
			if (currSound != null)
				currSound.stop();
			if (currSoundHandler != null)
				currSoundHandler.onPlaybackComplete(null);
		}
	}

	@Override
	public void setSoundFinishedListener(SoundExecutorListener listener) {
		this.listener = listener;
	}
	
	private String getPlayerPathDir(){
		if (playerPathDir != null)
			return playerPathDir;
		playerPathDir = findPlayerPathDir();
		return playerPathDir;
	}
	
	private String findPlayerPathDir(){
		NodeList<Element> scriptNodes = Document.get().getElementsByTagName("script");
		String empiriaPlayerFileName = "/empiria.player.nocache.js";
		for (int s = 0 ; s < scriptNodes.getLength() ; s ++){
			if (((Element)scriptNodes.getItem(s)).hasAttribute("src")){
				String src = ((Element)scriptNodes.getItem(s)).getAttribute("src");				
				if (src.endsWith(empiriaPlayerFileName)){
					return src.substring(0, src.indexOf(empiriaPlayerFileName) +1);
				}
			}
		}
		return "";
		
	}
}
