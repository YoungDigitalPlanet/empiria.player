package eu.ydp.empiria.player.client.module.video.view;

import static com.google.gwt.core.client.GWT.getModuleBaseURL;

import com.google.gwt.core.client.JavaScriptObject;

public class VideoPlayerNativeImpl implements VideoPlayerNative {

	private static final String FALLBACK_SWF = getModuleBaseURL() + "/videojs/video-js.swf";
	private JavaScriptObject playerObject;
	private String playerId;
	private int startPosition = 0;

	public void initPlayer(String playerId) {
		this.playerId = playerId;
		playerObject = initPlayerNative();

		if ((startPosition != 0) && !isFlashFallback()) { // Because of lack
															// in
															// progressive
															// download for
															// flash
			addDurationChangeHandler(new VideoPlayerControlHandler() {
				@Override
				public void handle(VideoPlayerControl playerControl) {
					playerControl.pause();
					playerControl.setCurrentTime(startPosition);
					playerControl.play();
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

	public void addPlayHandler(VideoPlayerControlHandler handler) {
		addEventHandler("play", handler);
	};

	public void addPauseHandler(VideoPlayerControlHandler handler) {
		addEventHandler("pause", handler);
	};

	public void addEndedHandler(VideoPlayerControlHandler handler) {
		addEventHandler("ended", handler);
	};

	public void addTimeUpdateHandler(VideoPlayerControlHandler handler) {
		addEventHandler("timeupdate", handler);
	}

	public void addLoadStartHandler(VideoPlayerControlHandler handler) {
		addEventHandler("loadstart", handler);
	};

	public void addLoadedMetadataHandler(VideoPlayerControlHandler handler) {
		addEventHandler("loadedmetadata", handler);
	};

	public void addLoadedDataHandler(VideoPlayerControlHandler handler) {
		addEventHandler("loadeddata", handler);
	}

	public void addLoadedAllDataHandler(VideoPlayerControlHandler handler) {
		addEventHandler("loadedalldata", handler);
	}

	public void addDurationChangeHandler(VideoPlayerControlHandler handler) {
		addEventHandler("durationchange", handler);
	}

	private native void addEventHandler(String event, VideoPlayerControlHandler handler) /*-{
		var player = this.@eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl::playerObject;
		var javaPlayer = this;

		if (player) {
			player
					.on(
							event,
							function() {
								handler.@eu.ydp.empiria.player.client.module.video.view.VideoPlayerControlHandler::handle(Leu/ydp/empiria/player/client/module/video/view/VideoPlayerControl;)(javaPlayer);
							});
		}
	}-*/;
}
