package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.gin.factory.MathJaxModuleFactory;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxNative;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxView;
import eu.ydp.empiria.player.client.module.mathjax.inline.view.InlineMathJax;
import eu.ydp.empiria.player.client.module.mathjax.inline.view.InlineMathJaxView;
import eu.ydp.empiria.player.client.module.mathjax.interaction.MathJaxGapContainer;
import eu.ydp.empiria.player.client.module.mathjax.interaction.view.InteractionMathJax;
import eu.ydp.empiria.player.client.module.mathjax.interaction.view.InteractionMathJaxView;

public class MathJaxGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(MathJaxView.class).annotatedWith(InlineMathJax.class).to(InlineMathJaxView.class);
		bind(MathJaxView.class).annotatedWith(InteractionMathJax.class).to(InteractionMathJaxView.class);
		bind(MathJaxNative.class).in(Singleton.class);
		bind(MathJaxGapContainer.class).in(Singleton.class);

		install(new GinFactoryModuleBuilder().build(MathJaxModuleFactory.class));
	}
}
