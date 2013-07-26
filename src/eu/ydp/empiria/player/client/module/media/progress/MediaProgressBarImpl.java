package eu.ydp.empiria.player.client.module.media.progress;

import static eu.ydp.empiria.player.client.util.events.media.MediaEventTypes.ON_DURATION_CHANGE;
import static eu.ydp.empiria.player.client.util.events.media.MediaEventTypes.ON_END;
import static eu.ydp.empiria.player.client.util.events.media.MediaEventTypes.ON_FULL_SCREEN_SHOW_CONTROLS;
import static eu.ydp.empiria.player.client.util.events.media.MediaEventTypes.ON_STOP;
import static eu.ydp.empiria.player.client.util.events.media.MediaEventTypes.ON_TIME_UPDATE;
import static eu.ydp.empiria.player.client.util.events.media.MediaEventTypes.SET_CURRENT_TIME;

import javax.annotation.PostConstruct;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.media.button.AbstractMediaScroll;
import eu.ydp.empiria.player.client.module.media.button.SimpleMediaButton;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.style.ComputedStyle;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.position.PositionHelper;

public class MediaProgressBarImpl extends AbstractMediaScroll<MediaProgressBarImpl> implements MediaProgressBar {

	interface MediaProgressBarUiBinder extends UiBinder<Widget, MediaProgressBarImpl> {}

	private static MediaProgressBarUiBinder uiBinder = GWT.create(MediaProgressBarUiBinder.class);
	@UiField(provided = true) protected SimpleMediaButton button;
	@UiField protected FlowPanel progressBar;
	@UiField protected FlowPanel mainProgressDiv;
	@UiField protected FlowPanel beforeButton;
	@UiField protected FlowPanel afterButton;

	@Inject private StyleNameConstants styleNames;
	@Inject protected EventsBus eventsBus;
	@Inject private ElementSizeCalculator elementSizeCalculator;
	@Inject private PositionHelper positionHelper;
	@Inject private ComputedStyle computedStyle;
	private ProgressBarUpdateEventHandler progressBarEventHandler;
	private MediaProgressBarPositionCalculator progressBarPositionCalculator;

	@PostConstruct
	public void postConstruct() {
		button = new SimpleMediaButton(styleNames.QP_MEDIA_CENTER_PROGRESS_BUTTON(), false);
		progressBarPositionCalculator = new MediaProgressBarPositionCalculator(this, computedStyle);
		initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * wielkosc przycisku wyswietlanego na pasku postepu
	 *
	 * @return
	 */
	@Override
	public int getButtonWidth() {
		int buttonWidth = 0;
		if (button != null) {
			buttonWidth = elementSizeCalculator.getWidth(button);
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
	@Override
	public int getScrollWidth() {
		return elementSizeCalculator.getWidth(mainProgressDiv) - getButtonWidth();
	}

	@Override
	public void init() {
		super.init();
		if (isSupported()) {
			progressBarEventHandler = new ProgressBarUpdateEventHandler(this);
			CurrentPageScope scope = new CurrentPageScope();
			eventsBus.addAsyncHandlerToSource(MediaEvent.getType(ON_TIME_UPDATE), getMediaWrapper(), progressBarEventHandler, scope);
			eventsBus.addAsyncHandlerToSource(MediaEvent.getType(ON_DURATION_CHANGE), getMediaWrapper(), progressBarEventHandler, scope);
			eventsBus.addAsyncHandlerToSource(MediaEvent.getType(ON_STOP), getMediaWrapper(), progressBarEventHandler, scope);
			eventsBus.addAsyncHandlerToSource(MediaEvent.getType(ON_FULL_SCREEN_SHOW_CONTROLS), getMediaWrapper(), progressBarEventHandler, scope);

			// nie zawsze zostanie wyzwolony timeupdate ze wzgledu na
			// ograniczenie
			// na 1s postepu wiec robimy to tu
			ProgressBarEndEventHandler handlerForEnd = new ProgressBarEndEventHandler(this, eventsBus);
			eventsBus.addHandlerToSource(MediaEvent.getType(ON_END), getMediaWrapper(), handlerForEnd, new CurrentPageScope());

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
	@Override
	public void moveScroll(int positionX) {// NOPMD
		moveScroll(positionX, false);
	}

	/**
	 * ustawia suwak na odpowiedniej pozycji
	 *
	 * @param positionX
	 */
	protected void moveScroll(final int positionX, boolean force) {// NOPMD
		if (!isPressed() || force) {
			setButtonPosition(positionX);
			setBeforeButtonPositionAndStyle(positionX);
			setAfterButtonPositionAndStyle(positionX);
		}
	}

	private void setAfterButtonPositionAndStyle(final int leftOffsetForProgressButton) {
		Style afterButtonStyle = afterButton.getElement().getStyle();
		int leftPositionForAfterProgress = progressBarPositionCalculator.getLeftPositionForAfterProgressElement(leftOffsetForProgressButton);
		afterButtonStyle.setLeft(leftPositionForAfterProgress, Unit.PX);
		afterButtonStyle.setWidth(progressBarPositionCalculator.getWidthForAfterProgressElement(leftOffsetForProgressButton), Unit.PX);
	}

	private void setBeforeButtonPositionAndStyle(final int leftOffsetForProgressButton) {
		Style beforeButtonStyle = beforeButton.getElement().getStyle();
		beforeButtonStyle.setWidth(leftOffsetForProgressButton + getHalfOfProgressButtonWidth(), Unit.PX);
		beforeButtonStyle.setLeft( progressBarPositionCalculator.getLeftPositionForBeforeProgressElement(leftOffsetForProgressButton), Unit.PX);
	}

	private void setButtonPosition(final int leftOffsetForProgressButton) {
		int leftOffsetForProgress = progressBarPositionCalculator.calculateCurrentPosistionForScroll(leftOffsetForProgressButton);
		button.getElement().getStyle().setLeft(leftOffsetForProgress, Unit.PX);
	}

	private int getHalfOfProgressButtonWidth() {
		return getButtonWidth() / 2;
	}

	/**
	 * @param positionX
	 */
	protected void seekInMedia(int positionX) {
		if (isAttached()) {
			double position = progressBarPositionCalculator.calculateCurrentPosistion(positionX);
			fireSetCurrentTimeEvent(position);
		}
	}

	private void fireSetCurrentTimeEvent(double position) {
		MediaEvent event = new MediaEvent(SET_CURRENT_TIME, getMediaWrapper());
		event.setCurrentTime(position);
		eventsBus.fireAsyncEventFromSource(event, getMediaWrapper());
	}

	protected int getPositionX(NativeEvent event) {
		return positionHelper.getPositionX(event, mainProgressDiv.getElement());
	}

	@Override
	protected void setPosition(NativeEvent event) {
		if (isPressed() && isAttached()) {
			int positionX = getPositionX(event);
			int positionNonNegative = Math.max(positionX, 0);
			positionNonNegative -= getHalfOfProgressButtonWidth();
			seekInMedia(positionNonNegative);
			progressBarEventHandler.resetCurrentTime();
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
