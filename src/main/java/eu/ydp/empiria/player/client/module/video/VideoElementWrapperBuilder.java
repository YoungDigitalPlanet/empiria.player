/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.module.video;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.gwt.dom.client.MediaElement;
import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.video.structure.SourceBean;
import eu.ydp.empiria.player.client.module.video.wrappers.SourceElementWrapper;
import eu.ydp.empiria.player.client.module.video.wrappers.VideoElementWrapper;
import eu.ydp.empiria.player.client.module.video.wrappers.poster.DefaultPosterUriProvider;

import java.util.Collection;
import java.util.List;

public class VideoElementWrapperBuilder {

    private static final String DEFAULT_PRELOAD = MediaElement.PRELOAD_NONE;
    private static final String DEFAULT_SKIN = "vjs-default-skin";

    private final List<SourceBean> sources = Lists.newArrayList();

    @Inject
    private Provider<SourceElementWrapper> sourceElementWrapperProvider;
    @Inject
    private Provider<VideoElementWrapper> videoElementWrapperProvider;
    @Inject
    private DefaultPosterUriProvider defaultPosterUriProvider;

    private int width = 0;
    private int height = 0;

    private boolean controls = true;

    private Optional<String> skinName = Optional.absent();
    private Optional<String> preload = Optional.absent();
    private Optional<String> poster = Optional.absent();

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
        this.poster = Optional.fromNullable(poster);
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

        videoElement.addClassName(skinName.or(DEFAULT_SKIN));
        videoElement.setPreload(preload.or(DEFAULT_PRELOAD));

        final String defaultPosterUri = defaultPosterUriProvider.getDefaultPosterUri();
        videoElement.setPoster(poster.or(defaultPosterUri));

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
