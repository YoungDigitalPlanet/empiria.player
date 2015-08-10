package eu.ydp.empiria.player.client.module.media.fullscreen;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;

public class VideoFullScreenViewImpl extends Composite implements VideoFullScreenView {

    private final EventsBus eventsBus;

    private static VideoFullScreenViewUiBinder uiBinder = GWT.create(VideoFullScreenViewUiBinder.class);

    interface VideoFullScreenViewUiBinder extends UiBinder<Widget, VideoFullScreenViewImpl> {
    }

    @UiField
    protected FlowPanel container;

    @UiField
    protected FlowPanel controls;

    @Inject
    public VideoFullScreenViewImpl(EventsBus eventsBus) {
        this.eventsBus = eventsBus;
        initWidget(uiBinder.createAndBindUi(this));
        container.addDomHandler(new TouchStartHandler() {

            @Override
            public void onTouchStart(TouchStartEvent event) {
                VideoFullScreenViewImpl.this.eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.TOUCH_EVENT_RESERVATION));
            }
        }, TouchStartEvent.getType());
    }

    /*
     * (non-Javadoc)
     *
     * @see eu.ydp.empiria.player.client.module.media.fullscreen.VideoFullScreenView#getContainer()
     */
    @Override
    public FlowPanel getContainer() {
        return container;
    }

    /*
     * (non-Javadoc)
     *
     * @see eu.ydp.empiria.player.client.module.media.fullscreen.VideoFullScreenView#getControls()
     */
    @Override
    public FlowPanel getControls() {
        return controls;
    }

}
