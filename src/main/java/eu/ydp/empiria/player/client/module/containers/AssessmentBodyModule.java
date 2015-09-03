package eu.ydp.empiria.player.client.module.containers;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.resources.PageStyleNameConstants;

public class AssessmentBodyModule extends SimpleContainerModuleBase {

    @Inject
    public AssessmentBodyModule(PageStyleNameConstants styleNames) {
        setContainerStyleName(styleNames.QP_BODY());
    }
}
