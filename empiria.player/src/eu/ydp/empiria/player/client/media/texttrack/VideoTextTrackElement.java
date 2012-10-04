package eu.ydp.empiria.player.client.media.texttrack;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.module.media.button.AbstractMediaController;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;

public class VideoTextTrackElement extends AbstractMediaController<VideoTextTrackElement> implements MediaEventHandler {

	private static VideoTextTrackElementUiBinder uiBinder = GWT.create(VideoTextTrackElementUiBinder.class);

	interface VideoTextTrackElementUiBinder extends UiBinder<Widget, VideoTextTrackElement> {
	}

	protected final EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();
	protected StyleNameConstants styleNames = PlayerGinjector.INSTANCE.getStyleNameConstants();
	private final TextTrackKind kind;
	private TextTrackCue textTrackCue;

	@UiField
	protected FlowPanel text;

	public VideoTextTrackElement(TextTrackKind kind) {
		initWidget(uiBinder.createAndBindUi(this));
		this.kind = kind;
		setStyleNames();
	}

	@Override
	public final void setStyleNames() {
		String toAdd = getSuffixToAdd();
		text.setStyleName(styleNames.QP_MEDIA_TEXT_TRACK() + toAdd);
		text.addStyleName(styleNames.QP_MEDIA_TEXT_TRACK() + toAdd + "-" + kind.name().toLowerCase());
	}

	@Override
	public VideoTextTrackElement getNewInstance() {
		return null;
	}

	@Override
	public boolean isSupported() {
		return true;
	}

	@Override
	public void init() {
		eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.TEXT_TRACK_UPDATE), getMediaWrapper(), this, new CurrentPageScope());
		eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_TIME_UPDATE), getMediaWrapper(), this, new CurrentPageScope());
	}

	private void showHideText(TextTrackCue textTrackCue) {
		if (textTrackCue.getEndTime() < getMediaWrapper().getCurrentTime()) {
			text.getElement().setInnerText("");
		} else if (textTrackCue.getStartTime() < getMediaWrapper().getCurrentTime()) {
			text.getElement().setInnerText(textTrackCue.getText());
		}
	}

	@Override
	public void onMediaEvent(MediaEvent event) {
		if (event.getType() == MediaEventTypes.TEXT_TRACK_UPDATE && event.getTextTrackCue() != null) {
			TextTrackCue trackCue = event.getTextTrackCue();
			if (trackCue.getTextTrack().getKind() == kind) {
				textTrackCue = trackCue;
				showHideText(trackCue);
			}
		} else if (event.getType() == MediaEventTypes.ON_TIME_UPDATE && textTrackCue != null) {
			showHideText(textTrackCue);
		}
	}

}
