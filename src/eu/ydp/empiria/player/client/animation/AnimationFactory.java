package eu.ydp.empiria.player.client.animation;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.animation.holder.AnimationHolder;
import eu.ydp.empiria.player.client.animation.js.JsAnimation;

public class AnimationFactory {

	@Inject Provider<JsAnimation> jsAnimationProvider;
	
	public Animation getAnimation(AnimationConfig config, AnimationHolder holder){
		JsAnimation jsAnimation = jsAnimationProvider.get();
		jsAnimation.init(config, holder);
		return jsAnimation;
	}
}
