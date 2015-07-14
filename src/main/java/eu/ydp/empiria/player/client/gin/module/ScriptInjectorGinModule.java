package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.scripts.ScriptsLoader;

public class ScriptInjectorGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(ScriptsLoader.class).in(Singleton.class);
    }
}
