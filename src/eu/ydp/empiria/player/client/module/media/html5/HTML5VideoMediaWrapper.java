package eu.ydp.empiria.player.client.module.media.html5;

import javax.annotation.PostConstruct;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.AbstractHTML5MediaExecutor;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.HTML5VideoMediaExecutor;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.object.impl.Media;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

public class HTML5VideoMediaWrapper extends AbstractHTML5MediaWrapper {
	protected AttachHandlerImpl attachHandlerImpl;

	private final AttachHandlerFactory attachHandlerFactory;

	private final HTML5MediaExecutorDelegator html5MediaExecutorDelegator = new HTML5MediaExecutorDelegator();

	@Inject
	private UserAgentUtil userAgentUtil;

	private final EventsBus eventsBus;

	@Inject
	public HTML5VideoMediaWrapper(@Assisted Media media, AttachHandlerFactory attachHandlerFactory, EventsBus eventsBus, PageScopeFactory pageScopeFactory) {
		super(media, eventsBus, pageScopeFactory);
		this.eventsBus = eventsBus;
		this.attachHandlerFactory = attachHandlerFactory;
	}

	@PostConstruct
	public void registerEvents() {
		attachHandlerImpl = attachHandlerFactory.createAttachHandler((HTML5VideoMediaExecutor)getMediaExecutor(), this);
		if (isHTML5VideoForcePosterNeeded()) {
			HTML5VideoForcePosterHack html5VideoForcePosterHack = new HTML5VideoForcePosterHack(getMediaBase(), html5MediaExecutorDelegator);
			addHandlerRegistration(MediaEventTypes.SUSPEND,
					eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.SUSPEND), this, html5VideoForcePosterHack, new CurrentPageScope()));
			addHandlerRegistration(MediaEventTypes.ON_PLAY,
					eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PLAY), this, html5VideoForcePosterHack, new CurrentPageScope()));
		}
	}

	private boolean isHTML5VideoForcePosterNeeded() {
		return (userAgentUtil.isMobileUserAgent(MobileUserAgent.SAFARI) || userAgentUtil.isMobileUserAgent(MobileUserAgent.SAFARI_WEBVIEW));
	}

	@Override
	public void setMediaExecutor(AbstractHTML5MediaExecutor mediaExecutor) {
		super.setMediaExecutor(mediaExecutor);
		if (attachHandlerImpl != null) {
			attachHandlerImpl.setMediaExecutor((HTML5VideoMediaExecutor)mediaExecutor);
		}
		html5MediaExecutorDelegator.setExecutor(mediaExecutor);
	}

}
