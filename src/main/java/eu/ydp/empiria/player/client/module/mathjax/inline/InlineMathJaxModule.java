package eu.ydp.empiria.player.client.module.mathjax.inline;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.factory.MathJaxModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.core.base.InlineModuleBase;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxPresenter;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxView;
import eu.ydp.empiria.player.client.module.mathjax.inline.view.InlineMathJax;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;

public class InlineMathJaxModule extends InlineModuleBase implements PlayerEventHandler {

    private MathJaxPresenter presenter;

    @Inject
    public InlineMathJaxModule(MathJaxModuleFactory factory, @InlineMathJax MathJaxView view, EventsBus eventsBus, PageScopeFactory pageScopeFactory) {
        this.presenter = factory.getMathJaxPresenter(view);
        eventsBus.addAsyncHandler(PlayerEvent.getType(PlayerEventTypes.PICTURE_LABEL_RENDERED), this, pageScopeFactory.getCurrentPageScope());
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

    @Override
    public void onPlayerEvent(PlayerEvent event) {
        if (event.getType() == PlayerEventTypes.PICTURE_LABEL_RENDERED) {
            typeset();
        }
    }

    private void typeset() {
        presenter.typesetMathElement();
    }
}
