package eu.ydp.empiria.player.client.module.dictionary;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryPopupView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import eu.ydp.gwtutil.client.proxy.RootPanelDelegate;

public class DictionaryPopupPresenter {

	private final DictionaryPopupView dictionaryPopupView;

	private final RootPanelDelegate rootPanelDelegate;

	@Inject
	public DictionaryPopupPresenter(@ModuleScoped DictionaryPopupView dictionaryPopupView, RootPanelDelegate rootPanelDelegate) {
		this.dictionaryPopupView = dictionaryPopupView;
		this.rootPanelDelegate = rootPanelDelegate;
	}

	public void bindUi() {
		dictionaryPopupView.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
	}

	public void show() {
		RootPanel rootPanel = rootPanelDelegate.getRootPanel();
		rootPanel.add(dictionaryPopupView);
	}

	public void hide() {
		RootPanel rootPanel = rootPanelDelegate.getRootPanel();
		rootPanel.remove(dictionaryPopupView);
	}
}
