package eu.ydp.empiria.player.client.module.object.template;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;

import eu.ydp.empiria.player.client.media.texttrack.TextTrackKind;
import eu.ydp.empiria.player.client.media.texttrack.VideoTextTrackElement;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.button.FullScreenMediaButton;
import eu.ydp.empiria.player.client.module.media.button.MediaController;
import eu.ydp.empiria.player.client.module.media.button.MediaProgressBar;
import eu.ydp.empiria.player.client.module.media.button.MuteMediaButton;
import eu.ydp.empiria.player.client.module.media.button.PlayPauseMediaButton;
import eu.ydp.empiria.player.client.module.media.button.StopMediaButton;
import eu.ydp.empiria.player.client.module.media.button.VolumeMediaButton;
import eu.ydp.empiria.player.client.module.media.info.MediaCurrentTime;
import eu.ydp.empiria.player.client.module.media.info.MediaTotalTime;
import eu.ydp.empiria.player.client.module.media.info.PositionInMediaStream;
import eu.ydp.empiria.player.client.util.AbstractTemplateParser;
import eu.ydp.empiria.player.client.util.XMLUtils;

public class ObjectTemplateParser<T extends Widget> extends AbstractTemplateParser {
	protected Map<String, MediaController<?>> controllers = new HashMap<String, MediaController<?>>();
	protected MediaWrapper<T> mediaDescriptor = null;

	public ObjectTemplateParser(MediaWrapper<T> mediaDescriptor) {
		this.mediaDescriptor = mediaDescriptor;
		controllers.put(ModuleTagName.MEDIA_PLAY_PAUSE_BUTTON.tagName(), new PlayPauseMediaButton());
		controllers.put(ModuleTagName.MEDIA_STOP_BUTTON.tagName(), new StopMediaButton());
		controllers.put(ModuleTagName.MEDIA_MUTE_BUTTON.tagName(), new MuteMediaButton());
		controllers.put(ModuleTagName.MEDIA_PROGRESS_BAR.tagName(), (MediaController<?>) GWT.create(MediaProgressBar.class));
		controllers.put(ModuleTagName.MEDIA_FULL_SCREEN_BUTTON.tagName(), new FullScreenMediaButton());
		controllers.put(ModuleTagName.MEDIA_POSITION_IN_STREAM.tagName(), new PositionInMediaStream());
		controllers.put(ModuleTagName.MEDIA_VOLUME_BAR.tagName(), new VolumeMediaButton());
		controllers.put(ModuleTagName.MEDIA_CURRENT_TIME.tagName(), new MediaCurrentTime());
		controllers.put(ModuleTagName.MEDIA_TOTAL_TIME.tagName(), new MediaTotalTime());
		controllers.put(ModuleTagName.MEDIA_TEXT_TRACK.tagName(), null);
	}

	@Override
	protected MediaController<?> getMediaControllerNewInstance(String moduleName, Node node) {
		MediaController<?> controller = null;
		if (ModuleTagName.MEDIA_TEXT_TRACK.tagName().equals(moduleName)) {
			String kind = XMLUtils.getAttributeAsString((Element) node, "kind", TextTrackKind.SUBTITLES.name());
			TextTrackKind trackKind = TextTrackKind.SUBTITLES;
			try {
				trackKind = TextTrackKind.valueOf(kind.toUpperCase());
			} catch (IllegalArgumentException exception) { //NOPMD

			}
			controller = new VideoTextTrackElement(trackKind);
		} else {
			controller = controllers.get(moduleName);
			controller = (MediaController<?>) controller.getNewInstance();
		}
		if (controller != null) {
			controller.setMediaDescriptor(mediaDescriptor);
		}

		return controller;
	}

	@Override
	protected boolean isModuleSupported(String moduleName) {
		return controllers.containsKey(moduleName);
	}

}
