package eu.ydp.empiria.player.client.controller.extensions.internal.state;

import eu.ydp.empiria.player.client.controller.data.AssessmentDataSourceManager;
import eu.ydp.gwtutil.client.Base64Util;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LessonIdentifierProvider {

    private final AssessmentDataSourceManager assessmentDataSourceManager;
    private final Base64Util base64Util;

    @Inject
    public LessonIdentifierProvider(AssessmentDataSourceManager assessmentDataSourceManager, Base64Util base64Util) {
        this.assessmentDataSourceManager = assessmentDataSourceManager;
        this.base64Util = base64Util;
    }

    public String getLessonIdentifier() {
        String title = assessmentDataSourceManager.getAssessmentTitle();
        return base64Util.encode(title);
    }
}
