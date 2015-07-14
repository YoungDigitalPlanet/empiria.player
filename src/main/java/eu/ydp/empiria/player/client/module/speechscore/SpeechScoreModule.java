package eu.ydp.empiria.player.client.module.speechscore;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.speechscore.presenter.SpeechScorePresenter;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class SpeechScoreModule extends SimpleModuleBase {

    @Inject
    @ModuleScoped
    private SpeechScorePresenter presenter;

    @Override
    public Widget getView() {
        return presenter.getView();
    }

    @Override
    protected void initModule(Element element) {
        presenter.init(element);
    }
}
