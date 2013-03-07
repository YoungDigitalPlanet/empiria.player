package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.ui.FlowPanel;

import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.HTML5AudioMediaExecutor;
import eu.ydp.empiria.player.client.media.Audio;
import eu.ydp.empiria.player.client.module.media.html5.AbstractHTML5MediaWrapper;

public class ReCreateAudioHack {

	public void apply(AbstractHTML5MediaWrapper wrapper, HTML5AudioMediaExecutor executor) {
		Audio audio = (Audio) wrapper.getMediaObject();
		Audio newAudio = reAttachAudio(audio);
		wrapper.setMediaObject(newAudio);
		executor.setMedia(newAudio);
		executor.init();
	}

	private Audio reAttachAudio(Audio audio) {
		NodeList<Node> sourceList = audio.getAudioElement().getChildNodes();
		
		FlowPanel parent = (FlowPanel) audio.getParent();
		parent.remove(audio);
		Audio newAudio = createNewAudioAndAddToFlowPanel(parent);
		appendChilds(sourceList, newAudio);

		return newAudio;
	}
	
	private Audio createNewAudioAndAddToFlowPanel(FlowPanel panel) {
		Audio newAudio = new Audio();
		panel.add(newAudio);
		return newAudio;
	}

	private void appendChilds(NodeList<Node> sourceList, Audio newAudio) {
		for (int i = 0; i < sourceList.getLength(); i++) {
			newAudio.getElement().appendChild(sourceList.getItem(i));
		}
	}
	
}
