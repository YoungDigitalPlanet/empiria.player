package eu.ydp.empiria.player.client.module.audioplayer;

import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.SourceUtil;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.callback.CallbackRecevier;
import eu.ydp.empiria.player.client.util.events.media.AbstractMediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;

public class DefaultAudioPlayerModule implements AudioPlayerModule {

	@Inject private EventsBus eventsBus;
	
	private PushButton button;
	private boolean playing;
	private boolean enabled = true;
	private ModuleSocket moduleSocket;
	private MediaWrapper<?> mediaWrapper;
	
	public DefaultAudioPlayerModule(){
		button = new PushButton();
	}

	@Override
	public void initModule(Element element, ModuleSocket moduleSocket, InteractionEventsListener iel) {
		this.moduleSocket = moduleSocket;

		Map<String, String> sources = SourceUtil.getSource(element, "audio");

		button.setStyleName("qp-audioplayer-button");
		button.getElement().setId(com.google.gwt.dom.client.Document.get().createUniqueId());

		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				onButtonClick();
			}
		});
		
		BaseMediaConfiguration bmc = new BaseMediaConfiguration(sources, false, true);
		eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.CREATE_MEDIA_WRAPPER, bmc , new CallbackRecevier<MediaWrapper<?>>() {

			@Override
			public void setCallbackReturnObject(MediaWrapper<?> mw) {
				initMediaWrapper(mw);
			}
		}));
	}

	private void initMediaWrapper(MediaWrapper<?> mw) {
		this.mediaWrapper = mw;
		AbstractMediaEventHandler handler = createMediaHandler();
		addMediaHandlers(handler);
		addMediaWidgetToRoot();
	}

	private void addMediaWidgetToRoot() {
		RootPanel.get().add(mediaWrapper.getMediaObject());
	}

	private AbstractMediaEventHandler createMediaHandler() {
		return new AbstractMediaEventHandler() {
			@Override
			public void onMediaEvent(MediaEvent event) {
				if (event.getType() == MediaEventTypes.ON_PLAY) {
					setPlaying();
				} else {
					setStopped();
				}
			}
		};
	}

	private void addMediaHandlers(AbstractMediaEventHandler handler) {
		addMediaHandler(MediaEventTypes.ON_PAUSE, handler);
		addMediaHandler(MediaEventTypes.ON_END, handler);
		addMediaHandler(MediaEventTypes.ON_STOP, handler);
		addMediaHandler(MediaEventTypes.ON_PLAY, handler);
	}

	private void addMediaHandler(MediaEventTypes type, MediaEventHandler handler) {
		CurrentPageScope scope = new CurrentPageScope();
		eventsBus.addHandlerToSource(MediaEvent.getType(type), mediaWrapper, handler, scope);		
	}

	@Override
	public Widget getView() {
		return button;
	}

	private void play(){
		enabled = false;
		eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.STOP, mediaWrapper), mediaWrapper);
		eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.PLAY, mediaWrapper), mediaWrapper);
	}

	protected void stop(){
		enabled = false;
		eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.PAUSE, mediaWrapper), mediaWrapper);
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
