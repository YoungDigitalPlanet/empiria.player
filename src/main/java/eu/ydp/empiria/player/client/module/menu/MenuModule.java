package eu.ydp.empiria.player.client.module.menu;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.report.table.ReportTableGenerator;
import eu.ydp.empiria.player.client.gin.factory.ReportModuleFactory;
import eu.ydp.empiria.player.client.module.ContainerModuleBase;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;

public class MenuModule extends ContainerModuleBase implements PlayerEventHandler {

    private final MenuPresenter presenter;
    private final ReportModuleFactory reportModuleFactory;

    @Inject
    public MenuModule(MenuPresenter presenter, ReportModuleFactory reportModuleFactory, EventsBus eventsBus) {
        this.reportModuleFactory = reportModuleFactory;
        this.presenter = presenter;
        eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.BEFORE_FLOW), this);
    }

    @Override
    public void initModule(Element element, ModuleSocket moduleSocket, BodyGeneratorSocket bgs) {
        super.initModule(element, moduleSocket, bgs);

        ReportTableGenerator reportTableGenerator = reportModuleFactory.createReportTableGenerator(bgs);
        FlexTable table = reportTableGenerator.generate(element);

        presenter.setTable(table);
    }

    @Override
    public Widget getView() {
        return presenter.getView();
    }

    @Override
    public void onPlayerEvent(PlayerEvent event) {
        presenter.hide();
    }
}
