package eu.ydp.empiria.player.client.controller;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.data.AssessmentDataSourceManager;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import eu.ydp.gwtutil.client.inject.ScriptInjectorWrapper;

import javax.inject.Singleton;

@Singleton
public class GtmLoader {

    public static final String GTM_FILE_NAME = "gtm.js";
    private final EmpiriaPaths empiriaPaths;
    private final ScriptInjectorWrapper scriptInjectorWrapper;
    private final AssessmentDataSourceManager assessmentDataSourceManager;

    @Inject
    public GtmLoader(EmpiriaPaths empiriaPaths, ScriptInjectorWrapper scriptInjectorWrapper, AssessmentDataSourceManager assessmentDataSourceManager) {
        this.empiriaPaths = empiriaPaths;
        this.scriptInjectorWrapper = scriptInjectorWrapper;
        this.assessmentDataSourceManager = assessmentDataSourceManager;
    }

    public void loadGtm() {
        Optional<String> gtm = assessmentDataSourceManager.getAssessmentGtm();
        if (gtm.isPresent()) {
            String gtmURL = empiriaPaths.getCommonsFilePath(GTM_FILE_NAME);
            scriptInjectorWrapper.fromUrl(gtmURL);
        }
    }
}
