package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

import eu.ydp.empiria.player.client.animation.css.CssAnimationClassBuilder;
import eu.ydp.empiria.player.client.animation.js.FrameworkAnimation;
import eu.ydp.empiria.player.client.animation.js.FrameworkAnimationNative;

public class TutorGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(FrameworkAnimation.class).to(FrameworkAnimationNative.class);
		bind(CssAnimationClassBuilder.class).in(Singleton.class);
	}
}
