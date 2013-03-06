package eu.ydp.empiria.player.client.module.media.html5;

import javax.annotation.PostConstruct;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.AbstractHTML5MediaExecutor;
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

	AttachHandlerFactory attachHandlerFactory;

	private final HTML5MediaExecutorDelegator html5MediaExecutorDelegator = new HTML5MediaExecutorDelegator();

	@Inject
	private UserAgentUtil userAgentUtil;

	@Inject
	public HTML5VideoMediaWrapper(@Assisted Media media, AttachHandlerFactory attachHandlerFactory, EventsBus eventsBus, PageScopeFactory pageScopeFactory) {
		super(media, eventsBus, pageScopeFactory);
		this.attachHandlerFactory = attachHandlerFactory;
	}

	@PostConstruct
	public void registerEvents() {
		attachHandlerImpl = attachHandlerFactory.createAttachHandler(mediaExecutor, this);
		if (isHTML5VideoForcePosterNeeded()) {
			HTML5VideoForcePosterHack html5VideoForcePosterHack = new HTML5VideoForcePosterHack(mediaBase, html5MediaExecutorDelegator);
			handlerRegistrations.put(MediaEventTypes.SUSPEND,
					eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.SUSPEND), this, html5VideoForcePosterHack, new CurrentPageScope()));
			handlerRegistrations.put(MediaEventTypes.ON_PLAY,
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
			attachHandlerImpl.setMediaExecutor(mediaExecutor);
		}
		html5MediaExecutorDelegator.setExecutor(mediaExecutor);
	}

}
