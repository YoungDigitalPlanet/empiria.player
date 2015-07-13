package eu.ydp.empiria.player.client.module.containers;

import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class AssessmentBodyModule extends SimpleContainerModuleBase {

    protected StyleNameConstants styleNames = PlayerGinjectorFactory.getPlayerGinjector().getStyleNameConstants();

    public AssessmentBodyModule() {
        setContainerStyleName(styleNames.QP_BODY());
    }

    public void initModule(Element element, BodyGeneratorSocket generator) {
        initModule(element, null, generator);
    }
}
