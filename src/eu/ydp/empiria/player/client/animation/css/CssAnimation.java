package eu.ydp.empiria.player.client.animation.css;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Strings;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.animation.AnimationConfig;
import eu.ydp.empiria.player.client.animation.AnimationEndHandler;
import eu.ydp.empiria.player.client.animation.AnimationRuntimeConfig;
import eu.ydp.empiria.player.client.animation.AnimationWithRuntimeConfig;
import eu.ydp.empiria.player.client.animation.holder.AnimationHolder;
import eu.ydp.empiria.player.client.util.geom.Size;

public class CssAnimation implements AnimationWithRuntimeConfig {
	private @Inject @Nonnull CssAnimationClassBuilder animationClassBuilder;
	private @Nullable AnimationEndHandler animationEndHandler;
	private @Nullable String currentAnimationClassName;
	private HandlerRegistration endHandlerRegistration;
	private AnimationRuntimeConfig animationRuntimeConfig;

	@Override
	public void start(AnimationEndHandler handler) {
		this.animationEndHandler = handler;
		play();
	}

	private void play() {
		currentAnimationClassName = animationClassBuilder.createAnimationCssClassName(getAnimationConfig(), animationRuntimeConfig.getImageSize());
		Size frameSize = getAnimationConfig().getFrameSize();
		endHandlerRegistration = getAnimationHolder().addAnimationEndHandler(new AnimationEndHandler() {

			@Override
			public void onEnd() {
				animationEnd();
			}
		});
		getAnimationHolder().setAnimationStyleName(currentAnimationClassName, frameSize);
	}

	private AnimationConfig getAnimationConfig() {
		return animationRuntimeConfig.getAnimationConfig();
	}

	private AnimationHolder getAnimationHolder() {
		return animationRuntimeConfig.getAnimationHolder();
	}

	@Override
	public void terminate() {
		removeAnimationStyleName();
		endHandlerRegistration.removeHandler();
	}

	private void removeAnimationStyleName() {
		if (!Strings.isNullOrEmpty(currentAnimationClassName)) {
			getAnimationHolder().removeAnimationStyleName(currentAnimationClassName);
		}
	}

	private void animationEnd() {
		if (endHandlerRegistration != null) {
			endHandlerRegistration.removeHandler();
		}
		removeAnimationStyleName();
		if (animationEndHandler != null) {
			animationEndHandler.onEnd();
		}
	}

	@Override
	public void setRuntimeConfiguration(AnimationRuntimeConfig animationRuntimeConfig) {
		this.animationRuntimeConfig = animationRuntimeConfig;
	}

}
