package eu.ydp.empiria.player.client.module.menu;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.report.table.ReportTableGenerator;
import eu.ydp.empiria.player.client.gin.factory.ReportModuleFactory;
import eu.ydp.empiria.player.client.module.ContainerModuleBase;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;

import java.util.Map;

import static eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes.BEFORE_FLOW;
import static eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes.PAGE_LOADED;

public class MenuModule extends ContainerModuleBase implements PlayerEventHandler {

    private final MenuPresenter presenter;
    private FlowDataSupplier flowDataSupplier;
    private final ReportModuleFactory reportModuleFactory;
    private Map<Integer, Integer> pageToRow;

    @Inject
    public MenuModule(MenuPresenter presenter, ReportModuleFactory reportModuleFactory, EventsBus eventsBus, FlowDataSupplier flowDataSupplier) {
        this.reportModuleFactory = reportModuleFactory;
        this.presenter = presenter;
        this.flowDataSupplier = flowDataSupplier;
        eventsBus.addHandler(PlayerEvent.getTypes(BEFORE_FLOW, PAGE_LOADED), this);
    }

    @Override
    public void initModule(Element element, ModuleSocket moduleSocket, BodyGeneratorSocket bgs) {
        super.initModule(element, moduleSocket, bgs);

        ReportTableGenerator reportTableGenerator = reportModuleFactory.createReportTableGenerator(bgs);
        FlexTable table = reportTableGenerator.generate(element);
        pageToRow = reportTableGenerator.getPageToRowMap();
        presenter.setTable(table);
    }

    @Override
    public Widget getView() {
        return presenter.getView();
    }

    @Override
    public void onPlayerEvent(PlayerEvent event) {
        int i = flowDataSupplier.getCurrentPageIndex();
        int row = currentRow(i);
        switch (event.getType()) {
            case BEFORE_FLOW:
                presenter.hide();
                presenter.unmarkRow(row);
                break;
            case PAGE_LOADED:
                presenter.markRow(row);
        }
    }

    private int currentRow(int page) {
        if (pageToRow.containsKey(page)) {
            return pageToRow.get(page);
        }
        return -1;
    }
}
