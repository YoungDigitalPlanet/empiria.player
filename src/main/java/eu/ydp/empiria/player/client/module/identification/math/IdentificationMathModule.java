package eu.ydp.empiria.player.client.module.identification.math;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.containers.AbstractActivityContainerModuleBase;
import eu.ydp.empiria.player.client.module.identification.math.view.IdentificationMathView;
import eu.ydp.empiria.player.client.module.mathjax.interaction.MathJaxGapContainer;

public class IdentificationMathModule extends AbstractActivityContainerModuleBase {

    private static final String RESPONSE_IDENTIFIER = "responseIdentifier";

    private final MathJaxGapContainer mathJaxGapContainer;
    private final IdentificationMathView view;

    @Inject
    public IdentificationMathModule(MathJaxGapContainer mathJaxGapContainer, IdentificationMathView view) {
        this.mathJaxGapContainer = mathJaxGapContainer;
        this.view = view;
    }

    @Override
    public void initModule(Element element) {
        getBodyGenerator().generateBody(element, view);
        String responseIdentifier = element.getAttribute(RESPONSE_IDENTIFIER);
        mathJaxGapContainer.addMathGap(view.asWidget(), responseIdentifier);
    }

    @Override
    public Widget getView() {
        return view.asWidget();
    }
}
