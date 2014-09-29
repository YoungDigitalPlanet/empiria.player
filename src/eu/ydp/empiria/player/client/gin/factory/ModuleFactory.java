package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.extensions.internal.PlayerCoreApiExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.ScormSupportExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport.AssessmentJsonReportExtension;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.module.button.NavigationButtonDirection;
import eu.ydp.empiria.player.client.module.button.NavigationButtonModule;
import eu.ydp.empiria.player.client.module.pageswitch.PageSwitchModule;
import eu.ydp.empiria.player.client.module.report.ReportModule;

public interface ModuleFactory {
	AssessmentJsonReportExtension getAssessmentJsonReportExtension();

	ScormSupportExtension getScormSupportExtension();

	PlayerCoreApiExtension getPlayerCoreApiExtension();

	NavigationButtonModule createNavigationButtonModule(NavigationButtonDirection direction);

	ReportModule createReportModule(DataSourceDataSupplier dataSourceDataSupplier, SessionDataSupplier sessionDataSupplier);

	PageSwitchModule createPageSwitchModule();
}
