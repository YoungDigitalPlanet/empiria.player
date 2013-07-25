package eu.ydp.empiria.player.client.animation.holder;
import com.google.gwt.event.shared.HandlerRegistration;

import eu.ydp.empiria.player.client.animation.AnimationEndHandler;

public interface AnimationCssHolder {

	void setAnimationStyleName(String styleName);

	void removeAnimationStyleName(String styleName);

	HandlerRegistration addAnimationEndHandler(AnimationEndHandler animationEndHandler);
}
