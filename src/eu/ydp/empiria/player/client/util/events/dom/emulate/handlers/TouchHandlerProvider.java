package eu.ydp.empiria.player.client.util.events.dom.emulate.handlers;

import com.google.inject.Inject;

import eu.ydp.gwtutil.client.util.UserAgentUtil;

public class TouchHandlerProvider {

	@Inject
	private UserAgentUtil userAgentUtil;

	@Inject
	private PointerHandlersInitializer pointerHandlersInitializer;

	@Inject
	private TouchHandlersInitializer touchHandlersInitializer;

	public ITouchHandlerInitializer getTouchHandlersInitializer() {
		return userAgentUtil.isIE() ? pointerHandlersInitializer : touchHandlersInitializer;
	}
}
