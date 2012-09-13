package eu.ydp.empiria.player.client.module.media.button;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlowPanel;

import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.gwtutil.client.debug.logger.Debug;

/**
 * bazowy przycisk dla kontrolerow multimediow
 *
 * @param <T>
 *            typ przycisku dla {@link Factory}
 */
public abstract class AbstractMediaButton<T> extends MediaController<T> {
	private String baseStyleName;
	private String onClickStyleName;
	private String hoverStyleName;
	private boolean active = false;
	private final FlowPanel divElement = new FlowPanel();
	private boolean singleClick = true;

	/**
	 * bazowy przycisk dla kontrolerow multimediow
	 *
	 * @param baseStyleName
	 * @param singleClick
	 *            czy element jest zwyklym przyciskiem i mousup jest ignorowany
	 *            wartosc true<br/>
	 *            false wywoluje ponownie akcje na mouseup
	 */
	public AbstractMediaButton(String baseStyleName, boolean singleClick) {
		this.baseStyleName = baseStyleName;
		this.onClickStyleName = baseStyleName + CLICK_SUFFIX;
		this.hoverStyleName = baseStyleName + HOVER_SUFFIX;
		this.singleClick = singleClick;
		initWidget(divElement);

	}

	@Override
	public void init() {
		if (isSupported()) {
			sinkEvents(Event.MOUSEEVENTS | Event.TOUCHEVENTS);
			this.setStyleName(this.baseStyleName);
		} else {
			this.setStyleName(this.baseStyleName + UNSUPPORTED_SUFFIX);
		}

	}

	public AbstractMediaButton(String baseStyleName) {
		this(baseStyleName, true);
	}

	@Override
	public void onBrowserEvent(Event event) {
		event.preventDefault();
		switch (event.getTypeInt()) {
		case Event.ONMOUSEDOWN:
		case Event.ONTOUCHSTART:
			onClick();
			break;
		case Event.ONMOUSEUP:
			if (!singleClick) {
				onClick();
			}
			break;
		case Event.ONMOUSEOVER:
			onMouseOver();
			break;
		case Event.ONTOUCHEND:
		case Event.ONMOUSEOUT:
			onMouseOut();
			break;
		default:
			break;
		}
	}

	/**
	 * zdarzenie click
	 */
	protected abstract void onClick();

	protected boolean isActive() {
		return active;
	}

	protected void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * zmiana stylu elementu dla zdarzenia click
	 */
	protected void changeStyleForClick() {
		if (active) {
			divElement.getElement().addClassName(onClickStyleName);
		} else {
			divElement.getElement().removeClassName(onClickStyleName);
		}

	}

	/**
	 * zmiana stylu elementu dla zdarzenia onMouseOver
	 */
	protected void onMouseOver() {
		if (hoverStyleName.trim().isEmpty()) {
			divElement.getElement().addClassName(hoverStyleName);
		}
	}

	/**
	 * zmiana stylu elementu dla zdarzenia onMouseOut
	 */
	protected void onMouseOut() {
		if (hoverStyleName.trim().isEmpty()) {
			divElement.getElement().removeClassName(hoverStyleName);
		}
	}
}
