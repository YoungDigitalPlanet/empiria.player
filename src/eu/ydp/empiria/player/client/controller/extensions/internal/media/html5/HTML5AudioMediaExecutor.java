package eu.ydp.empiria.player.client.controller.extensions.internal.media.html5;

import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;

import eu.ydp.gwtutil.client.debug.gwtlogger.Logger;
import eu.ydp.gwtutil.client.proxy.RootPanelDelegate;
import eu.ydp.gwtutil.client.util.UserAgentUtil;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;

public class HTML5AudioMediaExecutor extends AbstractHTML5MediaExecutor<Audio> implements TouchStartHandler{
	
	private static final Logger LOGGER = new Logger();
	private HandlerRegistration touchHandlerRegistration;
	private final RootPanelDelegate rootPanelDelegate;
	
	@Inject
	public HTML5AudioMediaExecutor(UserAgentUtil userAgentUtil, RootPanelDelegate rootPanelDelegate) {
		this.rootPanelDelegate = rootPanelDelegate;
		
		if(isPlayOnTouchHackNeeded(userAgentUtil)){
			addPlayOnTouchHandler();
		}
	}

	private boolean isPlayOnTouchHackNeeded(UserAgentUtil userAgentUtil) {
		return userAgentUtil.isInsideIframe() && isMobileSafari(userAgentUtil);
	}

	private boolean isMobileSafari(UserAgentUtil userAgentUtil) {
		return userAgentUtil.isMobileUserAgent(MobileUserAgent.SAFARI) || userAgentUtil.isMobileUserAgent(MobileUserAgent.SAFARI_WEBVIEW);
	}

	@Override
	public void initExecutor() {
		//
	}

	protected void addPlayOnTouchHandler() {
		RootPanel root = rootPanelDelegate.getRootPanel();
		touchHandlerRegistration = root.addDomHandler(this, TouchStartEvent.getType());
	}
	
	@Override
	public void onTouchStart(TouchStartEvent event) {
		touchHandlerRegistration.removeHandler();
		super.play();
	}
}
