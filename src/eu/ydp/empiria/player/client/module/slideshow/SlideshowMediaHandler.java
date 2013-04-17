package eu.ydp.empiria.player.client.module.slideshow;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.slideshow.SlideshowPlayerEvent;
import eu.ydp.empiria.player.client.util.events.slideshow.SlideshowPlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.slideshow.SlideshowPlayerEventType;

public class SlideshowMediaHandler implements SlideshowPlayerEventHandler{

		@Inject
		private EventsBus eventBus;
		private final SlideshowPlayerModule slideshowPlayerModule;
		private final SlideshowPresenter slideshowPresenter;

		@AssistedInject
		public SlideshowMediaHandler(@Assisted SlideshowPlayerModule slideshowPlayerModule, @Assisted SlideshowPresenter slideshowPresenter) {
			this.slideshowPlayerModule = slideshowPlayerModule;
			this.slideshowPresenter = slideshowPresenter;
		}

		@Override
		public void onSlideshowPlayerEvent(SlideshowPlayerEvent event) {
			SlideshowPlayerEventType eventType = event.getType();

			switch (eventType) {
				case ON_NEXT:
					slideshowPlayerModule.showNextSlide();
					break;
				case ON_PREVIOUS:
					slideshowPlayerModule.showPreviousSlide();
					break;
				case ON_PLAY:
					slideshowPlayerModule.onPlayEvent();
					break;
				case ON_STOP:
					slideshowPlayerModule.onStopEvent();
					break;
				default:
					break;
			}
		}

		public void addEventHandler(SlideshowPlayerEventType type){
			eventBus.addHandlerToSource(SlideshowPlayerEvent.getType(type), slideshowPresenter, this);
		}
}
