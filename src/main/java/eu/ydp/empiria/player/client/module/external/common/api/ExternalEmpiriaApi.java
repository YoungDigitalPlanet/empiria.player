package eu.ydp.empiria.player.client.module.external.common.api;

import com.google.gwt.core.client.js.JsType;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.external.common.sound.ExternalSoundInstanceCallback;
import eu.ydp.empiria.player.client.module.external.common.sound.ExternalSoundInstanceCreator;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

@JsType
public class ExternalEmpiriaApi {

    @Inject
    @ModuleScoped
    private ExternalSoundInstanceCreator soundInstanceCreator;

    public void initSound(String src, ExternalSoundInstanceCallback callback) {
        soundInstanceCreator.createSound(src, callback);
    }
}
