package eu.ydp.empiria.player.client.rebind;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules(TestGinModule.class)
public interface TestGinjector extends Ginjector {
    SimpleInject getSimpleInject();

    ProviderInject getProviderInject();

    SimpleFactory getSimpleFactory();

    InterfaceFactory getInterfaceFactory();
}
