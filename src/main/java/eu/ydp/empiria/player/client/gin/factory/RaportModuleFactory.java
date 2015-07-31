package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.module.report.ReportModule;
import eu.ydp.empiria.player.client.module.report.table.ReportTableGenerator;
import eu.ydp.empiria.player.client.module.report.table.extraction.PageTodoExtractor;
import eu.ydp.empiria.player.client.module.report.table.extraction.PagesRangeExtractor;

public interface RaportModuleFactory {

    ReportModule createReportModule(DataSourceDataSupplier dataSourceDataSupplier, SessionDataSupplier sessionDataSupplier);

    ReportTableGenerator createReportTableGenerator(BodyGeneratorSocket bgs, DataSourceDataSupplier dataSourceDataSupplier,
                                                    SessionDataSupplier sessionDataSupplier);

    PageTodoExtractor createPageTodoExtractor(SessionDataSupplier sessionDataSupplier);

    PagesRangeExtractor createPagesRangeExtractor(DataSourceDataSupplier dataSourceDataSupplier);
}
