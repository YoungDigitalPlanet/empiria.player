package eu.ydp.empiria.player.client.event.animation;

public class SafariEventTypes implements EventTypes {
	@Override
	public String getAnimationEnd() {
		return "webkitAnimationEnd";
	}

	@Override
	public String getTransistionEnd() {
		return "webkitTransitionEnd";
	}
}
