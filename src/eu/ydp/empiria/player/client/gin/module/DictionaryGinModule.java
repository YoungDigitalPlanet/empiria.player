package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

import eu.ydp.empiria.player.client.module.dictionary.external.controller.EntriesController;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.ExplanationListener;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.MainController;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.PasswordsController;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.PasswordsLoadingListener;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.PasswordsSocket;
import eu.ydp.empiria.player.client.module.dictionary.external.view.ExplanationView;
import eu.ydp.empiria.player.client.module.dictionary.external.view.MainView;
import eu.ydp.empiria.player.client.module.dictionary.external.view.MenuView;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryButtonView;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryButtonViewImpl;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryPopupView;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryPopupViewImpl;

public class DictionaryGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(DictionaryButtonView.class).to(DictionaryButtonViewImpl.class);
		bind(DictionaryPopupView.class).to(DictionaryPopupViewImpl.class);

		bind(PasswordsLoadingListener.class).to(MainController.class);
		bind(ExplanationListener.class).to(MainController.class);
		bind(MainController.class).in(Singleton.class);

		bind(PasswordsSocket.class).to(PasswordsController.class);
		bind(PasswordsController.class).in(Singleton.class);

		bind(EntriesController.class).in(Singleton.class);

		bind(MainView.class).in(Singleton.class);
		bind(MenuView.class).in(Singleton.class);
		bind(ExplanationView.class).in(Singleton.class);
	}
}
