package eu.ydp.empiria.player.client.module.dictionary;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.core.base.SimpleModuleBase;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class DictionaryModule extends SimpleModuleBase {

    private final DictionaryPresenter presenter;

    @Inject
    public DictionaryModule(@ModuleScoped DictionaryPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Widget getView() {
        return presenter.getView();
    }

    @Override
    protected void initModule(Element element) {
        presenter.bindUi();
    }
}
