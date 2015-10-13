package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.module.info.progress.InfoModuleCssProgressParser;
import eu.ydp.empiria.player.client.module.info.progress.InfoModuleProgressMapping;

public interface InfoModuleFactory {
    InfoModuleCssProgressParser getInfoModuleCssProgressParser(String prefix);

    InfoModuleProgressMapping getInfoModuleProgressMapping(InfoModuleCssProgressParser infoModuleCssProgressParser);
}
