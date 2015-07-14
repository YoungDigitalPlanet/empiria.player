package eu.ydp.empiria.player.client.module.speechscore.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.speechscore.view.SpeechScoreLinkView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class SpeechScorePresenter {

    @Inject
    @ModuleScoped
    private SpeechScoreLinkView view;

    private static final String URL_ATTR_NAME = "url";

    public Widget getView() {
        return view.asWidget();
    }

    public void init(Element element) {
        String linkText = element.getFirstChild().getNodeValue();
        String href = "http://" + element.getAttribute(URL_ATTR_NAME);
        view.buildLink(linkText, href);
    }
}
