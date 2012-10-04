package eu.ydp.empiria.player.client.media;

import com.google.gwt.dom.client.Document;



public class Audio extends com.google.gwt.media.client.Audio {

	public Audio() {
		super(Document.get().createAudioElement());
	}

}
