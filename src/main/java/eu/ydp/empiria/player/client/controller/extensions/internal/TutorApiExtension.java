package eu.ydp.empiria.player.client.controller.extensions.internal;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorService;
import eu.ydp.empiria.player.client.controller.extensions.types.PlayerJsObjectModifierExtension;
import eu.ydp.gwtutil.client.collections.JsMapStringToInt;

import java.util.Map;
import java.util.Map.Entry;

public class TutorApiExtension extends InternalExtension implements PlayerJsObjectModifierExtension {

    private final TutorService tutorService;

    @Inject
    public TutorApiExtension(TutorService tutorService) {
        this.tutorService = tutorService;
    }

    @Override
    public void init() {
    }

    @Override
    public void setPlayerJsObject(JavaScriptObject playerJsObject) {
        initTutorApi(playerJsObject);
        importTutorPersonasId(playerJsObject);
    }

    private native void initTutorApi(JavaScriptObject playerJsObject) /*-{
        var self = this;
        playerJsObject.exportTutorPersonasId = function () {
            return self.@eu.ydp.empiria.player.client.controller.extensions.internal.TutorApiExtension::exportTutorPersonasId()();
        }
    }-*/;

    JsMapStringToInt exportTutorPersonasId() {
        Map<String, Integer> tutorIdToCurrentPersonaIndexMap = tutorService.buildTutorIdToCurrentPersonaIndexMap();
        JsMapStringToInt jsTutorToPersonaIndex = JavaScriptObject.createObject().cast();
        for (Entry<String, Integer> tutorIdToCurrentPersonaIndex : tutorIdToCurrentPersonaIndexMap.entrySet()) {
            String key = tutorIdToCurrentPersonaIndex.getKey();
            Integer value = tutorIdToCurrentPersonaIndex.getValue();
            jsTutorToPersonaIndex.put(key, value);
        }

        return jsTutorToPersonaIndex;
    }

    private void importTutorPersonasId(JavaScriptObject playerJsObject) {
        JsMapStringToInt idMap = importTutorPersonasIdNative(playerJsObject).cast();
        importTutorPersonasId(idMap);
    }

    private native JavaScriptObject importTutorPersonasIdNative(JavaScriptObject playerJsObject) /*-{
        if (!!playerJsObject.importTutorPersonasId) {
            return playerJsObject.importTutorPersonasId();
        }
        return {};
    }-*/;

    void importTutorPersonasId(JsMapStringToInt jsTutorToPersonaIndex) {
        tutorService.importCurrentPersonasForTutors(jsTutorToPersonaIndex);
    }

}
