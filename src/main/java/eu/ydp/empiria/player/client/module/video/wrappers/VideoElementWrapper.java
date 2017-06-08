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

package eu.ydp.empiria.player.client.module.video.wrappers;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.VideoElement;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

import java.util.ArrayList;
import java.util.List;

public class VideoElementWrapper {

    private static final String VIDEOJS_SETUP_ATTRIBUTE = "data-setup";
    private final VideoElement videoElement;

    public VideoElementWrapper(VideoElement videoElement) {
        this.videoElement = videoElement;
    }

    public void setWidth(int width) {
        videoElement.setWidth(width);
    }

    public void setHeight(int height) {
        videoElement.setHeight(height);
    }

    public void setControls(boolean controls) {
        videoElement.setControls(controls);
    }

    public boolean addClassName(String className) {
        return videoElement.addClassName(className);
    }

    public void setPreload(String preload) {
        videoElement.setPreload(preload);
    }

    public void setPoster(String posterUrl) {
        if (UserAgentChecker.isMobileUserAgent()) {
            videoElement.setPoster(posterUrl);
        } else {
            videoElement.setAttribute(VIDEOJS_SETUP_ATTRIBUTE, posterSetupAttributeValue(posterUrl));
        }
    }

    public <T extends Node> T appendChild(T newChild) {
        return videoElement.appendChild(newChild);
    }

    public String getId() {
        return videoElement.getId();
    }

    public Node asNode() {
        return videoElement;
    }

    private String posterSetupAttributeValue(String posterUrl) {
        return "{\"poster\" : \"" + posterUrl + "\" }";
    }

    public List<String> getSources() {
        List<String> sources = new ArrayList<>();
        NodeList<Element> childList = videoElement.getElementsByTagName("source");
        for (int i = 0; i < childList.getLength(); i++) {
            Element element = childList.getItem(i);
            String source = element.getAttribute("src");
            sources.add(source);
        }
        return sources;
    }
}
