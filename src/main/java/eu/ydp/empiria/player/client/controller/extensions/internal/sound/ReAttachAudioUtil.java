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

package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import com.google.gwt.dom.client.AudioElement;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.ui.FlowPanel;
import eu.ydp.empiria.player.client.media.Audio;

public class ReAttachAudioUtil {

    public Audio reAttachAudio(Audio audio) {
        NodeList<Node> sourceList = getSourceNodes(audio);
        FlowPanel parentPanel = getParentPanelAndRemoveAudioElement(audio);
        Audio newAudio = createNewAudioAndAddToFlowPanel(parentPanel);
        appendChilds(sourceList, newAudio);
        return newAudio;
    }

    public FlowPanel getParentPanelAndRemoveAudioElement(Audio audio) {
        FlowPanel parentPanel = (FlowPanel) audio.getParent();
        parentPanel.remove(audio);
        return parentPanel;
    }

    public Audio createNewAudioAndAddToFlowPanel(FlowPanel panel) {
        Audio newAudio = createAudio();
        newAudio.addToParent(panel);
        return newAudio;
    }

    private void appendChilds(NodeList<Node> sourceList, Audio newAudio) {
        for (int i = 0; i < sourceList.getLength(); i++) {
            newAudio.getElement().appendChild(sourceList.getItem(i));
        }
    }

    protected Audio createAudio() {
        return new Audio();
    }

    private NodeList<Node> getSourceNodes(Audio audio) {
        AudioElement audioElement = audio.getAudioElement();
        return audioElement.getChildNodes();
    }
}
