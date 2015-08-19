package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScopedProvider;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public abstract class EmpiriaModule extends AbstractGinModule {

    protected <F, T extends F> void bindPageScoped(Class<F> clazz, TypeLiteral<PageScopedProvider<T>> typeLiteral) {
        bind(typeLiteral).in(Singleton.class);
        bind(clazz).annotatedWith(PageScoped.class).toProvider(Key.get(typeLiteral));
    }

    protected <F, T extends F> void bindModuleScoped(Class<F> clazz, TypeLiteral<? extends Provider<T>> typeLiteral) {
        bind(typeLiteral).in(Singleton.class);
        bind(clazz).annotatedWith(ModuleScoped.class).toProvider(Key.get(typeLiteral));
    }

}
