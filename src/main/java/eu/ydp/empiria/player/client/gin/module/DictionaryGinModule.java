package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.gin.factory.DictionaryModuleFactory;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.*;
import eu.ydp.empiria.player.client.module.dictionary.external.view.ExplanationView;
import eu.ydp.empiria.player.client.module.dictionary.external.view.ExplanationViewImpl;
import eu.ydp.empiria.player.client.module.dictionary.external.view.MainView;
import eu.ydp.empiria.player.client.module.dictionary.external.view.MenuView;
import eu.ydp.empiria.player.client.module.dictionary.external.view.visibility.VisibilityChanger;
import eu.ydp.empiria.player.client.module.dictionary.external.view.visibility.VisibilityChangerProvider;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryButtonView;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryButtonViewImpl;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryPopupView;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryPopupViewImpl;
import eu.ydp.jsfilerequest.client.FileRequestCallback;

public class DictionaryGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(DictionaryButtonView.class).to(DictionaryButtonViewImpl.class);
        bind(DictionaryPopupView.class).to(DictionaryPopupViewImpl.class);

        bind(WordsLoadingListener.class).to(MainController.class);
        bind(ExplanationListener.class).to(MainController.class);
        bind(MainController.class).in(Singleton.class);

        bind(WordsSocket.class).to(WordsController.class);
        bind(WordsController.class).in(Singleton.class);

        bind(EntriesController.class).in(Singleton.class);

        bind(ExplanationView.class).to(ExplanationViewImpl.class);

        bind(MainView.class).in(Singleton.class);
        bind(MenuView.class).in(Singleton.class);
        bind(ExplanationViewImpl.class).in(Singleton.class);

        bind(VisibilityChanger.class).toProvider(VisibilityChangerProvider.class);
        bind(VisibilityChanger.class).in(Singleton.class);

        install(new GinFactoryModuleBuilder().implement(FileRequestCallback.class, DictionaryFileRequestCallback.class).build(DictionaryModuleFactory.class));

    }
}
