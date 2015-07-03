package eu.ydp.empiria.player.client.module.video.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

import javax.inject.Inject;

public class VideoViewImpl extends Composite implements VideoView {

    private static VideoViewImplUiBinder uiBinder = GWT.create(VideoViewImplUiBinder.class);

    @Inject
    private UserInteractionHandlerFactory userInteractionHandlerFactory;
    @UiField
    FlowPanel container;

    interface VideoViewImplUiBinder extends UiBinder<Widget, VideoViewImpl> {
    }

    @Override
    public void createView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void preparePlayForBookshelf(Command command) {
        userInteractionHandlerFactory.createUserClickHandler(command).apply(container);
    }

    @Override
    public void attachVideoPlayer(VideoPlayer videoPlayer) {
        container.clear();
        container.add(videoPlayer);
    }
}
