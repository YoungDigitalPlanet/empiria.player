package eu.ydp.empiria.player.client.module.media.html5;

import static eu.ydp.gwtutil.client.util.UserAgentChecker.isMobileUserAgent;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.HTML5MediaExecutor;
import eu.ydp.empiria.player.client.module.object.impl.Media;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;

public class HTML5VideoMediaWrapper extends HTML5MediaWrapper {

	private final HTML5MediaExecutorDelegator html5MediaExecutorDelegator = new HTML5MediaExecutorDelegator();
	
	public HTML5VideoMediaWrapper(Media media) {
		super(media);
		attachHandlerImpl = new AttachHandlerImpl();
		attachHandlerImpl.setMediaBase(mediaBase);
		attachHandlerImpl.setMediaExecutor(mediaExecutor);
		attachHandlerImpl.setMediaWrapper(this);
		mediaBase.addAttachHandler(attachHandlerImpl);		
		
		registerEvents();
	}

	private void registerEvents() {		
		if (isHTML5VideoForcePosterNeeded()) {
			HTML5VideoForcePosterHack html5VideoForcePosterHackForIOS = new HTML5VideoForcePosterHack(mediaBase, html5MediaExecutorDelegator);
			handlerRegistrations.put(MediaEventTypes.SUSPEND, eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.SUSPEND), this, html5VideoForcePosterHackForIOS, new CurrentPageScope()));
			handlerRegistrations.put(MediaEventTypes.ON_PLAY, eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PLAY), this, html5VideoForcePosterHackForIOS, new CurrentPageScope()));
		}
	}
	
	private boolean isHTML5VideoForcePosterNeeded() {		
		return (isMobileUserAgent(MobileUserAgent.SAFARI) || isMobileUserAgent(MobileUserAgent.SAFARI_WEBVIEW));
	}
	
	@Override
	public void setMediaExecutor(HTML5MediaExecutor mediaExecutor) {
		super.setMediaExecutor(mediaExecutor);
		html5MediaExecutorDelegator.setExecutor(mediaExecutor);
	}
	
}
