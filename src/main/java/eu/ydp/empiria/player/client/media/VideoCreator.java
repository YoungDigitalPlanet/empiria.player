package eu.ydp.empiria.player.client.media;

import com.google.gwt.core.client.GWT;
import eu.ydp.empiria.player.client.module.object.impl.Media;

public class VideoCreator {

    public Video createVideo() {
        Media defaultMedia = GWT.create(eu.ydp.empiria.player.client.module.object.impl.Video.class);
        return (Video) defaultMedia.getMedia();
    }

}
