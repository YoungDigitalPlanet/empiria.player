package eu.ydp.empiria.player.client.module.mathjax.inline;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.factory.MathJaxModuleFactory;
import eu.ydp.empiria.player.client.module.InlineModuleBase;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxPresenter;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxView;
import eu.ydp.empiria.player.client.module.mathjax.inline.view.InlineMathJax;

public class InlineMathJaxModule extends InlineModuleBase {

    private MathJaxPresenter presenter;

    @Inject
    public InlineMathJaxModule(MathJaxModuleFactory factory, @InlineMathJax MathJaxView view) {
        this.presenter = factory.getMathJaxPresenter(view);
    }

    @Override
    public Widget getView() {
        return presenter.getView();
    }

    @Override
    protected void initModule(Element element) {
        String mmlScript = element.getChildNodes().toString();
        presenter.setMmlScript(mmlScript);
    }
}
