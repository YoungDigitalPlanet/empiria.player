package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

import eu.ydp.empiria.player.client.animation.css.CssAnimationClassBuilder;
import eu.ydp.empiria.player.client.animation.js.FrameworkAnimation;
import eu.ydp.empiria.player.client.animation.js.FrameworkAnimationNative;
import eu.ydp.empiria.player.client.gin.factory.TutorCommandsModuleFactory;
import eu.ydp.empiria.player.client.module.tutor.ActionExecutorService;
import eu.ydp.empiria.player.client.module.tutor.ActionExecutorServiceImpl;
import eu.ydp.empiria.player.client.module.tutor.TutorCommand;
import eu.ydp.empiria.player.client.module.tutor.commands.AnimationCommand;
import eu.ydp.empiria.player.client.module.tutor.commands.ShowImageCommand;

public class TutorGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(FrameworkAnimation.class).to(FrameworkAnimationNative.class);
		bind(CssAnimationClassBuilder.class).in(Singleton.class);
		bind(ActionExecutorService.class).to(ActionExecutorServiceImpl.class);
		install(new GinFactoryModuleBuilder()
				.implement(TutorCommand.class, Names.named("image"), ShowImageCommand.class)
				.implement(TutorCommand.class, Names.named("animation"), AnimationCommand.class)
				.build(TutorCommandsModuleFactory.class));
	}
}
