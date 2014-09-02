package eu.ydp.empiria.player.client.util.events.dom.emulate.handlers;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.gwtutil.client.util.UserAgentUtil;

public class TouchHandlerProvider {

	@Inject
	private UserAgentUtil userAgentUtil;

	@Inject
	private Provider<PointerHandlersInitializer> pointerHandlersInitializerProvider;

	@Inject
	private Provider<TouchHandlersInitializer> touchHandlersInitializerProvider;

	public ITouchHandlerInitializer getTouchHandlersInitializer() {
		return userAgentUtil.isIE() ? pointerHandlersInitializerProvider.get() : touchHandlersInitializerProvider.get();
	}
}
