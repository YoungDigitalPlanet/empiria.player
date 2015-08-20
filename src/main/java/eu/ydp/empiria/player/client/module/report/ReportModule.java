package eu.ydp.empiria.player.client.module.report;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.workmode.PlayerWorkMode;
import eu.ydp.empiria.player.client.controller.workmode.PlayerWorkModeService;
import eu.ydp.empiria.player.client.gin.factory.RaportModuleFactory;
import eu.ydp.empiria.player.client.module.ContainerModuleBase;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.OnModuleShowHandler;
import eu.ydp.empiria.player.client.module.report.table.ReportTableGenerator;

public class ReportModule extends ContainerModuleBase implements OnModuleShowHandler {

    private final SessionDataSupplier sessionDataSupplier;
    private final DataSourceDataSupplier dataSourceDataSupplier;

    private final Panel mainPanel;
    private FlexTable table;
    private final ReportStyleNameConstants styleNames;
    private final PlayerWorkModeService playerWorkModeService;
    private final RaportModuleFactory raportModuleFactory;

    @Inject
    public ReportModule(@Assisted DataSourceDataSupplier dataSourceDataSupplier,
                        @Assisted SessionDataSupplier sessionDataSupplier,
                        ReportStyleNameConstants styleNames, PlayerWorkModeService playerWorkModeService,
                        RaportModuleFactory raportModuleFactory) {
        this.dataSourceDataSupplier = dataSourceDataSupplier;
        this.sessionDataSupplier = sessionDataSupplier;
        this.styleNames = styleNames;
        this.playerWorkModeService = playerWorkModeService;
        this.raportModuleFactory = raportModuleFactory;

        mainPanel = new FlowPanel();
        mainPanel.setStyleName(this.styleNames.QP_REPORT());
    }

    @Override
    public void initModule(Element element, ModuleSocket moduleSocket, BodyGeneratorSocket bgs) {
        super.initModule(element, moduleSocket, bgs);

        ReportTableGenerator reportTableGenerator = raportModuleFactory.createReportTableGenerator(bgs, dataSourceDataSupplier, sessionDataSupplier);
        table = reportTableGenerator.generate(element);

        mainPanel.add(table);
    }

    @Override
    public Widget getView() {
        return mainPanel;
    }

    @Override
    public void onShow() {
        playerWorkModeService.tryToUpdateWorkMode(PlayerWorkMode.TEST_SUBMITTED);

    }
}
