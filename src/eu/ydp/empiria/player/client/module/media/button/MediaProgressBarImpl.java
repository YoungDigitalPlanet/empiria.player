package eu.ydp.empiria.player.client.module.media.button;

import static eu.ydp.empiria.player.client.util.events.media.MediaEventTypes.ON_DURATION_CHANGE;
import static eu.ydp.empiria.player.client.util.events.media.MediaEventTypes.ON_END;
import static eu.ydp.empiria.player.client.util.events.media.MediaEventTypes.ON_FULL_SCREEN_SHOW_CONTROLS;
import static eu.ydp.empiria.player.client.util.events.media.MediaEventTypes.ON_STOP;
import static eu.ydp.empiria.player.client.util.events.media.MediaEventTypes.ON_TIME_UPDATE;
import static eu.ydp.empiria.player.client.util.events.media.MediaEventTypes.PAUSE;
import static eu.ydp.empiria.player.client.util.events.media.MediaEventTypes.SET_CURRENT_TIME;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.AbstractMediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;

public class MediaProgressBarImpl extends AbstractMediaScroll<MediaProgressBarImpl> implements MediaProgressBar {

	interface MediaProgressBarUiBinder extends UiBinder<Widget, MediaProgressBarImpl> {
	}

	private static MediaProgressBarUiBinder uiBinder = GWT.create(MediaProgressBarUiBinder.class);
	protected final static StyleNameConstants styleNames = PlayerGinjectorFactory.getPlayerGinjector().getStyleNameConstants(); // NOPMD

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

	protected EventsBus eventsBus = PlayerGinjectorFactory.getPlayerGinjector().getEventsBus();

	// -1 aby przy pierwszym zdarzeniu pokazal sie timer
	private int lastTime = -1;

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
		int buttonWidth = 0;
		if (button != null) {
			buttonWidth = button.getElement().getAbsoluteRight() - button.getElement().getAbsoluteLeft();
		}
		return buttonWidth;
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
				Set<MediaEventTypes> fastUpdateEvents = new HashSet<MediaEventTypes>(Arrays.asList(new MediaEventTypes[]{ON_FULL_SCREEN_SHOW_CONTROLS,ON_STOP}));

				@Override
				public void onMediaEvent(MediaEvent event) {
					if (isMediaReady() && !isPressed()) {
						if (getMediaWrapper().getCurrentTime() > lastTime + 1 || getMediaWrapper().getCurrentTime() < lastTime - 1 || fastUpdateEvents.contains(event.getType())) {// NOPMD
							// przeskakujemy co sekunde
							lastTime = (int) getMediaWrapper().getCurrentTime();
							double steep = getScrollWidth() / getMediaWrapper().getDuration();
							moveScroll((int) (steep * getMediaWrapper().getCurrentTime()));
						}
					}
				}
			};
			CurrentPageScope scope = new CurrentPageScope();
			eventsBus.addAsyncHandlerToSource(MediaEvent.getType(ON_TIME_UPDATE), getMediaWrapper(), handler, scope);
			eventsBus.addAsyncHandlerToSource(MediaEvent.getType(ON_DURATION_CHANGE), getMediaWrapper(), handler, scope);
			eventsBus.addAsyncHandlerToSource(MediaEvent.getType(ON_STOP), getMediaWrapper(), handler, scope);
			eventsBus.addAsyncHandlerToSource(MediaEvent.getType(ON_FULL_SCREEN_SHOW_CONTROLS), getMediaWrapper(), handler, scope);

			// nie zawsze zostanie wyzwolony timeupdate ze wzgledu na
			// ograniczenie
			// na 1s postepu wiec robimy to tu
			handler = new AbstractMediaEventHandler() {
				@Override
				public void onMediaEvent(MediaEvent event) {
					double steep = getScrollWidth() / getMediaWrapper().getDuration();
					moveScroll((int) (steep * getMediaWrapper().getCurrentTime()));
					eventsBus.fireEventFromSource(new MediaEvent(PAUSE, getMediaWrapper()), getMediaWrapper());
				}
			};
			eventsBus.addHandlerToSource(MediaEvent.getType(ON_END), getMediaWrapper(), handler, new CurrentPageScope());

		} else {
			progressBar.setStyleName(styleNames.QP_MEDIA_PROGRESSBAR() + UNSUPPORTED_SUFFIX);
			progressBar.clear();
		}
	}

	/**
	 * ustawia suwak na odpowiedniej pozycji
	 *
	 * @param positionX
	 */
	protected void moveScroll(int positionX) {// NOPMD
		moveScroll(positionX, false);
	}

	/**
	 * ustawia suwak na odpowiedniej pozycji
	 *
	 * @param positionX
	 */
	protected void moveScroll(int positionX, boolean force) {// NOPMD
		if (!isPressed() || force) {
			int scrollSize = getScrollWidth();
			positionX = positionX > scrollSize ? scrollSize : positionX;
			button.getElement().getStyle().setLeft(positionX, Unit.PX);
			beforeButton.getElement().getStyle().setWidth(positionX, Unit.PX);
			afterButton.getElement().getStyle().setLeft(positionX + getButtonWidth(), Unit.PX);
			afterButton.getElement().getStyle().setWidth(getScrollWidth() - positionX, Unit.PX);
		}
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
			MediaEvent event = new MediaEvent(SET_CURRENT_TIME, getMediaWrapper());
			event.setCurrentTime(position);
			eventsBus.fireAsyncEventFromSource(event, getMediaWrapper());
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
		if (isPressed() && isAttached()) {
			int positionX = getPositionX(event);
			int positionNonNegative = Math.max(positionX, 0);
			seekInMedia(positionNonNegative);
			positionX -= (getButtonWidth() / 2);
			lastTime = -1;
			moveScroll(positionNonNegative, true);
		}
	}

	@Override
	public void setStyleNames() {
		if (isInFullScreen()) {
			progressBar.removeStyleName(styleNames.QP_MEDIA_PROGRESSBAR());
			progressBar.addStyleName(styleNames.QP_MEDIA_PROGRESSBAR() + FULL_SCREEN_SUFFIX);
		}
	}
}
