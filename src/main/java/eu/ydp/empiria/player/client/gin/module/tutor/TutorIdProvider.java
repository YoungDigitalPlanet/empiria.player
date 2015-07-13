package eu.ydp.empiria.player.client.gin.module.tutor;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class TutorIdProvider implements Provider<String> {

    private static final String TUTOR_ID_ATTRIBUTE_NAME = "tutorId";

    @Inject
    @ModuleScoped
    private Element xmlElement;

    @Override
    public String get() {
        String tutorId = xmlElement.getAttribute(TUTOR_ID_ATTRIBUTE_NAME);
        assert tutorId != null;
        return tutorId;
    }

}
