package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.TypeLiteral;
import eu.ydp.empiria.player.client.gin.factory.DictionaryModuleFactory;
import eu.ydp.empiria.player.client.module.dictionary.DictionaryPresenter;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.*;
import eu.ydp.empiria.player.client.module.dictionary.external.view.ExplanationView;
import eu.ydp.empiria.player.client.module.dictionary.external.view.ExplanationViewImpl;
import eu.ydp.empiria.player.client.module.dictionary.external.view.visibility.VisibilityChanger;
import eu.ydp.empiria.player.client.module.dictionary.external.view.visibility.VisibilityChangerProvider;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryButtonView;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryButtonViewImpl;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryPopupView;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryPopupViewImpl;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopedProvider;
import eu.ydp.jsfilerequest.client.FileRequestCallback;

public class DictionaryGinModule extends EmpiriaModule {

    @Override
    protected void configure() {
        bind(DictionaryButtonView.class).to(DictionaryButtonViewImpl.class);
        bind(DictionaryPopupView.class).to(DictionaryPopupViewImpl.class);

        bind(WordsLoadingListener.class).to(MainController.class);
        bind(ExplanationListener.class).to(MainController.class);

        bind(WordsSocket.class).to(WordsController.class);

        bind(ExplanationView.class).to(ExplanationViewImpl.class);

        bind(VisibilityChanger.class).toProvider(VisibilityChangerProvider.class);

        bindModuleScoped(DictionaryPresenter.class, new TypeLiteral<ModuleScopedProvider<DictionaryPresenter>>() {
        });
        bindModuleScoped(DictionaryButtonView.class, new TypeLiteral<ModuleScopedProvider<DictionaryButtonView>>() {
        });
        bindModuleScoped(DictionaryPopupView.class, new TypeLiteral<ModuleScopedProvider<DictionaryPopupView>>() {
        });

        install(new GinFactoryModuleBuilder().implement(FileRequestCallback.class, DictionaryFileRequestCallback.class).build(DictionaryModuleFactory.class));

    }
}
