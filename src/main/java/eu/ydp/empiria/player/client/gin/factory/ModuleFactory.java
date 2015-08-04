package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.controller.extensions.internal.PlayerCoreApiExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.ScormSupportExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport.AssessmentJsonReportExtension;
import eu.ydp.empiria.player.client.module.button.NavigationButtonDirection;
import eu.ydp.empiria.player.client.module.button.NavigationButtonModule;
import eu.ydp.empiria.player.client.module.pageswitch.PageSwitchModule;

public interface ModuleFactory {
    AssessmentJsonReportExtension getAssessmentJsonReportExtension();

    ScormSupportExtension getScormSupportExtension();

    PlayerCoreApiExtension getPlayerCoreApiExtension();

    NavigationButtonModule createNavigationButtonModule(NavigationButtonDirection direction);

    PageSwitchModule createPageSwitchModule();
}
