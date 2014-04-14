package eu.ydp.empiria.player.client.module.dictionary;

import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryButtonView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class DictionaryPresenter {

	private final DictionaryButtonView dictionaryButtonView;

	private final DictionaryPopupPresenter dictionaryPopupPresenter;

	@Inject
	public DictionaryPresenter(@ModuleScoped DictionaryButtonView dictionaryButtonView, @ModuleScoped DictionaryPopupPresenter dictionaryPopupPresenter) {
		this.dictionaryButtonView = dictionaryButtonView;
		this.dictionaryPopupPresenter = dictionaryPopupPresenter;
	}

	public void bindUi() {
		dictionaryPopupPresenter.bindUi();
		dictionaryButtonView.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				clicked();
			}
		});
	}

	private void clicked() {
		dictionaryPopupPresenter.show();
	}
}
