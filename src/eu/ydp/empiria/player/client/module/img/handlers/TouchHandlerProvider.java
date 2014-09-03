package eu.ydp.empiria.player.client.module.img.handlers;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates.EventsCoordinates;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.ITouchHandlerInitializer;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

public class TouchHandlerProvider {

	@Inject
	private UserAgentUtil userAgentUtil;

	@Inject
	private PointerHandlersInitializer pointerHandlersInitializer;

	@Inject
	private TouchHandlersInitializer touchHandlersInitializer;

	public ITouchHandlerInitializer<EventsCoordinates> getTouchHandlersInitializer() {
		return userAgentUtil.isIE() ? pointerHandlersInitializer : touchHandlersInitializer;
	}
}
