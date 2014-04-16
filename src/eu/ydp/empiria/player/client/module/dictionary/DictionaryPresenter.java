package eu.ydp.empiria.player.client.module.dictionary;

import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.dictionary.external.controller.MainController;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryButtonView;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryPopupView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class DictionaryPresenter {

	private final DictionaryButtonView dictionaryButtonView;

	private final DictionaryPopupView dictionaryPopupView;

	private final MainController mainController;

	@Inject
	public DictionaryPresenter(@ModuleScoped DictionaryButtonView dictionaryButtonView, @ModuleScoped DictionaryPopupView dictionaryPopupView,
			MainController mainController) {
		this.dictionaryButtonView = dictionaryButtonView;
		this.dictionaryPopupView = dictionaryPopupView;
		this.mainController = mainController;
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
		Panel container = dictionaryPopupView.getContainer();
		mainController.init(container);
		dictionaryPopupView.show();
	}

	public Widget getView() {
		return dictionaryButtonView.asWidget();
	}
}
