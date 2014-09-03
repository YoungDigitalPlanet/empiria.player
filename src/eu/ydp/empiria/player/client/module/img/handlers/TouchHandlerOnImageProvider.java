package eu.ydp.empiria.player.client.module.img.handlers;

import com.google.inject.Inject;

import eu.ydp.gwtutil.client.util.UserAgentUtil;

public class TouchHandlerOnImageProvider {

	@Inject
	private UserAgentUtil userAgentUtil;

	@Inject
	private PointerHandlersOnImageInitializer pointerHandlersInitializer;

	@Inject
	private TouchHandlersOnImageInitializer touchHandlersInitializer;

	public ITouchHandlerOnImageInitializer getTouchHandlersInitializer() {
		return userAgentUtil.isIE() ? pointerHandlersInitializer : touchHandlersInitializer;
	}
}
