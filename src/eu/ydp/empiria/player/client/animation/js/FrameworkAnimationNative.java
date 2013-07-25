package eu.ydp.empiria.player.client.animation.js;

import com.google.gwt.animation.client.Animation;

public class FrameworkAnimationNative extends Animation implements FrameworkAnimation {
	
	private FrameworkAnimationListener listener;
	
	@Override
	public void setListener(FrameworkAnimationListener listener) {
		this.listener = listener;
	}

	@Override
	protected void onUpdate(double progress) {
		if (listener != null){
			listener.onUpdate(progress);
		}
	}
	
	@Override
	protected double interpolate(double progress) {
		return progress;
	}

}
