package eu.ydp.empiria.player.client.module.dictionary;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryButtonView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class DictionaryModule extends SimpleModuleBase {

	private final DictionaryPresenter presenter;

	private final DictionaryButtonView dictionaryButtonView;

	@Inject
	public DictionaryModule(@ModuleScoped DictionaryPresenter presenter, @ModuleScoped DictionaryButtonView dictionaryButtonView) {
		this.presenter = presenter;
		this.dictionaryButtonView = dictionaryButtonView;
	}

	@Override
	public Widget getView() {
		return dictionaryButtonView.asWidget();
	}

	@Override
	protected void initModule(Element element) {
		presenter.bind();
	}
}
