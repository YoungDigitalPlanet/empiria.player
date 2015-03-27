package eu.ydp.empiria.player.client.module.media.html5;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.AbstractHTML5MediaExecutor;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

/**
 * Safari for iOS has a known problem with the poster attribute on the video tags. Potentially this solution can be used also for other browsers, on which the
 * similar problem will be appearing.
 */
public class HTML5VideoForcePosterHack implements MediaEventHandler {

	private Element posterImageElement;
	private boolean isPosterCreated = false;
	private final MediaBase mediaBase;
	private final HTML5MediaExecutorDelegator mediaExecutorDelegator;

	public HTML5VideoForcePosterHack(MediaBase mediaBase, HTML5MediaExecutorDelegator mediaExecutor) {
		this.mediaBase = mediaBase;
		this.mediaExecutorDelegator = mediaExecutor;
	}

	@Override
	public void onMediaEvent(MediaEvent event) {
		MediaEventTypes eventType = event.getType();
		if (MediaEventTypes.ON_PLAY.equals(eventType)) {
			hidePosterImage();
		} else if (MediaEventTypes.SUSPEND.equals(eventType) || MediaEventTypes.CAN_PLAY.equals(eventType)) {
			createPosterImageOnParentElementOnce();
		}
	}

	private void createPosterImageOnParentElementOnce() {
		if (isPosterCreated) {
			return;
		}

		Widget parent = mediaBase.getParent();
		Element parentElement = parent.getElement();

		if (parentElement != null) {
			AbstractHTML5MediaExecutor executor = mediaExecutorDelegator.getExecutor();
			BaseMediaConfiguration baseMediaConfiguration = executor.getBaseMediaConfiguration();
			createPosterImageLayer(parentElement, baseMediaConfiguration);
			isPosterCreated = true;
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
		posterImageElement.getStyle().setPadding(0, Unit.PX);
		posterImageElement.getStyle().setMargin(0, Unit.PX);
		posterImageElement.getStyle().setVisibility(Visibility.VISIBLE);

		mediaContainerElement.appendChild(posterImageElement);
	}
}
