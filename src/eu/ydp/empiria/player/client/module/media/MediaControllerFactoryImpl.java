package eu.ydp.empiria.player.client.module.media;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.gin.factory.VideoTextTrackElementFactory;
import eu.ydp.empiria.player.client.media.texttrack.TextTrackKind;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.media.button.AbstractMediaController;
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

public class MediaControllerFactoryImpl implements MediaControllerFactory {

	//@Inject
	protected MediaProgressBar mediaProgerssBar;
	@Inject
	protected VideoTextTrackElementFactory videoTextTrackElementFactory;

	@Inject
	protected Provider<FullScreenMediaButton> fullScreenMediaButtonProvider;

	@Override
	public AbstractMediaController<?> get(ModuleTagName moduleType) {
		AbstractMediaController<?> mediaController = null;
		if (moduleType != null) {
			switch (moduleType) {
			case MEDIA_PLAY_PAUSE_BUTTON:
				mediaController = new PlayPauseMediaButton();
				break;
			case MEDIA_STOP_BUTTON:
				mediaController = new StopMediaButton();
				break;
			case MEDIA_MUTE_BUTTON:
				mediaController = new MuteMediaButton();
				break;
			case MEDIA_PROGRESS_BAR:
				mediaController = GWT.create(MediaProgressBar.class);// (AbstractMediaController<?>) ((AbstractMediaController<?>) mediaProgerssBar).getNewInstance();
				break;
			case MEDIA_FULL_SCREEN_BUTTON:
				mediaController = fullScreenMediaButtonProvider.get();
				break;
			case MEDIA_POSITION_IN_STREAM:
				mediaController = new PositionInMediaStream();
				break;
			case MEDIA_VOLUME_BAR:
				mediaController = new VolumeMediaButton();
				break;
			case MEDIA_CURRENT_TIME:
				mediaController = new MediaCurrentTime();
				break;
			case MEDIA_TOTAL_TIME:
				mediaController = new MediaTotalTime();
				break;
			default:
				break;
			}
		}
		return mediaController;
	}

	@Override
	public MediaController<?> get(ModuleTagName moduleType, Object... args) {
		MediaController<?> controller = null;
		if (args == null || args.length == 0) {
			controller = get(moduleType);
		} else if (moduleType == ModuleTagName.MEDIA_TEXT_TRACK && args.length == 1 && args[0] instanceof TextTrackKind) {
			controller = videoTextTrackElementFactory.getVideoTextTrackElement((TextTrackKind) args[0]);
		}
		return controller;
	}

}
