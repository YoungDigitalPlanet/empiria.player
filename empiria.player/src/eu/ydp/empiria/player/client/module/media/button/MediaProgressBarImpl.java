package eu.ydp.empiria.player.client.module.media.button;

import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.event.html5.HTML5MediaEvent;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventHandler;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventsType;

public class MediaProgressBarImpl extends AbstractMediaScroll<MediaProgressBarImpl> implements MediaProgressBar {

	interface MediaProgressBarUiBinder extends UiBinder<Widget, MediaProgressBarImpl> {
	}

	private static MediaProgressBarUiBinder uiBinder = GWT.create(MediaProgressBarUiBinder.class);

	@UiField(provided = true)
	SimpleMediaButton button = new SimpleMediaButton("qp-media-center-progress-button", false);

	@UiField
	FlowPanel progressBar;

	@UiField
	FlowPanel mainProgressDiv;

	@UiField
	FlowPanel beforeButton;

	@UiField
	FlowPanel afterButton;

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
			final ScheduledCommand command = new ScheduledCommand() {
				int lastTime = 0;

				@Override
				public void execute() {
					if (isMediaReady() && !isPressed()) {
						if (getMedia().getCurrentTime() > lastTime + 1 || getMedia().getCurrentTime() < lastTime - 1) {
							// przeskakujemy co sekunde
							lastTime = (int) getMedia().getCurrentTime();
							double steep = getScrollWidth() / getMedia().getDuration();
							moveScroll((int) (steep * getMedia().getCurrentTime()));
						}
					}
				}
			};
			getMedia().addBitlessDomHandler(new HTML5MediaEventHandler() {
				@Override
				public void onEvent(HTML5MediaEvent t) {
					Scheduler.get().scheduleDeferred(command);
				}
			}, HTML5MediaEvent.getType(HTML5MediaEventsType.timeupdate));

			// nie zawsze zostanie wyzwolony timeupdate ze wzgledu na
			// ograniczenie
			// na 1s postepu wiec robimy to tu
			getMedia().addBitlessDomHandler(new HTML5MediaEventHandler() {
				@Override
				public void onEvent(HTML5MediaEvent event) {
					double steep = getScrollWidth() / getMedia().getDuration();
					moveScroll((int) (steep * getMedia().getCurrentTime()));
					getMedia().pause();
				}
			}, HTML5MediaEvent.getType(HTML5MediaEventsType.ended));
		} else {
			progressBar.setStyleName(progressBar.getStyleName() + unsupportedSuffx);
			Iterator<Widget> iter = progressBar.iterator();
			while (iter.hasNext()) {
				iter.next().removeFromParent();
			}
		}
	}

	/**
	 * ustawia suwak na odpowiedniej pozycji
	 *
	 * @param positionX
	 */
	protected void moveScroll(int positionX) {
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
			double steep = getMedia().getDuration() / scrollSize;
			double time = steep * positionX;
			getMedia().setCurrentTime(time > getMedia().getDuration() ? getMedia().getDuration() : time);
		}
	}

	protected int getPositionX(NativeEvent event) {
		JsArray<Touch> touches = event.getChangedTouches();
		int positionX = 0;
		if (touches != null && touches.length() == 1) {
			Touch t = touches.get(0);
			positionX = t.getRelativeX(mainProgressDiv.getElement());
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
