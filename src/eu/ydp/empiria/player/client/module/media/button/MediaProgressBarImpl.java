package eu.ydp.empiria.player.client.module.media.button;

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

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.AbstractMediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

public class MediaProgressBarImpl extends AbstractMediaScroll<MediaProgressBarImpl> implements MediaProgressBar {

	interface MediaProgressBarUiBinder extends UiBinder<Widget, MediaProgressBarImpl> {
	}

	private static MediaProgressBarUiBinder uiBinder = GWT.create(MediaProgressBarUiBinder.class);

	@UiField(provided = true)
	protected SimpleMediaButton button = new SimpleMediaButton(styleNames.QP_MEDIA_CENTER_PROGRESS_BUTTON(), false);

	@UiField
	protected FlowPanel progressBar;

	@UiField
	protected FlowPanel mainProgressDiv;

	@UiField
	protected FlowPanel beforeButton;

	@UiField
	protected FlowPanel afterButton;

	protected EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();

	public MediaProgressBarImpl() {
		super();
		initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * wielkosc przycisku wyswietlanego na pasku postepu
	 *
	 * @return
	 */
	protected int getButtonWidth() {
		return button.getElement().getAbsoluteRight() - button.getElement().getAbsoluteLeft();
	}

	@Override
	public MediaProgressBarImpl getNewInstance() {
		return new MediaProgressBarImpl();
	}

	@Override
	public boolean isSupported() {
		return getMediaAvailableOptions().isSeekSupported();
	}

	/**
	 * dlugosc paska postepu
	 *
	 * @return
	 */
	protected int getScrollWidth() {
		return (mainProgressDiv.getElement().getAbsoluteRight() - mainProgressDiv.getElement().getAbsoluteLeft()) - getButtonWidth();
	}

	@Override
	public void init() {
		super.init();
		if (isSupported()) {
			AbstractMediaEventHandler handler = new AbstractMediaEventHandler() {
				//-1 aby przy pierwszym zdarzeniu pokazal sie timer
				int lastTime = -1;

				@Override
				public void onMediaEvent(MediaEvent event) {
					if (isMediaReady() && !isPressed()) {
						if (getMediaWrapper().getCurrentTime() > lastTime + 1 || getMediaWrapper().getCurrentTime() < lastTime - 1  ||  event.getType() == MediaEventTypes.ON_STOP) {//NOPMD
							// przeskakujemy co sekunde
							lastTime = (int) getMediaWrapper().getCurrentTime();
							double steep = getScrollWidth() / getMediaWrapper().getDuration();
							moveScroll((int) (steep * getMediaWrapper().getCurrentTime()));
						}
					}
				}
			};
			eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_TIME_UPDATE), getMediaWrapper(), handler);
			eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_DURATION_CHANGE), getMediaWrapper(), handler);
			eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_STOP), getMediaWrapper(), handler);
			// nie zawsze zostanie wyzwolony timeupdate ze wzgledu na
			// ograniczenie
			// na 1s postepu wiec robimy to tu
			handler = new AbstractMediaEventHandler() {
				@Override
				public void onMediaEvent(MediaEvent event) {
					double steep = getScrollWidth() / getMediaWrapper().getDuration();
					moveScroll((int) (steep * getMediaWrapper().getCurrentTime()));
					eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.PAUSE, getMediaWrapper()),getMediaWrapper());
				}
			};
			eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_END), getMediaWrapper(), handler);

		} else {
			progressBar.setStyleName(progressBar.getStyleName() + UNSUPPORTED_SUFFIX);
			progressBar.clear();
		}
	}

	/**
	 * ustawia suwak na odpowiedniej pozycji
	 *
	 * @param positionX
	 */
	protected void moveScroll(int positionX) {//NOPMD
		int scrollSize = getScrollWidth();
		positionX = positionX > scrollSize ? scrollSize : positionX;
		button.getElement().getStyle().setLeft(positionX, Unit.PX);
		beforeButton.getElement().getStyle().setWidth(positionX, Unit.PX);
		afterButton.getElement().getStyle().setLeft(positionX + getButtonWidth(), Unit.PX);
		afterButton.getElement().getStyle().setWidth(getScrollWidth() - positionX, Unit.PX);
	}

	/**
	 * @param positionX
	 */
	protected void seekInMedia(int positionX) {
		if (isAttached()) {
			int scrollSize = getScrollWidth();
			double steep = getMediaWrapper().getDuration() / scrollSize;
			double time = steep * positionX;
			double position = time > getMediaWrapper().getDuration() ? getMediaWrapper().getDuration() : time;
			//TODO dodac schedulera dla zdarzen aby ograniczyc ilosc wykonywanych
			eventsBus.fireAsyncEventFromSource(new MediaEvent(MediaEventTypes.SET_CURRENT_TIME, getMediaWrapper(), position),getMediaWrapper());
		}
	}

	protected int getPositionX(NativeEvent event) {
		JsArray<Touch> touches = event.getChangedTouches();
		int positionX = 0;
		if (touches != null && touches.length() == 1) {
			Touch touch = touches.get(0);
			positionX = touch.getRelativeX(mainProgressDiv.getElement());
		} else {
			Element target = mainProgressDiv.getElement();
			positionX = event.getClientX() - target.getAbsoluteLeft() + target.getScrollLeft() + target.getOwnerDocument().getScrollLeft();
		}
		return positionX;
	}

	@Override
	protected void setPosition(NativeEvent event) {
		if (isPressed()) {
			int positionX = getPositionX(event);
			seekInMedia(positionX > 0 ? positionX : 0);
			positionX -= (getButtonWidth() / 2);
			moveScroll(positionX > 0 ? positionX : 0);
		}
	}
}
