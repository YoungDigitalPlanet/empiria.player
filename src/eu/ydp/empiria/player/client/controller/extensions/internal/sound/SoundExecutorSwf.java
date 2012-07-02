package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import eu.ydp.empiria.gwtflashmedia.client.FlashSound;
import eu.ydp.empiria.gwtflashmedia.client.FlashSoundFactory;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaCompleteEvent;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaCompleteHandler;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaPlayEvent;
import eu.ydp.empiria.gwtflashmedia.client.event.FlashMediaPlayHandler;

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
		
		currSound.addFlashMediaPlayHandler(new FlashMediaPlayHandler() {
			
			@Override
			public void onFlashSoundPlay(FlashMediaPlayEvent event) {
				if (listener != null)
					listener.onPlay();
				playing = true;
			}
		});
		
		currSound.addFlashMediaCompleteHandler(new FlashMediaCompleteHandler() {
			
			@Override
			public void onFlashSoundComplete(FlashMediaCompleteEvent event) {
				onSoundStop();
			}
		});
		
		currSound.play();

	}

	@Override
	public void stop() {
		if (playing){
			if (currSound != null)
				currSound.stop();
			onSoundStop();
		}
	}
	
	private void onSoundStop(){
		playing = false;
		if (listener != null)
			listener.onSoundFinished();
		currSound.free();
	}

	@Override
	public void setSoundFinishedListener(SoundExecutorListener listener) {
		this.listener = listener;
	}
	
}
