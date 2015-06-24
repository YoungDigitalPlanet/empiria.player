package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import eu.ydp.empiria.player.client.gin.factory.MathJaxModuleFactory;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxNative;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxView;
import eu.ydp.empiria.player.client.module.mathjax.inline.view.InlineMathJaxView;

public class MathJaxGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(MathJaxView.class).annotatedWith(Names.named("inline")).to(InlineMathJaxView.class);
		bind(MathJaxNative.class).in(Singleton.class);

		install(new GinFactoryModuleBuilder().build(MathJaxModuleFactory.class));
	}
}
