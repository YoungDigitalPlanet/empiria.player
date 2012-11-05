package eu.ydp.empiria.player.client.module.object.template;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.media.texttrack.TextTrackKind;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.media.MediaControllerFactory;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.button.FullScreenMediaButton;
import eu.ydp.empiria.player.client.module.media.button.MediaController;
import eu.ydp.empiria.player.client.util.AbstractTemplateParser;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class ObjectTemplateParser<T extends Widget> extends AbstractTemplateParser {
	protected Set<String> controllers = new HashSet<String>();
	protected MediaWrapper<?> mediaDescriptor = null;
	protected MediaWrapper<?> fullScreenMediaWrapper = null;

	@Inject
	protected MediaControllerFactory factory;

	private Element fullScreenTemplate;
	private  boolean fullScreen = false;

	public ObjectTemplateParser() {
		controllers.add(ModuleTagName.MEDIA_PLAY_PAUSE_BUTTON.tagName());
		controllers.add(ModuleTagName.MEDIA_STOP_BUTTON.tagName());
		controllers.add(ModuleTagName.MEDIA_MUTE_BUTTON.tagName());
		controllers.add(ModuleTagName.MEDIA_PROGRESS_BAR.tagName());
		controllers.add(ModuleTagName.MEDIA_FULL_SCREEN_BUTTON.tagName());
		controllers.add(ModuleTagName.MEDIA_POSITION_IN_STREAM.tagName());
		controllers.add(ModuleTagName.MEDIA_VOLUME_BAR.tagName());
		controllers.add(ModuleTagName.MEDIA_CURRENT_TIME.tagName());
		controllers.add(ModuleTagName.MEDIA_TOTAL_TIME.tagName());
		controllers.add(ModuleTagName.MEDIA_TEXT_TRACK.tagName());
	}

	public void setMediaWrapper(MediaWrapper<?> mediaDescriptor) {
		this.mediaDescriptor = mediaDescriptor;
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
		} else {
			controller = factory.get(ModuleTagName.getTag(moduleName));
		}
		if (controller != null) {
			controller.setMediaDescriptor(mediaDescriptor);
			controller.setFullScreen(fullScreen);
		}
		if (controller instanceof FullScreenMediaButton) {
			((FullScreenMediaButton) controller).setFullScreenTemplate(fullScreenTemplate);
			((FullScreenMediaButton) controller).setMediaWrapper(mediaDescriptor);
			((FullScreenMediaButton) controller).setFullScreenMediaWrapper(fullScreenMediaWrapper);
		}

		return controller;
	}

	@Override
	protected boolean isModuleSupported(String moduleName) {
		return controllers.contains(moduleName);
	}

	public void setFullScreenTemplate(Element fullScreenTemplate) {
		this.fullScreenTemplate = fullScreenTemplate;
	}

	public void setFullScreen(boolean fullScreen) {
		this.fullScreen = fullScreen;
	}
}
