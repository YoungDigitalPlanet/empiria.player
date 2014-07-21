package eu.ydp.empiria.player.client.controller.multiview;

import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.multiview.animation.Animation;
import eu.ydp.empiria.player.client.controller.multiview.animation.AnimationEndCallback;
import eu.ydp.empiria.player.client.inject.Instance;
import eu.ydp.empiria.player.client.module.button.NavigationButtonDirection;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.proxy.WindowDelegate;
import eu.ydp.gwtutil.client.scheduler.Scheduler;

public class PageSwitchAnimation {

	@Inject
	private WindowDelegate windowDelegate;
	@Inject
	private Instance<Animation> animation;
	@Inject
	private EventsBus eventsBus;
	@Inject
	private Scheduler scheduler;

	private AnimationEndCallback animationCallback = null;

	public void animatePageSwitch(
			final MultiPageController multiPageController,
			final float from, final float to,
			final NavigationButtonDirection direction,
			final int duration, final boolean onlyPositionReset) {
		if (Math.abs(from - to) > 1) {
			if (!onlyPositionReset) {
				windowDelegate.scrollTo(0, 0);
			}
			animation.get().removeAnimationEndCallback(animationCallback);
			animationCallback = new AnimationEndCallback() {
				@Override
				public void onComplate(int position) {
					scheduler.scheduleDeferred(new ScheduledCommand() {
						@Override
						public void execute() {
							multiPageController.resetFocusAndStyles();
							if (direction != null) {
								multiPageController.invokeNavigationRequest(direction);
							}
							if (!onlyPositionReset) {
								eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.PAGE_VIEW_LOADED));
							}
							multiPageController.setCurrentPosition(to);
						}
					});
				}
			};
			animation.get().addAnimationEndCallback(animationCallback);
			FlowPanel target = multiPageController.getMainPanel();
			int width = multiPageController.getWidth();
			int xPosition = Math.round((to / width)) * width;
			animation.get().goTo(target, xPosition, duration);
		} else if (!onlyPositionReset) {
			eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.PAGE_VIEW_LOADED));
		}
	}

	public boolean isAnimationRunning() {
		return animation.get().isRunning();
	}
}
