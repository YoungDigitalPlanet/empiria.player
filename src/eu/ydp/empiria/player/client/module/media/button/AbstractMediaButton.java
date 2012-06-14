package eu.ydp.empiria.player.client.module.media.button;

import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlowPanel;

import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.object.impl.VideoImpl;
import eu.ydp.empiria.player.client.util.UserAgentChecker.MobileUserAgent;

/**
 * bazowy przycisk dla kontrolerow multimediow
 *
 * @param <T>
 *            typ przycisku dla {@link Factory}
 */
public abstract class AbstractMediaButton<T> extends MediaController<T>  {
	public enum MediaType {
		AUDIO, VIDEO
	}


	private String baseStyleName;
	private String onClickStyleName;
	private String hoverStyleName;
	protected boolean clicked = false;
	private FlowPanel divElement = new FlowPanel();
	private MediaBase media;
	protected MediaType mediaType = MediaType.AUDIO;
	private boolean singleClick = true;

	/**
	 * bazowy przycisk dla kontrolerow multimediow
	 *
	 * @param baseStyleName
	 * @param singleClick
	 *            czy element jest zwyklym przyciskiem i mousup jest ignorowany wartosc true<br/>
	 *            false wywoluje ponownie akcje na mouseup
	 */
	public AbstractMediaButton(String baseStyleName, boolean singleClick, MobileUserAgent... supportedUserAgents) {
		this.baseStyleName = baseStyleName;
		this.onClickStyleName = baseStyleName + clickSuffix;
		this.hoverStyleName = baseStyleName + hoverSuffx;
		this.singleClick = singleClick;
		setSupportedMobileAgents(supportedUserAgents);
		initWidget(divElement);
		if(isSupported()){
			sinkEvents(Event.MOUSEEVENTS | Event.TOUCHEVENTS);
			this.setStyleName(this.baseStyleName);
		}else{
			this.setStyleName(this.baseStyleName+unsupportedSuffx);
		}
	}

	public AbstractMediaButton(String baseStyleName, MobileUserAgent... supportedUserAgents) {
		this(baseStyleName, true,supportedUserAgents);
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
		}
		super.onBrowserEvent(event);
	}

	/**
	 * zdarzenie click
	 */
	protected void onClick() {
		if (hoverStyleName.trim().length() == 0) {
			return;
		}
		changeStyleForClick();
		clicked = !clicked;
	}

	/**
	 * zmiana stylu elementu dla zdarzenia click
	 */
	protected void changeStyleForClick() {
		if (!clicked) {
			divElement.getElement().addClassName(onClickStyleName);
		} else {
			divElement.getElement().removeClassName(onClickStyleName);
		}

	}

	/**
	 * zmiana stylu elementu dla zdarzenia onMouseOver
	 */
	protected void onMouseOver() {
		if (hoverStyleName.trim().length() > 0) {
			divElement.getElement().addClassName(hoverStyleName);
		}
	}

	/**
	 * zmiana stylu elementu dla zdarzenia onMouseOut
	 */
	void onMouseOut() {
		if (hoverStyleName.trim().length() > 0) {
			divElement.getElement().removeClassName(hoverStyleName);
		}
	}

	public void setMedia(MediaBase media) {
		if (media instanceof VideoImpl) {
			mediaType = MediaType.VIDEO;
		}
		this.media = media;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public MediaBase getMedia() {
		return media;
	}

}
