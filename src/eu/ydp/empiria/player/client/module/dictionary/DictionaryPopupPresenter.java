package eu.ydp.empiria.player.client.module.dictionary;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryPopupView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class DictionaryPopupPresenter {

	private final DictionaryPopupView dictionaryPopupView;

	@Inject
	public DictionaryPopupPresenter(@ModuleScoped DictionaryPopupView dictionaryPopupView) {
		this.dictionaryPopupView = dictionaryPopupView;
	}

	public void bindUi() {
		dictionaryPopupView.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
	}

	private void hide() {
		dictionaryPopupView.hide();
	}

	public void show() {
		dictionaryPopupView.show();
	}
}
