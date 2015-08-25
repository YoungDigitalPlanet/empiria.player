package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.report.table.ReportTableGenerator;

public interface ReportModuleFactory {

    ReportTableGenerator createReportTableGenerator(BodyGeneratorSocket bgs);
}
