package eu.ydp.empiria.player.client.module.media.button;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.AbstractMediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;

public abstract class AbstractPlayMediaButton<T> extends AbstractMediaButton<T> {

	@Inject
	protected EventsBus eventsBus;

	@Inject
	private Provider<T> provider;

	public AbstractPlayMediaButton(String baseStyleName) {
		super(baseStyleName);
	}

	protected abstract MediaEvent createMediaEvent();

	protected abstract boolean initButtonStyleChangeHandlersCondition();

	@Override
	public void init() {
		super.init();
		if (initButtonStyleChangeHandlersCondition()) {
			initButtonStyleChangeHandlers();
		}
	}

	@Override
	public boolean isSupported() {
		return getMediaAvailableOptions().isPlaySupported();
	}

	@Override
	public T getNewInstance() {
		return provider.get();
	}

	protected void initButtonStyleChangeHandlers() {
		AbstractMediaEventHandler handler = createButtonActivationHandler();
		CurrentPageScope scope = createCurrentPageScope();
		addMediaEventHandlers(handler, scope);
	}

	protected CurrentPageScope createCurrentPageScope() {
		return new CurrentPageScope();
	}

	@Override
	protected void onClick() {
		MediaEvent mediaEvent = createMediaEvent();
		eventsBus.fireEventFromSource(mediaEvent, getMediaWrapper());
	}

	private void addMediaEventHandlers(AbstractMediaEventHandler handler, CurrentPageScope scope) {
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PAUSE), getMediaWrapper(), handler, scope);
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_END), getMediaWrapper(), handler, scope);
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_STOP), getMediaWrapper(), handler, scope);
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_PLAY), getMediaWrapper(), handler, scope);
	}

	private AbstractMediaEventHandler createButtonActivationHandler() {
		return new AbstractMediaEventHandler() {
			@Override
			public void onMediaEvent(MediaEvent event) {
				if (event.getType() == MediaEventTypes.ON_PLAY) {
					setActive(true);
				} else {
					setActive(false);
				}
				changeStyleForClick();
			}
		};
	}
}
