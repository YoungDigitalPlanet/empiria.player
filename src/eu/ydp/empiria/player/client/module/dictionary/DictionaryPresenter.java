package eu.ydp.empiria.player.client.module.dictionary;

import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryButtonView;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryPopupView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class DictionaryPresenter {

	private final DictionaryButtonView dictionaryButtonView;

	private final DictionaryPopupView dictionaryPopupView;

	@Inject
	public DictionaryPresenter(@ModuleScoped DictionaryButtonView dictionaryButtonView, @ModuleScoped DictionaryPopupView dictionaryPopupView) {
		this.dictionaryButtonView = dictionaryButtonView;
		this.dictionaryPopupView = dictionaryPopupView;
	}

	public void bindUi() {
		dictionaryButtonView.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				showPopup();
			}
		});
		dictionaryPopupView.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hidePopup();
			}
		});
	}

	private void hidePopup() {
		dictionaryPopupView.hide();
	}

	private void showPopup() {
		dictionaryPopupView.show();
	}

	public Widget getView() {
		return dictionaryButtonView.asWidget();
	}
}
