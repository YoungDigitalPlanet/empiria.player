package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.EndedEvent;
import com.google.gwt.event.dom.client.EndedHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

@Deprecated
public class SoundExecutorHtml5 implements SoundExecutor<Widget>, TouchStartHandler {

	protected Audio audioCtrl;
	protected boolean playing = false;
	protected SoundExecutorListener listener;
	private BaseMediaConfiguration baseMediaConfiguration;
	private HandlerRegistration touchHandlerRegistration;

	public SoundExecutorHtml5(){
		audioCtrl = Audio.createIfSupported();
		RootPanel.get().add( audioCtrl );
		audioCtrl.setVisible(false);
		simulateUserInteraction();
		
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
		
		if (audioCtrl != null) {
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
	
	/** Ta metoda jest potrzebna aby obejsc problemy z odtwarzaniem dzwieku na iOS. Apple w swej madrosci nie pozwolilo na odtwarzanie dzwiekow
	 * dopoki nie zostanie odtworzony jakikolwiek dzwiek w reakcji na dzialanie uzytkownika. */
	protected void simulateUserInteraction() {
		RootPanel root = RootPanel.get();
		touchHandlerRegistration = root.addDomHandler(this, TouchStartEvent.getType());
	}
	
	@Override
	public void onTouchStart(TouchStartEvent event) {
		touchHandlerRegistration.removeHandler();
		audioCtrl.play();
		audioCtrl.pause();
	}

	@Override
	public void setSoundFinishedListener(SoundExecutorListener listener) {
		this.listener = listener;
	}

	@Override
	public MediaWrapper<Widget> getMediaWrapper() {
		return null;
	}

	@Override
	public void pause() {
	}

	@Override
	public void setMuted(boolean mute) {
	}

	@Override
	public void setVolume(double volume) {
	}

	@Override
	public void setCurrentTime(double time) {
	}

	@Override
	public void setMediaWrapper(MediaWrapper<Widget> descriptor) {
	}

	@Override
	public void init() {
	}

	@Override
	public void play() {
		play(baseMediaConfiguration.getSources().keySet().iterator().next());
	}

	@Override
	public void setBaseMediaConfiguration(BaseMediaConfiguration baseMediaConfiguration) {
		this.baseMediaConfiguration = baseMediaConfiguration;
	}

}
