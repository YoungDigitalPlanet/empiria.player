package eu.ydp.empiria.player.client.module.video;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gwt.dom.client.MediaElement;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.video.structure.SourceBean;
import eu.ydp.empiria.player.client.module.video.wrappers.SourceElementWrapper;
import eu.ydp.empiria.player.client.module.video.wrappers.VideoElementWrapper;

public class VideoElementWrapperBuilder {

	private static final String DEFAULT_PRELOAD = MediaElement.PRELOAD_NONE;
	private static final String DEFAUL_SKIN = "vjs-default-skin";

	private final List<SourceBean> sources = Lists.newArrayList();

	@Inject
	private Provider<SourceElementWrapper> sourceElementWrapperProvider;
	@Inject
	private Provider<VideoElementWrapper> videoElementWrapperProvider;

	private int width = 0;
	private int height = 0;
	private boolean controls = true;
	private Optional<String> skinName = Optional.absent();
	private Optional<String> preload = Optional.absent();
	private String poster = "";

	public VideoElementWrapperBuilder withWidth(int width) {
		this.width = width;
		return this;
	}

	public VideoElementWrapperBuilder withHeight(int height) {
		this.height = height;
		return this;
	}

	public VideoElementWrapperBuilder withControls(boolean controls) {
		this.controls = controls;
		return this;
	}

	public VideoElementWrapperBuilder withSkinName(String skinName) {
		this.skinName = Optional.fromNullable(skinName);
		return this;
	}

	public VideoElementWrapperBuilder withPreload(String preload) {
		this.preload = Optional.fromNullable(preload);
		return this;
	}

	public VideoElementWrapperBuilder withPoster(String poster) {
		this.poster = poster;
		return this;
	}

	public VideoElementWrapperBuilder withSource(SourceBean source) {
		sources.add(source);
		return this;
	}

	public VideoElementWrapperBuilder withSources(Collection<SourceBean> sources) {
		this.sources.addAll(sources);
		return this;
	}

	public VideoElementWrapper build() {
		VideoElementWrapper videoElement = videoElementWrapperProvider.get();

		videoElement.setWidth(width);
		videoElement.setHeight(height);
		videoElement.setControls(controls);

		videoElement.addClassName(skinName.or(DEFAUL_SKIN));
		videoElement.setPreload(preload.or(DEFAULT_PRELOAD));

		if (!Strings.isNullOrEmpty(poster)) {
			videoElement.setPoster(poster);
		}

		if (sources.isEmpty()) {
			throw new IllegalStateException("Video sources cannot be empty");
		}
		for (SourceBean source : sources) {
			SourceElementWrapper srcElem = sourceElementWrapperProvider.get();

			srcElem.setSrc(source.getSrc());
			srcElem.setType(source.getTypeString());

			videoElement.appendChild(srcElem.asNode());
		}

		return videoElement;
	}
}
