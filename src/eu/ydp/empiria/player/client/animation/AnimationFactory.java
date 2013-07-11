package eu.ydp.empiria.player.client.animation;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.animation.css.CssAnimation;
import eu.ydp.empiria.player.client.animation.holder.AnimationHolder;
import eu.ydp.empiria.player.client.animation.js.JsAnimation;

public class AnimationFactory {

	@Inject private Provider<JsAnimation> jsAnimationProvider;
	@Inject private Provider<CssAnimation> cssAnimationProvider;
	@Inject private CssAnimationSupportAnalizer cssAnimationSupportAnalizer;
	@Inject private Provider<GenericAnimation> baseAnimationProvider;

	public Animation getAnimation(AnimationConfig config, AnimationHolder holder){
		AnimationWithRuntimeConfig animation = getAnimationInstance();
		GenericAnimation baseAnimation = baseAnimationProvider.get();
		baseAnimation.init(animation, config, holder);
		return baseAnimation;
	}

	private AnimationWithRuntimeConfig getAnimationInstance() {
		if(cssAnimationSupportAnalizer.isCssAnimationSupported()){
			return cssAnimationProvider.get();
		}
		return jsAnimationProvider.get();
	}
}
