package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.EndedEvent;
import com.google.gwt.event.dom.client.EndedHandler;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.ui.RootPanel;

public class SoundExecutorHtml5 implements SoundExecutor {

	protected Audio audioCtrl;
	protected boolean playing = false;
	protected SoundExecutorListener listener;
	
	public SoundExecutorHtml5(){
		audioCtrl = Audio.createIfSupported();
		RootPanel.get().add( audioCtrl );
		audioCtrl.setVisible(false);
		if (audioCtrl != null){
			addEndedListener(audioCtrl.getElement());		
			audioCtrl.addEndedHandler(new EndedHandler(){
				
				@Override
				public void onEnded(EndedEvent event) {
					onEnded2();
				}
			});
		}
	}
	
	private native void addEndedListener(Element audioElement)/*-{
		var instance = this;
		audioElement.addEventListener('ended', function(){
				instance.@eu.ydp.empiria.player.client.controller.extensions.internal.sound.SoundExecutorHtml5::onEnded2()();
			}
		);
	}-*/;
	
	protected void onEnded2(){
		if (playing){
			playing = false;
			listener.onSoundFinished();
		}
	}
	
	@Override
	public void play(String src) {
		if (audioCtrl != null){
			if (playing)
				stop();
			listener.onPlay();
			audioCtrl.setSrc(src);
			audioCtrl.play();
			playing = true;
		}
	}

	@Override
	public void stop() {
		audioCtrl.pause();
		onEnded2();
	}

	@Override
	public void setSoundFinishedListener(SoundExecutorListener listener) {
		this.listener = listener;
	}

}
