package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

import eu.ydp.empiria.player.client.animation.css.CssAnimationClassBuilder;
import eu.ydp.empiria.player.client.animation.js.FrameworkAnimation;
import eu.ydp.empiria.player.client.animation.js.FrameworkAnimationNative;
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
	}
}
