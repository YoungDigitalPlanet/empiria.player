package eu.ydp.empiria.player.client.module.video.view;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.dom.client.SourceElement;
import com.google.gwt.dom.client.VideoElement;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.video.structure.SourceBean;

public class VideoPlayer extends Widget {

	private static final String DEFAULT_PRELOAD = MediaElement.PRELOAD_NONE;

	private VideoPlayerNative nativePlayer;
	private Provider<SourceElement> sourceElementProvider;

	private boolean isLoaded = false;

	private int width;
	private int height;
	private String skinName = "vjs-default-skin";
	private boolean controls = true;
	private String preload = DEFAULT_PRELOAD;
	private String poster = "";
	private final List<SourceBean> sources = Lists.newArrayList();

	@Inject
	public VideoPlayer(VideoPlayerNative nativePlayer, Provider<SourceElement> sourceElementProvider) {
		this.nativePlayer = nativePlayer;
		this.sourceElementProvider = sourceElementProvider;
		setElement(Document.get().createDivElement());
	}

	@Override
	protected void onLoad() {
		if (!isLoaded) {
			VideoElement videoElement = createVideoElement();
			getElement().appendChild(videoElement);
			nativePlayer.initPlayer();
			isLoaded = true;
		}
	}

	private VideoElement createVideoElement() {
		VideoElement videoElement = nativePlayer.createVideoElement();
		videoElement.setWidth(width);
		videoElement.setHeight(height);

		videoElement.addClassName(skinName);
		videoElement.setControls(controls);
		videoElement.setPreload(preload);

		if (Strings.isNullOrEmpty(poster)) {
			videoElement.setPoster(poster);
		}

		if (sources.isEmpty()) {
			throw new IllegalArgumentException("Video sources cannot be empty");
		}

		for (SourceBean source : sources) {
			SourceElement srcElem = sourceElementProvider.get();

			srcElem.setSrc(source.getSrc());
			srcElem.setType(source.getTypeString());

			videoElement.appendChild(srcElem);
		}
		return videoElement;
	}

	@Override
	protected void onUnload() {
		super.onUnload();
		nativePlayer.unload();
	}

	public void play() {
		nativePlayer.play();
	}

	public void pause() {
		nativePlayer.pause();
	}

	public void setCurrentTime(float position) {
		nativePlayer.setCurrentTime(position);
	}

	public void addPlayHandler(VideoPlayerHandler handler) {
		nativePlayer.addPlayHandler(handler);
	};

	public void addPauseHandler(VideoPlayerHandler handler) {
		nativePlayer.addPauseHandler(handler);
	};

	public void addEndedHandler(VideoPlayerHandler handler) {
		nativePlayer.addEndedHandler(handler);
	};

	public void addTimeUpdateHandler(VideoPlayerHandler handler) {
		nativePlayer.addTimeUpdateHandler(handler);
	}

	public void addLoadStartHandler(VideoPlayerHandler handler) {
		nativePlayer.addLoadStartHandler(handler);
	};

	public void addLoadedMetadataHandler(VideoPlayerHandler handler) {
		nativePlayer.addLoadedMetadataHandler(handler);
	};

	public void addLoadedDataHandler(VideoPlayerHandler handler) {
		nativePlayer.addLoadedDataHandler(handler);
	}

	public void addLoadedAllDataHandler(VideoPlayerHandler handler) {
		nativePlayer.addLoadedAllDataHandler(handler);
	}

	public void addDurationChangeHandler(VideoPlayerHandler handler) {
		nativePlayer.addDurationChangeHandler(handler);
	}

	public void setSkinName(String skinName) {
		this.skinName = skinName;
	}

	public void setControls(boolean controls) {
		this.controls = controls;
	}

	public void setPreload(String preload) {
		this.preload = preload;
	}

	public void addSource(SourceBean source) {
		sources.add(source);
	}

	public void addSources(Collection<SourceBean> sources) {
		this.sources.addAll(sources);
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
