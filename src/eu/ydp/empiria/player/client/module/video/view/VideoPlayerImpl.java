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

public class VideoPlayerImpl extends Widget implements VideoPlayer {

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
	public VideoPlayerImpl(VideoPlayerNative nativePlayer, Provider<SourceElement> sourceElementProvider) {
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

	@Override
	public void play() {
		nativePlayer.play();
	}

	@Override
	public void pause() {
		nativePlayer.pause();
	}

	@Override
	public void setCurrentTime(float position) {
		nativePlayer.setCurrentTime(position);
	}

	@Override
	public float getCurrentTime() {
		return nativePlayer.getCurrentTime();
	}

	@Override
	public void addPlayHandler(VideoPlayerHandler handler) {
		nativePlayer.addPlayHandler(handler);
	};

	@Override
	public void addPauseHandler(VideoPlayerHandler handler) {
		nativePlayer.addPauseHandler(handler);
	};

	@Override
	public void addEndedHandler(VideoPlayerHandler handler) {
		nativePlayer.addEndedHandler(handler);
	};

	@Override
	public void addTimeUpdateHandler(VideoPlayerHandler handler) {
		nativePlayer.addTimeUpdateHandler(handler);
	}

	@Override
	public void addLoadStartHandler(VideoPlayerHandler handler) {
		nativePlayer.addLoadStartHandler(handler);
	};

	@Override
	public void addLoadedMetadataHandler(VideoPlayerHandler handler) {
		nativePlayer.addLoadedMetadataHandler(handler);
	};

	@Override
	public void addLoadedDataHandler(VideoPlayerHandler handler) {
		nativePlayer.addLoadedDataHandler(handler);
	}

	@Override
	public void addLoadedAllDataHandler(VideoPlayerHandler handler) {
		nativePlayer.addLoadedAllDataHandler(handler);
	}

	@Override
	public void addDurationChangeHandler(VideoPlayerHandler handler) {
		nativePlayer.addDurationChangeHandler(handler);
	}

	@Override
	public void setSkinName(String skinName) {
		this.skinName = skinName;
	}

	@Override
	public void setControls(boolean controls) {
		this.controls = controls;
	}

	@Override
	public void setPreload(String preload) {
		this.preload = preload;
	}

	@Override
	public void addSource(SourceBean source) {
		sources.add(source);
	}

	@Override
	public void addSources(Collection<SourceBean> sources) {
		this.sources.addAll(sources);
	}

	@Override
	public void setPoster(String poster) {
		this.poster = poster;
	}

	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public void setHeight(int height) {
		this.height = height;
	}
}
