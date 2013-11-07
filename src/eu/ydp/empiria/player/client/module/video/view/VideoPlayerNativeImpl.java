package eu.ydp.empiria.player.client.module.video.view;

import static com.google.gwt.core.client.GWT.getModuleBaseURL;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.VideoElement;

public class VideoPlayerNativeImpl implements VideoPlayerNative {

	private static final String FALLBACK_SWF = getModuleBaseURL() + "/videojs/video-js.swf";
	private JavaScriptObject playerObject;
	private String playerId;
	private int startPosition = 0;

	@Override
	public VideoElement createVideoElement() {
		playerId = Document.get().createUniqueId();

		VideoElement videoElem = Document.get().createVideoElement();

		videoElem.setId(playerId);
		videoElem.addClassName("video-js");

		return videoElem;
	}

	public void initPlayer() {
		playerObject = initPlayerNative();

		if ((startPosition != 0) && !isFlashFallback()) { // Because of lack
															// in
															// progressive
															// download for
															// flash
			addDurationChangeHandler(new VideoPlayerHandler() {
				@Override
				public void handle(VideoPlayer player) {
					player.pause();
					player.setCurrentTime(startPosition);
					player.play();
				}
			});
		}
	}

	@Override
	public void unload() {
		this.playerObject = null;
	}

	@Override
	public native void play() /*-{
		var player = this.@eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl::playerObject;

		if (player) {
			player.play();
		}
	}-*/;

	@Override
	public native void pause() /*-{
		var player = this.@eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl::playerObject;

		if (player) {
			player.pause();
		}
	}-*/;

	@Override
	public native void setCurrentTime(float position) /*-{
		var player = this.@eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl::playerObject;

		if (player) {
			player.currentTime(position);
		}
	}-*/;

	@Override
	public native float getCurrentTime() /*-{
		var player = this.@eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl::playerObject;

		if (player) {
			return player.currentTime();
		}
	}-*/;

	private native void setFlashFallback() /*-{
		$wnd.vjs.options.flash.swf = @eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl::FALLBACK_SWF;
	}-*/;

	private native boolean isFlashFallback() /*-{
		var objects = $wnd.document
				.getElementById(
						this.@eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl::playerId)
				.getElementsByTagName('object');

		return ((objects != null) && (objects.length != 0));
	}-*/;

	private native JavaScriptObject initPlayerNative() /*-{
		return $wnd
				.vjs(
						this.@eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl::playerId,
						{}, function() {
						});
	}-*/;

	public void addPlayHandler(VideoPlayerHandler handler) {
		addEventHandler("play", handler);
	};

	public void addPauseHandler(VideoPlayerHandler handler) {
		addEventHandler("pause", handler);
	};

	public void addEndedHandler(VideoPlayerHandler handler) {
		addEventHandler("ended", handler);
	};

	public void addTimeUpdateHandler(VideoPlayerHandler handler) {
		addEventHandler("timeupdate", handler);
	}

	public void addLoadStartHandler(VideoPlayerHandler handler) {
		addEventHandler("loadstart", handler);
	};

	public void addLoadedMetadataHandler(VideoPlayerHandler handler) {
		addEventHandler("loadedmetadata", handler);
	};

	public void addLoadedDataHandler(VideoPlayerHandler handler) {
		addEventHandler("loadeddata", handler);
	}

	public void addLoadedAllDataHandler(VideoPlayerHandler handler) {
		addEventHandler("loadedalldata", handler);
	}

	public void addDurationChangeHandler(VideoPlayerHandler handler) {
		addEventHandler("durationchange", handler);
	}

	private native void addEventHandler(String event, VideoPlayerHandler handler) /*-{
		var player = this.@eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl::playerObject;
		var javaPlayer = this;

		if (player) {
			player
					.on(
							event,
							function() {
								handler.@eu.ydp.empiria.player.client.module.video.view.VideoPlayerHandler::handle(Leu/ydp/empiria/player/client/module/video/view/VideoPlayer;)(javaPlayer);
							});
		}
	}-*/;
}
