package eu.ydp.empiria.player.client.module.audioplayer;

import java.util.Vector;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallback;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallforward;
import eu.ydp.empiria.player.client.controller.events.internal.InternalEvent;
import eu.ydp.empiria.player.client.controller.events.internal.InternalEventTrigger;
import eu.ydp.empiria.player.client.module.IBrowserEventHandler;
import eu.ydp.empiria.player.client.module.MediaModuleInteractionListener;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.util.xml.XMLUtils;

public class AudioPlayerModule extends Composite implements IBrowserEventHandler {

	public AudioPlayerModule(Element element, ModuleSocket moduleSocket, MediaModuleInteractionListener moduleEventsListener){

		mediaListener = moduleEventsListener;
		
		address = XMLUtils.getAttributeAsString(element, "src");
		if (address == null  ||  "".equals(address))
			address = XMLUtils.getAttributeAsString(element, "data");
		
		playing = false;
		
		button = new PushButton();
		button.setStyleName("qp-audioplayer-button");
		button.getElement().setId(com.google.gwt.dom.client.Document.get().createUniqueId());
		
		initWidget(button);
	}
	
	private PushButton button;
	protected String address;
	protected MediaModuleInteractionListener mediaListener;
	protected boolean playing;
	
	protected MediaInteractionSoundEventCallforward callforward;
	
	private void play(){
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
		setPlaying();
	}
	
	protected void stop(){
		callforward.stop();
	}
	
	private void setPlaying(){
		playing = true;
		button.setStyleName("qp-audioplayer-button-playing");
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
		if (!playing)
			play();
		else
			stop();
		
	}

}
