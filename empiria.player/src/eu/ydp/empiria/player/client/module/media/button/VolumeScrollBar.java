package eu.ydp.empiria.player.client.module.media.button;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.AbstractMediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;

public class VolumeScrollBar extends AbstractMediaScroll<VolumeScrollBar> {

	private static VolumeScrollBarUiBinder uiBinder = GWT.create(VolumeScrollBarUiBinder.class);

	interface VolumeScrollBarUiBinder extends UiBinder<Widget, VolumeScrollBar> {
	}

	@UiField(provided = true)
	protected SimpleMediaButton button = new SimpleMediaButton(styleNames.QP_MEDIA_VOLUME_SCROLLBAR_BUTTON(), false);
	@UiField
	protected FlowPanel progressBar;

	@UiField
	protected FlowPanel mainProgressDiv;

	@UiField
	protected FlowPanel beforeButton;

	@UiField
	protected FlowPanel afterButton;

	protected HandlerRegistration durationchangeHandlerRegistration; //NOPMD
	protected EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();

	public VolumeScrollBar() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public VolumeScrollBar getNewInstance() {
		return new VolumeScrollBar();
	}

	@Override
	public boolean isSupported() {
		return getMediaAvailableOptions().isVolumeChangeSupported();
	}

	/**
	 * wielkosc przycisku wyswietlanego na pasku postepu
	 *
	 * @return
	 */
	protected int getButtonLength() {
		return button.getElement().getAbsoluteBottom() - button.getElement().getAbsoluteTop();
	}

	/**
	 * dlugosc paska postepu
	 *
	 * @return
	 */
	protected int getScrollLength() {
		return (mainProgressDiv.getElement().getAbsoluteBottom() - mainProgressDiv.getElement().getAbsoluteTop()) - getButtonLength();
	}

	@Override
	public void init() {
		super.init();
		if (isSupported()) {
			AbstractMediaEventHandler handler = new AbstractMediaEventHandler() {
				@Override
				public void onMediaEvent(MediaEvent event) {
					if (getMediaWrapper().isMuted()) {
						moveScroll(getScrollLength());
					}
				}
			};
			eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_VOLUME_CHANGE), getMediaWrapper(), handler,new CurrentPageScope());
			handler = new AbstractMediaEventHandler() {
				@Override
				public void onMediaEvent(MediaEvent event) {
					double volume = getMediaWrapper().getVolume();
					moveScroll((int) (getScrollLength() * volume));
					durationchangeHandlerRegistration.removeHandler();
				}
			};
			durationchangeHandlerRegistration = eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_DURATION_CHANGE), getMediaWrapper(), handler);

		} else {
			progressBar.setStyleName(progressBar.getStyleName() + UNSUPPORTED_SUFFIX);
			Iterator<Widget> iter = progressBar.iterator();
			while (iter.hasNext()) {
				iter.next().removeFromParent();
			}
		}
	}

	protected int getPositionY(NativeEvent event) {
		JsArray<Touch> touches = event.getChangedTouches();
		int positionY = 0;
		if (touches != null && touches.length() == 1) {
			Touch touch = touches.get(0);
			positionY = touch.getRelativeY(mainProgressDiv.getElement());
		} else {
			Element target = mainProgressDiv.getElement();
			positionY = event.getClientY() - target.getAbsoluteTop() + target.getScrollTop() + target.getOwnerDocument().getScrollTop();
		}
		return positionY;
	}

	protected void setVolume(double value) {
		MediaEvent event = new MediaEvent(MediaEventTypes.CHANGE_VOLUME, getMediaWrapper());
		event.setVolume(value);
		eventsBus.fireAsyncEventFromSource(event, getMediaWrapper());
	}

	@Override
	protected void setPosition(NativeEvent event) {// NOPMD
		if (isPressed() && ((Element) event.getEventTarget().cast()).getClassName().contains("qp-media-volume-scrollbar-center")) {
			int positionY = getPositionY(event);
			positionY = positionY > 0 ? positionY : 0;
			double volume = (1f / getScrollLength()) * (getScrollLength() - positionY);
			setVolume(volume > 1 ? 1 : volume < 0 ? 0 : volume);
			positionY = positionY - getButtonLength() / 2;
			moveScroll(positionY > 0 ? positionY : 0);
		}
	}

	/**
	 * ustawia suwak na odpowiedniej pozycji
	 *
	 * @param positionY
	 */
	protected void moveScroll(int positionY) {// NOPMD
		int scrollSize = getScrollLength();
		positionY = positionY > scrollSize ? scrollSize : positionY;
		button.getElement().getStyle().setTop(positionY, Unit.PX);
		beforeButton.getElement().getStyle().setHeight(positionY, Unit.PX);
		afterButton.getElement().getStyle().setTop(positionY + getButtonLength(), Unit.PX);
		afterButton.getElement().getStyle().setHeight(getScrollLength() - positionY, Unit.PX);
	}
}
