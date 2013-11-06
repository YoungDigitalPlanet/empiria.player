package eu.ydp.empiria.player.client.module.videojs.view;

import static com.google.gwt.core.client.GWT.*;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.dom.client.SourceElement;
import com.google.gwt.dom.client.VideoElement;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.gwtutil.client.debug.gwtlogger.Logger;

public class VideoJsPlayer extends Widget {

	private static final String FALLBACK_SWF = getModuleBaseURL() + "/videojs/video-js.swf";
	private static final String DEFAULT_PRELOAD = MediaElement.PRELOAD_NONE;

	private final int width;
	private final int height;

	private String skinName = "lpc-skin";
	private boolean controls = true;
	private String preload = DEFAULT_PRELOAD;
	private String poster = null;
	private int startPosition = 0;
	private boolean isLoaded = false;

	private final List<String> sources = new ArrayList<String>();
	private final List<String> sourceType = new ArrayList<String>();

	private String playerId;
	private JavaScriptObject playerObject;
	Logger logger = new Logger();

	public VideoJsPlayer(int width, int height) {
		// if (VideoJsPlayerResources.RESOURCES.css().ensureInjected()) {
		// setFlashFallback();
		// }

		this.width = width;
		this.height = height;

		setElement(Document.get().createDivElement());
	}

	@Override
	protected void onLoad() {
		if (!isLoaded) {

			loadPlayer();

			isLoaded = true;
		}
	}

	private void loadPlayer() {
		playerId = Document.get().createUniqueId();

		VideoElement videoElem = Document.get().createVideoElement();

		videoElem.setId(playerId);
		videoElem.addClassName("video-js");
		videoElem.setWidth(width);
		videoElem.setHeight(height);

		if (skinName != null) {
			videoElem.addClassName(skinName);
		}

		videoElem.setControls(controls);

		if (preload != null) {
			videoElem.setPreload(preload);
		} else {
			videoElem.setPreload(DEFAULT_PRELOAD);
		}

		if (poster != null) {
			videoElem.setPoster(poster);
		}

		if ((sources.size() == 0) || (sources.size() != sourceType.size())) {
			throw new IllegalArgumentException("Wrong number of video sources");
		}

		for (int i = 0; i < sources.size(); i++) {
			SourceElement srcElem = Document.get().createSourceElement();

			srcElem.setSrc(sources.get(i));
			srcElem.setType(sourceType.get(i));

			videoElem.appendChild(srcElem);
		}

		getElement().appendChild(videoElem);

		this.playerObject = initPlayer();

		if ((startPosition != 0) && !isFlashFallback()) { // Because of lack
															// in
															// progressive
															// download for
															// flash
			addDurationChangeHandler(new VideoJsPlayerHandler() {
				@Override
				public void handle(VideoJsPlayer player) {
					player.pause();
					player.setCurrentTime(startPosition);
					player.play();
				}
			});
		}

		addLoadedmetadataHandler(new VideoJsPlayerHandler() {

			@Override
			public void handle(VideoJsPlayer player) {
				logger.info("addLoadedmetadataHandler");

			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.Widget#onUnload()
	 */
	@Override
	protected void onUnload() {
		super.onUnload();

		this.playerObject = null;
	}

	/**
	 * Start the video playback.
	 */
	public native void play() /*-{
		var player = this.@eu.ydp.empiria.player.client.module.videojs.view.VideoJsPlayer::playerObject;

		if (player) {
			player.play();
		}
	}-*/;

	/**
	 * Pause the video playback.
	 */
	public native void pause() /*-{
		var player = this.@eu.ydp.empiria.player.client.module.videojs.view.VideoJsPlayer::playerObject;

		if (player) {
			player.pause();
		}
	}-*/;

	/**
	 * Returns the current time of the video in seconds.
	 * 
	 * @return
	 */
	public native float getCurrentTime() /*-{
		var player = this.@eu.ydp.empiria.player.client.module.videojs.view.VideoJsPlayer::playerObject;

		if (player) {
			return player.currentTime();
		}
	}-*/;

	/**
	 * Seek to the supplied time (seconds).
	 * 
	 * @param position
	 */
	public native void setCurrentTime(float position) /*-{
		var player = this.@eu.ydp.empiria.player.client.module.videojs.view.VideoJsPlayer::playerObject;

		if (player) {
			player.currentTime(position);
		}
	}-*/;

	/**
	 * Check are we using flash fallback for current video.
	 * 
	 * @return
	 */
	public native boolean isFlashFallback() /*-{
		var objects = $wnd.document
				.getElementById(
						this.@eu.ydp.empiria.player.client.module.videojs.view.VideoJsPlayer::playerId)
				.getElementsByTagName('object');

		return ((objects != null) && (objects.length != 0));
	}-*/;

	/**
	 * @param startPosition
	 *            the startPosition to set
	 */
	public void setStartPosition(int startPosition) {
		this.startPosition = startPosition;
	}

	/**
	 * Fired whenever the media begins or resumes playback.
	 * 
	 * @param handler
	 */
	public void addPlayHandler(VideoJsPlayerHandler handler) {
		addEventHandler("play", handler);
	};

	public void addLoadedmetadataHandler(VideoJsPlayerHandler handler) {
		addEventHandler("loadedmetadata", handler);
	};

	/**
	 * Fired whenever the media has been paused.
	 * 
	 * @param handler
	 */
	public void addPauseHandler(VideoJsPlayerHandler handler) {
		addEventHandler("pause", handler);
	};

	/**
	 * Fired when the end of the media resource is reached. currentTime ==
	 * duration
	 * 
	 * @param handler
	 */
	public void addEndedHandler(VideoJsPlayerHandler handler) {
		addEventHandler("ended", handler);
	};

	/**
	 * Fired when the current playback position has changed. During playback
	 * this is fired every 15-250 milliseconds, depending on the playback
	 * technology in use.
	 * 
	 * @param handler
	 */
	public void addTimeUpdateHandler(VideoJsPlayerHandler handler) {
		addEventHandler("timeupdate", handler);
	}

	/**
	 * Fired when the user agent begins looking for media data.
	 * 
	 * @param handler
	 */
	public void addLoadStartHandler(VideoJsPlayerHandler handler) {
		addEventHandler("loadstart", handler);
	};

	/**
	 * Fired when the player has initial duration and dimension information.
	 * 
	 * @param handler
	 */
	public void addLoadedMetadataHandler(VideoJsPlayerHandler handler) {
		addEventHandler("loadedmetadata", handler);
	};

	/**
	 * Fired when the player has downloaded data at the current playback
	 * position.
	 * 
	 * @param handler
	 */
	public void addLoadedDataHandler(VideoJsPlayerHandler handler) {
		addEventHandler("loadeddata", handler);
	}

	/**
	 * Fired when the player has finished downloading the source data.
	 * 
	 * @param handler
	 */
	public void addLoadedAllDataHandler(VideoJsPlayerHandler handler) {
		addEventHandler("loadedalldata", handler);
	}

	/**
	 * Fired when the duration of the media resource is changed, or known for
	 * the first time.
	 * 
	 * @param handler
	 */
	public void addDurationChangeHandler(VideoJsPlayerHandler handler) {
		addEventHandler("durationchange", handler);
	}

	/**
	 * Set skin name.
	 * 
	 * @param skinName
	 *            the skinName to set
	 */
	public void setSkinName(String skinName) {
		this.skinName = skinName;
	}

	/**
	 * Show controls for the player.
	 * 
	 * @param controls
	 *            the controls to set
	 */
	public void setControls(boolean controls) {
		this.controls = controls;
	}

	/**
	 * Set preload type for the player. MediaElement.PRELOAD_NONE by default
	 * 
	 * @param preload
	 *            the preload to set
	 */
	public void setPreload(String preload) {
		this.preload = preload;
	}

	/**
	 * Add source for video tag. Type value could be from class VideoElement
	 * 
	 * @param src
	 * @param type
	 */
	public void addSource(String src, String type) {
		sources.add(src);
		sourceType.add(type);
	}

	/**
	 * @param poster
	 *            the poster to set
	 */
	public void setPoster(String poster) {
		this.poster = poster;
	}

	private native void setFlashFallback() /*-{
		$wnd.vjs.options.flash.swf = @eu.ydp.empiria.player.client.module.videojs.view.VideoJsPlayer::FALLBACK_SWF;
	}-*/;

	private native JavaScriptObject initPlayer() /*-{
		return $wnd
				.vjs(
						this.@eu.ydp.empiria.player.client.module.videojs.view.VideoJsPlayer::playerId,
						{
							"nativeControlsForTouch" : false,
							"controls" : true
						}, function() {
						});
	}-*/;

	private native void addEventHandler(String event, VideoJsPlayerHandler handler) /*-{
		var player = this.@eu.ydp.empiria.player.client.module.videojs.view.VideoJsPlayer::playerObject;
		var javaPlayer = this;

		if (player) {
			player
					.on(
							event,
							function() {
								handler.@eu.ydp.empiria.player.client.module.videojs.view.VideoJsPlayerHandler::handle(Leu/ydp/empiria/player/client/module/videojs/view/VideoJsPlayer;)(javaPlayer);
							});
		}
	}-*/;
}
