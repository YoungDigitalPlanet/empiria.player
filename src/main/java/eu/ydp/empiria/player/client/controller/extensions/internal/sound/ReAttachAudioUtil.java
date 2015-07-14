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
