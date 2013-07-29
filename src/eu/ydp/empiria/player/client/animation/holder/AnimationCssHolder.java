package eu.ydp.empiria.player.client.animation.holder;
import com.google.gwt.event.shared.HandlerRegistration;

import eu.ydp.empiria.player.client.animation.AnimationEndHandler;
import eu.ydp.empiria.player.client.util.geom.Size;

public interface AnimationCssHolder {

	void setAnimationStyleName(String styleName, Size size);

	void removeAnimationStyleName(String styleName);

	HandlerRegistration addAnimationEndHandler(AnimationEndHandler animationEndHandler);
}
