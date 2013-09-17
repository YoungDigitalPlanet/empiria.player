package eu.ydp.empiria.player.client.module.object.template;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.media.texttrack.TextTrackKind;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.media.MediaControllerFactory;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.button.MediaController;
import eu.ydp.empiria.player.client.module.media.button.MediaControllerWrapper;
import eu.ydp.empiria.player.client.module.media.button.VideoFullScreenMediaButton;
import eu.ydp.empiria.player.client.util.AbstractTemplateParser;
import eu.ydp.gwtutil.client.debug.gwtlogger.Logger;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class ObjectTemplateParser<T extends Widget> extends AbstractTemplateParser {
	
	private static final Logger LOGGER = new Logger();
	protected static final Set<String> CONTROLLERS = new HashSet<String>();
	protected MediaWrapper<?> mediaWrapper;
	protected MediaWrapper<?> fullScreenMediaWrapper;

	@Inject
	protected MediaControllerFactory factory;

	private Element fullScreenTemplate;
	/**
	 * czy jest renderowany w trybie fullscreen
	 */
	private boolean fullScreen = false;

	public ObjectTemplateParser() {
		if (CONTROLLERS.isEmpty()) {
			CONTROLLERS.add(ModuleTagName.MEDIA_PLAY_PAUSE_BUTTON.tagName());
			CONTROLLERS.add(ModuleTagName.MEDIA_PLAY_STOP_BUTTON.tagName());
			CONTROLLERS.add(ModuleTagName.MEDIA_STOP_BUTTON.tagName());
			CONTROLLERS.add(ModuleTagName.MEDIA_MUTE_BUTTON.tagName());
			CONTROLLERS.add(ModuleTagName.MEDIA_PROGRESS_BAR.tagName());
			CONTROLLERS.add(ModuleTagName.MEDIA_FULL_SCREEN_BUTTON.tagName());
			CONTROLLERS.add(ModuleTagName.MEDIA_POSITION_IN_STREAM.tagName());
			CONTROLLERS.add(ModuleTagName.MEDIA_VOLUME_BAR.tagName());
			CONTROLLERS.add(ModuleTagName.MEDIA_CURRENT_TIME.tagName());
			CONTROLLERS.add(ModuleTagName.MEDIA_TOTAL_TIME.tagName());
			CONTROLLERS.add(ModuleTagName.MEDIA_TEXT_TRACK.tagName());
			CONTROLLERS.add(ModuleTagName.MEDIA_SCREEN.tagName());
		}
	}

	protected Widget getMediaObject() {
		Widget mediaObjectWidget;
		if (fullScreen) {
			mediaObjectWidget = fullScreenMediaWrapper.getMediaObject();
		} else { 
			FlowPanel videoContainer = new FlowPanel();
			videoContainer.add(mediaWrapper.getMediaObject());
			videoContainer.getElement().getStyle().setPosition(Position.RELATIVE);
			mediaObjectWidget = videoContainer;
		}
		return mediaObjectWidget;
	}

	@Override
	public void beforeParse(Node mainNode, Widget parent) {
		// kompatybilnosc wsteczna z szablonami bez media_screen
		if(fullScreen && !isModuleInTemplate(ModuleTagName.MEDIA_SCREEN.tagName())) {
			Widget parentWrapper = parent.getParent();
			if(parentWrapper instanceof FlowPanel) {
				FlowPanel parentPanel = (FlowPanel) parentWrapper;
				parentPanel.add(getMediaObject());
			} else {
				LOGGER.warning("Cannot attach mediaScreen to: "+parentWrapper.getClass().getName());
			}
		} else 
			if (!isModuleInTemplate(ModuleTagName.MEDIA_SCREEN.tagName()) && parent instanceof HasWidgets) {
			((HasWidgets) parent).add(getMediaObject());
		}
	}

	@Override
	public void parse(Node mainNode, Widget parent) {
		super.parse(mainNode, parent);

	}

	public void setMediaWrapper(MediaWrapper<?> mediaDescriptor) {
		this.mediaWrapper = mediaDescriptor;
	}

	public void setFullScreenMediaWrapper(MediaWrapper<?> fullScreenMediaWrapper) {
		this.fullScreenMediaWrapper = fullScreenMediaWrapper;
	}

	@Override
	protected MediaController<?> getMediaControllerNewInstance(String moduleName, Node node) {
		MediaController<?> controller = null;
		if (ModuleTagName.MEDIA_TEXT_TRACK.tagName().equals(moduleName)) {
			String kind = XMLUtils.getAttributeAsString((Element) node, "kind", TextTrackKind.SUBTITLES.name());
			TextTrackKind trackKind = TextTrackKind.SUBTITLES;
			try {
				trackKind = TextTrackKind.valueOf(kind.toUpperCase()); // NOPMD
			} catch (IllegalArgumentException exception) { // NOPMD

			}
			controller = factory.get(ModuleTagName.MEDIA_TEXT_TRACK, trackKind);
		} else if (ModuleTagName.MEDIA_SCREEN.tagName().equals(moduleName)) {
			controller = new MediaControllerWrapper<Widget>(getMediaObject());
		} else {
			controller = factory.get(ModuleTagName.getTag(moduleName));
		}
		if (controller != null) {
			controller.setMediaDescriptor(mediaWrapper);
			controller.setFullScreen(fullScreen);
		}
		if (controller instanceof VideoFullScreenMediaButton) {
			((VideoFullScreenMediaButton) controller).setFullScreenTemplate(fullScreenTemplate);
			((VideoFullScreenMediaButton) controller).setMediaWrapper(mediaWrapper);
			((VideoFullScreenMediaButton) controller).setFullScreenMediaWrapper(fullScreenMediaWrapper);
		}

		return controller;
	}

	@Override
	protected boolean isModuleSupported(String moduleName) {
		return CONTROLLERS.contains(moduleName);
	}

	public void setFullScreenTemplate(Element fullScreenTemplate) {
		this.fullScreenTemplate = fullScreenTemplate;
	}

	public void setFullScreen(boolean fullScreen) {
		this.fullScreen = fullScreen;
	}
}
