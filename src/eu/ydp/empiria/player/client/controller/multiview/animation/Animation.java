package eu.ydp.empiria.player.client.controller.multiview.animation;

import com.google.gwt.user.client.ui.FlowPanel;

public interface Animation {

	public void goTo(FlowPanel toAnimate, int xPosition, double duration);
	public void addAnimationEndCallback(AnimationEndCallback endCallback);
	public void removeAnimationEndCallback(AnimationEndCallback endCallback);
	public double getPositionX();
	public boolean isRunning();

}
