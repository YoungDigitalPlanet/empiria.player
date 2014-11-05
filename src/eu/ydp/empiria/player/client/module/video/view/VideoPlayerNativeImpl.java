package eu.ydp.empiria.player.client.module.video.view;

import static com.google.gwt.core.client.GWT.*;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.module.video.VideoPlayerControlHandler;

public class VideoPlayerNativeImpl implements VideoPlayerNative {

	private static final String FALLBACK_SWF = getModuleBaseURL() + "/videojs/video-js.swf";
	private JavaScriptObject playerObject;
	private String playerId;

	@Override
	public void initPlayer(String playerId) {
		this.playerId = playerId;
		playerObject = initPlayerNative();
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
		var playerId = this.@eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl::playerId;

		var options = $wnd.document.getElementById(playerId).getAttribute(
				'data-setup')
				|| '{}';
		options = $wnd.vjs.JSON.parse(options);

		return $wnd.vjs(playerId, options);
	}-*/;

	@Override
	public void disablePointerEvents() {
		disablePointerEventsNative();
	}

	private native void disablePointerEventsNative() /*-{
		var playListeners = $wnd.$('video').add('.vjs-poster').add(
				'.vjs-big-play-button');
		playListeners.css("pointer-events", "none");
	}-*/;

	@Override
	public void disposeCurrentPlayer() {
		disposeCurrentPlayerNative();
	}

	private native void disposeCurrentPlayerNative() /*-{
		var playerId = this.@eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl::playerId;

		if ($wnd.vjs.players[playerId]) {
			$wnd.vjs.players[playerId].dispose();
		}

	}-*/;

	@Override
	public void addPlayHandler(VideoPlayerControlHandler handler) {
		addEventHandler("play", handler);
	}

	;

	@Override
	public void addPauseHandler(VideoPlayerControlHandler handler) {
		addEventHandler("pause", handler);
	}

	;

	@Override
	public void addEndedHandler(VideoPlayerControlHandler handler) {
		addEventHandler("ended", handler);
	}

	;

	@Override
	public void addTimeUpdateHandler(VideoPlayerControlHandler handler) {
		addEventHandler("timeupdate", handler);
	}

	@Override
	public void addLoadStartHandler(VideoPlayerControlHandler handler) {
		addEventHandler("loadstart", handler);
	}

	;

	@Override
	public void addLoadedMetadataHandler(VideoPlayerControlHandler handler) {
		addEventHandler("loadedmetadata", handler);
	}

	;

	@Override
	public void addLoadedDataHandler(VideoPlayerControlHandler handler) {
		addEventHandler("loadeddata", handler);
	}

	@Override
	public void addLoadedAllDataHandler(VideoPlayerControlHandler handler) {
		addEventHandler("loadedalldata", handler);
	}

	@Override
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
								handler.@eu.ydp.empiria.player.client.module.video.VideoPlayerControlHandler::handle(Leu/ydp/empiria/player/client/module/video/VideoPlayerControl;)(javaPlayer);
							});
		}
	}-*/;
}
