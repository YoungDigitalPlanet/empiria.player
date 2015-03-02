package eu.ydp.empiria.player.client.rebind;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;

public class TestGinModule extends AbstractGinModule {
	@Override
	protected void configure() {
		bind(SimpleInject.class);
		bind(InjectObj.class);
		install(new GinFactoryModuleBuilder().build(SimpleFactory.class));
		install(new GinFactoryModuleBuilder().implement(InjectInterface.class, InjectObj.class).build(InterfaceFactory.class));
	}
}
