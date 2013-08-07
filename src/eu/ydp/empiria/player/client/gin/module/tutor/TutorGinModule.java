package eu.ydp.empiria.player.client.gin.module.tutor;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

import eu.ydp.empiria.player.client.animation.css.CssAnimationClassBuilder;
import eu.ydp.empiria.player.client.animation.js.FrameworkAnimation;
import eu.ydp.empiria.player.client.animation.js.FrameworkAnimationNative;
import eu.ydp.empiria.player.client.gin.factory.PersonaServiceFactory;
import eu.ydp.empiria.player.client.gin.factory.TutorCommandsModuleFactory;
import eu.ydp.empiria.player.client.module.tutor.ActionExecutorService;
import eu.ydp.empiria.player.client.module.tutor.ActionExecutorServiceImpl;
import eu.ydp.empiria.player.client.module.tutor.TutorCommand;
import eu.ydp.empiria.player.client.module.tutor.actions.popup.TutorPopupPresenter;
import eu.ydp.empiria.player.client.module.tutor.actions.popup.TutorPopupPresenterImpl;
import eu.ydp.empiria.player.client.module.tutor.actions.popup.TutorPopupView;
import eu.ydp.empiria.player.client.module.tutor.actions.popup.TutorPopupViewImpl;
import eu.ydp.empiria.player.client.module.tutor.actions.popup.TutorPopupViewPersonaView;
import eu.ydp.empiria.player.client.module.tutor.actions.popup.TutorPopupViewPersonaViewImpl;
import eu.ydp.empiria.player.client.module.tutor.actions.popup.TutorPopupViewWidget;
import eu.ydp.empiria.player.client.module.tutor.actions.popup.TutorPopupViewWidgetImpl;
import eu.ydp.empiria.player.client.module.tutor.commands.AnimationCommand;
import eu.ydp.empiria.player.client.module.tutor.commands.ShowImageCommand;
import eu.ydp.empiria.player.client.module.tutor.presenter.TutorPresenter;
import eu.ydp.empiria.player.client.module.tutor.presenter.TutorPresenterImpl;
import eu.ydp.empiria.player.client.module.tutor.view.TutorView;
import eu.ydp.empiria.player.client.module.tutor.view.TutorViewImpl;

public class TutorGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(FrameworkAnimation.class).to(FrameworkAnimationNative.class);
		bind(CssAnimationClassBuilder.class).in(Singleton.class);
		bind(TutorView.class).to(TutorViewImpl.class);
		bind(TutorPresenter.class).to(TutorPresenterImpl.class);
		bind(ActionExecutorService.class).to(ActionExecutorServiceImpl.class);
		bind(TutorPopupPresenter.class).to(TutorPopupPresenterImpl.class);
		bind(TutorPopupView.class).to(TutorPopupViewImpl.class);
		bind(TutorPopupViewWidget.class).to(TutorPopupViewWidgetImpl.class);
		bind(TutorPopupViewPersonaView.class).to(TutorPopupViewPersonaViewImpl.class);
		install(new GinFactoryModuleBuilder()
				.implement(TutorCommand.class, Names.named("image"), ShowImageCommand.class)
				.implement(TutorCommand.class, Names.named("animation"), AnimationCommand.class)
				.build(TutorCommandsModuleFactory.class));
		
		install(new GinFactoryModuleBuilder()
				.build(PersonaServiceFactory.class));
	}
}
