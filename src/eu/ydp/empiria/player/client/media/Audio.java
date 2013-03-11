package eu.ydp.empiria.player.client.media;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.FlowPanel;



public class Audio extends com.google.gwt.media.client.Audio {

	public Audio() {
		super(Document.get().createAudioElement());
	}
	
	public void addToParent(FlowPanel panel) {
		panel.add(this);
	}
}
