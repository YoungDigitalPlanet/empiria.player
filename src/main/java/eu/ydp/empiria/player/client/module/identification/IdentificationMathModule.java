package eu.ydp.empiria.player.client.module.identification;

import com.google.gwt.query.client.impl.ConsoleBrowser;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.containers.AbstractActivityContainerModuleBase;
import eu.ydp.empiria.player.client.module.mathjax.interaction.MathJaxGapContainer;

public class IdentificationMathModule extends AbstractActivityContainerModuleBase {

    FlowPanel flowPanel;
    @Inject
    MathJaxGapContainer mathJaxGapContainer;


    @Override
    public void initModule(Element element) {
        flowPanel = new FlowPanel();
        getBodyGenerator().generateBody(element, flowPanel);
        String responseIdentifier = element.getAttribute("responseIdentifier");
        mathJaxGapContainer.addMathGap(flowPanel, responseIdentifier);
    }

    @Override
    public Widget getView() {
        return flowPanel;
    }
}
