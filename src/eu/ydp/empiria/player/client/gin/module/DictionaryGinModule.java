package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;

import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryButtonView;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryButtonViewImpl;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryPopupView;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryPopupViewImpl;

public class DictionaryGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(DictionaryButtonView.class).to(DictionaryButtonViewImpl.class);
		bind(DictionaryPopupView.class).to(DictionaryPopupViewImpl.class);
	}
}
