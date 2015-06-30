package eu.ydp.empiria.player.client.module.video.view;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.gwtutil.client.event.factory.Command;

public interface VideoView extends IsWidget {

    void createView();

    void attachVideoPlayer(VideoPlayer videoPlayer);

    void preparePlayForBookshelf(Command command);
}
