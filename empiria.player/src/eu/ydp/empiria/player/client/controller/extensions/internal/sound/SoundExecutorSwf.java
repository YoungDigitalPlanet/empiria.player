package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import eu.ydp.empiria.flash.gwtflashaudio.client.FlashSound;
import eu.ydp.empiria.flash.gwtflashaudio.client.FlashSoundFactory;
import eu.ydp.empiria.flash.gwtflashaudio.client.event.FlashSoundCompleteEvent;
import eu.ydp.empiria.flash.gwtflashaudio.client.event.FlashSoundCompleteHandler;
import eu.ydp.empiria.flash.gwtflashaudio.client.event.FlashSoundPlayEvent;
import eu.ydp.empiria.flash.gwtflashaudio.client.event.FlashSoundPlayHandler;

public class SoundExecutorSwf implements SoundExecutor {

	protected FlashSound currSound;
	protected boolean playing = false;
	protected SoundExecutorListener listener;
	protected String playerPathDir = null;
	
	public SoundExecutorSwf(){
		FlashSoundFactory.init();
	}
	
	@Override
	public void play(String src) {

		if (playing)
			stop();
		
		currSound = FlashSoundFactory.createSound(src);
		
		currSound.addFlashSoundPlayHandler(new FlashSoundPlayHandler() {
			
			@Override
			public void onFlashSoundPlay(FlashSoundPlayEvent event) {
				if (listener != null)
					listener.onPlay();
				playing = true;
			}
		});
		
		currSound.addFlashSoundCompleteHandler(new FlashSoundCompleteHandler() {
			
			@Override
			public void onFlashSoundComplete(FlashSoundCompleteEvent event) {
				onSoundStop();
			}
		});
		
		currSound.playSound();

	}

	@Override
	public void stop() {
		if (playing){
			if (currSound != null)
				currSound.stopSound();
			onSoundStop();
		}
	}
	
	private void onSoundStop(){
		playing = false;
		if (listener != null)
			listener.onSoundFinished();
		currSound.freeSound();
	}

	@Override
	public void setSoundFinishedListener(SoundExecutorListener listener) {
		this.listener = listener;
	}
	
}
