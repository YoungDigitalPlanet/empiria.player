package eu.ydp.empiria.player.client.module.img.events.handlers;

import com.google.inject.Inject;

import eu.ydp.gwtutil.client.util.UserAgentUtil;

public class TouchHandlerOnImageProvider {

	@Inject
	private UserAgentUtil userAgentUtil;

	@Inject
	private PointerHandlersOnImageInitializer pointerHandlersOnImageInitializer;

	@Inject
	private TouchHandlersOnImageInitializer touchHandlersOnImageInitializer;

	public ITouchHandlerOnImageInitializer getTouchHandlersOnImageInitializer() {
		return userAgentUtil.isIE() ? pointerHandlersOnImageInitializer : touchHandlersOnImageInitializer;
	}
}
