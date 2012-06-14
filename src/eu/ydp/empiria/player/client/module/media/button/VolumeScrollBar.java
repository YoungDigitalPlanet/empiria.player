package eu.ydp.empiria.player.client.module.media.button;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.event.html5.HTML5MediaEvent;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventHandler;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventsType;
import static eu.ydp.empiria.player.client.util.UserAgentChecker.MobileUserAgent.*;

public class VolumeScrollBar extends AbstractMediaScroll<VolumeScrollBar> {

	private static VolumeScrollBarUiBinder uiBinder = GWT.create(VolumeScrollBarUiBinder.class);

	interface VolumeScrollBarUiBinder extends UiBinder<Widget, VolumeScrollBar> {
	}

	@UiField(provided = true)
	SimpleMediaButton button = new SimpleMediaButton("qp-media-volume-scrollbar-button", false);
	@UiField
	FlowPanel progressBar;

	@UiField
	FlowPanel mainProgressDiv;

	@UiField
	FlowPanel beforeButton;

	@UiField
	FlowPanel afterButton;
	HandlerRegistration durationchangeHandlerRegistration;

	public VolumeScrollBar() {
		super(FIREFOX, CHROME);
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public VolumeScrollBar getNewInstance() {
		return new VolumeScrollBar();
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
			getMedia().addBitlessDomHandler(new HTML5MediaEventHandler() {
				@Override
				public void onEvent(HTML5MediaEvent event) {
					if (getMedia().isMuted()) {
						moveScroll(getScrollLength());
					}
				}
			}, HTML5MediaEvent.getType(HTML5MediaEventsType.volumechange));

			durationchangeHandlerRegistration = getMedia().addBitlessDomHandler(new HTML5MediaEventHandler() {
				@Override
				public void onEvent(HTML5MediaEvent event) {
					double volume = getMedia().getVolume();
					moveScroll((int) (getScrollLength() * volume));
					durationchangeHandlerRegistration.removeHandler();
				}
			}, HTML5MediaEvent.getType(HTML5MediaEventsType.durationchange));
		} else {
			progressBar.setStyleName(progressBar.getStyleName() + unsupportedSuffx);
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
			Touch t = touches.get(0);
			positionY = t.getRelativeY(mainProgressDiv.getElement());
		} else {
			Element target = mainProgressDiv.getElement();
			positionY = event.getClientY() - target.getAbsoluteTop() + target.getScrollTop() + target.getOwnerDocument().getScrollTop();
		}
		return positionY;
	}

	protected void setVolume(double value) {
		getMedia().setVolume(value);
	}

	@Override
	protected void setPosition(NativeEvent event) {
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
	protected void moveScroll(int positionY) {
		int scrollSize = getScrollLength();
		positionY = positionY > scrollSize ? scrollSize : positionY;
		button.getElement().getStyle().setTop(positionY, Unit.PX);
		beforeButton.getElement().getStyle().setHeight(positionY, Unit.PX);
		afterButton.getElement().getStyle().setTop(positionY + getButtonLength(), Unit.PX);
		afterButton.getElement().getStyle().setHeight(getScrollLength() - positionY, Unit.PX);
	}
}
