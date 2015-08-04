package eu.ydp.empiria.player.client.module.mathjax.interaction;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.factory.MathJaxModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.containers.AbstractActivityContainerModuleBase;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxPresenter;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxView;
import eu.ydp.empiria.player.client.module.mathjax.interaction.view.InteractionMathJax;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.proxy.RootPanelDelegate;

public class InteractionMathJaxModule extends AbstractActivityContainerModuleBase implements PlayerEventHandler {

    private MathJaxPresenter presenter;
    private RootPanelDelegate rootPanel;
    private boolean dirty;

    @Inject
    public InteractionMathJaxModule(MathJaxModuleFactory factory, @InteractionMathJax MathJaxView view, RootPanelDelegate rootPanel, EventsBus eventsBus, PageScopeFactory pageScopeFactory) {
        this.rootPanel = rootPanel;
        this.presenter = factory.getMathJaxPresenter(view);
        eventsBus.addAsyncHandler(PlayerEvent.getType(PlayerEventTypes.SOURCE_LIST_CLIENTS_SET_SIZE_COMPLETED), this, pageScopeFactory.getCurrentPageScope());
    }

    @Override
    public Widget getView() {
        return presenter.getView();
    }

    @Override
    public void initModule(Element element, ModuleSocket moduleSocket, BodyGeneratorSocket bodyGenerator) {
        super.initModule(element, moduleSocket, bodyGenerator);

        initPresenter(element);
        generateGaps(element, bodyGenerator);
    }

    private void generateGaps(Element element, BodyGeneratorSocket bodyGenerator) {
        Panel gapContainer = new FlowPanel();
        gapContainer.setVisible(false);
        rootPanel.getRootPanel().add(gapContainer);

        NodeList gaps = element.getElementsByTagName("gap");
        for (int i = 0; i < gaps.getLength(); i++) {
            Node gap = gaps.item(i);
            bodyGenerator.processNode(gap, gapContainer);
        }
    }

    private void initPresenter(Element element) {
        String mmlScript = element.getChildNodes().toString();
        presenter.setMmlScript(mmlScript);
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }


    @Override
    public void onPlayerEvent(PlayerEvent event) {
        if (event.getType() == PlayerEventTypes.SOURCE_LIST_CLIENTS_SET_SIZE_COMPLETED) {
            if (isDirty()) {
                presenter.rerenderMathElement(getModuleId());
                setDirty(false);
            }
        }
    }
}

