package eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.PlayerJsObjectModifierExtension;

public class AssessmentJsonReportExtension extends InternalExtension implements PlayerJsObjectModifierExtension {

    @Inject
    private AssessmentJsonReportGenerator generator;

    private JavaScriptObject playerJsObject;

    @Override
    public void setPlayerJsObject(JavaScriptObject playerJsObject) {
        this.playerJsObject = playerJsObject;
    }

    @Override
    public void init() {
        initPlayerJsObject(playerJsObject);
    }

    private native void initPlayerJsObject(JavaScriptObject player)/*-{
        var instance = this;

        player.getLessonJSONReport = function () {
            return instance.@eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport.AssessmentJsonReportExtension::getJSONReport()();
        }
    }-*/;

    private String getJSONReport() {
        AssessmentJsonReport jsonReport = generator.generate();
        return jsonReport.getJSONString();
    }
}
