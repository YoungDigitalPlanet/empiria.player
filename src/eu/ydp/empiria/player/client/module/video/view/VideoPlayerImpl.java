package eu.ydp.empiria.player.client.module.video.view;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class VideoPlayerImpl extends Widget implements VideoPlayer/*, VideoPlayerControl*/ {

	private final VideoPlayerNative nativePlayer;
	private VideoElementWrapper videoElementWrapper;

	private boolean isLoaded = false;

	@Inject
	public VideoPlayerImpl(@Assisted VideoElementWrapper videoElementWrapper, VideoPlayerNative nativePlayer) {
		this.nativePlayer = nativePlayer;
		this.videoElementWrapper = videoElementWrapper;
		setElement(Document.get().createDivElement());
	}

	@Override
	protected void onLoad() {
		if (!isLoaded) {
			getElement().appendChild(videoElementWrapper.asNode());

			String playerId = videoElementWrapper.getId();
			nativePlayer.initPlayer(playerId);

			isLoaded = true;
		}
	}

	@Override
	protected void onUnload() {
		super.onUnload();
		nativePlayer.unload();
	}

//	@Override
//	public void play() {
//		nativePlayer.play();
//	}
//
//	@Override
//	public void pause() {
//		nativePlayer.pause();
//	}
//
//	@Override
//	public void setCurrentTime(float position) {
//		nativePlayer.setCurrentTime(position);
//	}
//
//	@Override
//	public float getCurrentTime() {
//		return nativePlayer.getCurrentTime();
//	}
//
//	@Override
//	public void addPlayHandler(VideoPlayerControlHandler handler) {
//		nativePlayer.addPlayHandler(handler);
//	};
//
//	@Override
//	public void addPauseHandler(VideoPlayerControlHandler handler) {
//		nativePlayer.addPauseHandler(handler);
//	};
//
//	@Override
//	public void addEndedHandler(VideoPlayerControlHandler handler) {
//		nativePlayer.addEndedHandler(handler);
//	};
//
//	@Override
//	public void addTimeUpdateHandler(VideoPlayerControlHandler handler) {
//		nativePlayer.addTimeUpdateHandler(handler);
//	}
//
//	@Override
//	public void addLoadStartHandler(VideoPlayerControlHandler handler) {
//		nativePlayer.addLoadStartHandler(handler);
//	};
//
//	@Override
//	public void addLoadedMetadataHandler(VideoPlayerControlHandler handler) {
//		nativePlayer.addLoadedMetadataHandler(handler);
//	};
//
//	@Override
//	public void addLoadedDataHandler(VideoPlayerControlHandler handler) {
//		nativePlayer.addLoadedDataHandler(handler);
//	}
//
//	@Override
//	public void addLoadedAllDataHandler(VideoPlayerControlHandler handler) {
//		nativePlayer.addLoadedAllDataHandler(handler);
//	}
//
//	@Override
//	public void addDurationChangeHandler(VideoPlayerControlHandler handler) {
//		nativePlayer.addDurationChangeHandler(handler);
//	}
}
