package eu.ydp.empiria.player.client.module.report;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.report.table.ReportTable;
import eu.ydp.empiria.player.client.controller.report.table.ReportTableGenerator;
import eu.ydp.empiria.player.client.controller.workmode.PlayerWorkMode;
import eu.ydp.empiria.player.client.controller.workmode.PlayerWorkModeService;
import eu.ydp.empiria.player.client.gin.factory.ReportModuleFactory;
import eu.ydp.empiria.player.client.module.core.base.ContainerModuleBase;
import eu.ydp.empiria.player.client.module.core.flow.OnModuleShowHandler;
import eu.ydp.empiria.player.client.module.report.view.ReportView;

public class ReportModule extends ContainerModuleBase implements OnModuleShowHandler {

    private final ReportView view;
    private final PlayerWorkModeService playerWorkModeService;
    private final ReportModuleFactory reportModuleFactory;

    @Inject
    public ReportModule(ReportView view, PlayerWorkModeService playerWorkModeService,
                        ReportModuleFactory reportModuleFactory) {
        this.playerWorkModeService = playerWorkModeService;
        this.reportModuleFactory = reportModuleFactory;
        this.view = view;
    }

    @Override
    public void initModule(Element element) {
        ReportTableGenerator reportTableGenerator = reportModuleFactory.createReportTableGenerator(getBodyGenerator());
        ReportTable table = reportTableGenerator.generate(element);

        view.setTable(table.getFlexTable());
    }

    @Override
    public Widget getView() {
        return view.asWidget();
    }

    @Override
    public void onShow() {
        playerWorkModeService.tryToUpdateWorkMode(PlayerWorkMode.TEST_SUBMITTED);
    }
}
