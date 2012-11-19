package eu.ydp.empiria.player.client.module.audioplayer;

import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEvent;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallback;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallforward;
import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.util.SourceUtil;
import eu.ydp.gwtutil.client.util.MediaChecker;
import eu.ydp.gwtutil.client.xml.XMLUtils;

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
	private ModuleSocket moduleSocket;

	@Override
	public void initModule(Element element, ModuleSocket moduleSocket, InteractionEventsListener iel) {
		this.moduleSocket = moduleSocket;
		mediaListener = iel;

		Map<String, String> sources = SourceUtil.getSource(element, "audio");
		
		if (MediaChecker.isHtml5OggSupport()  &&  SourceUtil.containsOgg(sources)  &&  Audio.isSupported()){
			address = SourceUtil.getOggSource(sources);
		} else {
			address = SourceUtil.getMpegSource(sources);
		}
		
		if (address == null){
			address = XMLUtils.getAttributeAsString(element, "src");
		}

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
			public void setCallforward(MediaInteractionSoundEventCallforward callForward) {
				callforward = callForward;
			}
		}));
	}

	protected void stop(){
		enabled = false;
		if (callforward != null) {
			callforward.stop();
		}
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
			if (playing) {
				stop();
			} else {
				play();
			}
		}
	}

	@Override
	public HasChildren getParentModule() {
		return moduleSocket.getParent(this);
	}

	@Override
	public List<IModule> getChildren() {
		return moduleSocket.getChildren(this);
	}
}
