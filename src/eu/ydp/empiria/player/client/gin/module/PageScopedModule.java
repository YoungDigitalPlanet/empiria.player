package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;

public class PageScopedModule extends AbstractGinModule{

	@Override
	protected void configure() {
		
		//TODO: dodac tutaj moduly które musz¹ byæ w page scope
		
//		bind(DefaultVariableProcessorPageScopedProvider.class).in(Singleton.class);
//		
//		bind(DefaultVariableProcessor.class)
//			.annotatedWith(PageScoped.class)
//			.toProvider(DefaultVariableProcessorPageScopedProvider.class)
//			.in(Singleton.class);
//		
		
	}

}
