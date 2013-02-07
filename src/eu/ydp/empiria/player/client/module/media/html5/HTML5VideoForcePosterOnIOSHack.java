package eu.ydp.empiria.player.client.module.media.html5;

import static eu.ydp.gwtutil.client.util.UserAgentChecker.isMobileUserAgent;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.web.bindery.event.shared.HandlerRegistration;

import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.object.impl.Video;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;

/**
 * Safari for iOS has a known problem with the poster attribute on the video tags.
 */
public class HTML5VideoForcePosterOnIOSHack implements MediaEventHandler {
	
	private Element posterImageElement;
	protected final EventsBus eventsBus = PlayerGinjectorFactory.getPlayerGinjector().getEventsBus();
	private HandlerRegistration handlerRegistration = null;
	private boolean isPosterCreated = false;
	private HTML5MediaWrapper html5MediaWrapper;
		
	public void init(HTML5MediaWrapper html5MediaWrapper) {
		this.html5MediaWrapper = html5MediaWrapper;
		if (isHackApplyConditionMeet()) {
			handlerRegistration = eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PLAY), html5MediaWrapper, this, new CurrentPageScope());
		}
	}

	public void applayHackIfRequired(Element mediaContainerElement, BaseMediaConfiguration baseMediaConfiguration) {
		if (!isPosterCreated && isHackApplyConditionMeet()) {
			createPosterImageLayer(mediaContainerElement, baseMediaConfiguration);
			isPosterCreated = true;
		}
	}	

	public void clean() {
		if (handlerRegistration != null) {
			handlerRegistration.removeHandler();
		}
	}		
	
	@Override
	public void onMediaEvent(MediaEvent event) {
		if (MediaEventTypes.ON_PLAY.equals(event.getType())) {
			hidePosterImage();
		}
	}

	private void hidePosterImage() {
		posterImageElement.getStyle().setVisibility(Visibility.HIDDEN);
	}
	
	private void createPosterImageLayer(Element mediaContainerElement, BaseMediaConfiguration baseMediaConfiguration) {
		posterImageElement = DOM.createImg();
		DOM.setImgSrc(posterImageElement, baseMediaConfiguration.getPoster());
		posterImageElement.getStyle().setLeft(0, Unit.PX);
		posterImageElement.getStyle().setTop(0, Unit.PX);
		posterImageElement.getStyle().setPosition(Position.ABSOLUTE);
		posterImageElement.getStyle().setWidth(baseMediaConfiguration.getWidth(), Unit.PX);
		posterImageElement.getStyle().setHeight(baseMediaConfiguration.getHeight(), Unit.PX);		
		posterImageElement.getStyle().setZIndex(9999);
		posterImageElement.getStyle().setPadding(0, Unit.PX);
		posterImageElement.getStyle().setMargin(0, Unit.PX);
		posterImageElement.getStyle().setVisibility(Visibility.VISIBLE);
		
		mediaContainerElement.appendChild(posterImageElement);		
	}
	
	public boolean isHackApplyConditionMeet() {
		return (isMobileUserAgent(MobileUserAgent.SAFARI) || isMobileUserAgent(MobileUserAgent.SAFARI_WEBVIEW))
				&& html5MediaWrapper.getMedia() instanceof Video;
	}
}
