package eu.ydp.empiria.player.client.module.audioplayer;

import static eu.ydp.empiria.player.client.util.events.media.MediaEventTypes.ON_END;
import static eu.ydp.empiria.player.client.util.events.media.MediaEventTypes.ON_PAUSE;
import static eu.ydp.empiria.player.client.util.events.media.MediaEventTypes.ON_PLAY;
import static eu.ydp.empiria.player.client.util.events.media.MediaEventTypes.ON_STOP;
import static eu.ydp.empiria.player.client.util.events.media.MediaEventTypes.PAUSE;
import static eu.ydp.empiria.player.client.util.events.media.MediaEventTypes.PLAY;
import static eu.ydp.empiria.player.client.util.events.media.MediaEventTypes.STOP;

import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.feedback.player.HideNativeMediaControlsManager;
import eu.ydp.empiria.player.client.gin.binding.UniqueId;
import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
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
import eu.ydp.gwtutil.client.ui.button.CustomPushButton;

public class DefaultAudioPlayerModule implements AudioPlayerModule {
	@Inject private EventsBus eventsBus;
	@Inject private HideNativeMediaControlsManager nativeMediaControlsManager;
	@Inject @UniqueId private String moduleId;
	@Inject private StyleNameConstants styleNameConstants;
	@Inject private CustomPushButton button;
	private boolean playing;
	private boolean enabled = true;
	private ModuleSocket moduleSocket;
	private MediaWrapper<?> mediaWrapper;
	private Map<String, String> sources;


	@Override
	public void initModule(Element element, ModuleSocket moduleSocket, InteractionEventsListener iel) {
		this.moduleSocket = moduleSocket;
		sources = SourceUtil.getSource(element, "audio");
		button.setStyleName(styleNameConstants.QP_AUDIOPLAYER_BUTTON());
		button.getElement().setId(moduleId);
		addClickHandler();
	}

	private void addClickHandler() {
		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(mediaWrapper == null){
					createMediaWrapperAndPlayAudio(sources);
				}else{
					playAudio();
				}
			}
		});
	}

	private void createMediaWrapperAndPlayAudio(Map<String, String> sources) {
		BaseMediaConfiguration bmc = new BaseMediaConfiguration(sources, false, true);
		eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.CREATE_MEDIA_WRAPPER, bmc , new CallbackRecevier<MediaWrapper<?>>() {

			@Override
			public void setCallbackReturnObject(MediaWrapper<?> mw) {
				initMediaWrapperAndPlayAudio(mw);
			}
		}));
	}

	private void initMediaWrapperAndPlayAudio(MediaWrapper<?> mw) {
		this.mediaWrapper = mw;
		AbstractMediaEventHandler handler = createMediaHandler();
		addMediaHandlers(handler);
		addMediaWidgetToRoot();
		playAudio();
	}

	private void addMediaWidgetToRoot() {
		nativeMediaControlsManager.addToDocumentAndHideControls((MediaWrapper<Widget>) mediaWrapper);
	}

	private AbstractMediaEventHandler createMediaHandler() {
		return new AbstractMediaEventHandler() {
			@Override
			public void onMediaEvent(MediaEvent event) {
				if (event.getType() == ON_PLAY) {
					setPlaying();
				} else {
					setStopped();
				}
			}
		};
	}

	private void addMediaHandlers(AbstractMediaEventHandler handler) {
		addMediaHandler(ON_PAUSE, handler);
		addMediaHandler(ON_END, handler);
		addMediaHandler(ON_STOP, handler);
		addMediaHandler(ON_PLAY, handler);
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
		fireEventFromSource(STOP);
		fireEventFromSource(PLAY);
	}

	private void fireEventFromSource(MediaEventTypes eventType) {
		eventsBus.fireEventFromSource(new MediaEvent(eventType, mediaWrapper), mediaWrapper);
	}

	protected void stop(){
		enabled = false;
		fireEventFromSource(PAUSE);
	}

	protected void setPlaying(){
		enabled = true;
		playing = true;
		button.setStyleName(styleNameConstants.QP_AUDIOPLAYER_BUTTON_PLAYING());
	}


	protected void setStopped(){
		enabled = true;
		playing = false;
		button.setStyleName(styleNameConstants.QP_AUDIOPLAYER_BUTTON());
	}


	protected void playAudio() {
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
