package eu.ydp.empiria.player.client.module.report;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkMode;
import eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkModeService;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.module.ContainerModuleBase;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.report.table.ReportTableGenerator;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class ReportModule extends ContainerModuleBase {

	private final SessionDataSupplier sessionDataSupplier;
	private final DataSourceDataSupplier dataSourceDataSupplier;

	private final Panel mainPanel;
	private FlexTable table;
	private final StyleNameConstants styleNames = PlayerGinjectorFactory.getPlayerGinjector()
	                                                                    .getStyleNameConstants();
	private final PlayerWorkModeService playerWorkModeService = PlayerGinjectorFactory.getPlayerGinjector()
	                                                                                  .getPlayerWorkModeService();

	public ReportModule(FlowRequestInvoker flowRequestInvoker, DataSourceDataSupplier dataSourceDataSupplier, SessionDataSupplier sessionDataSupplier) {
		this.dataSourceDataSupplier = dataSourceDataSupplier;
		this.sessionDataSupplier = sessionDataSupplier;

		mainPanel = new FlowPanel();
		mainPanel.setStyleName(styleNames.QP_REPORT());
	}

	@Override
	public void initModule(Element element, ModuleSocket moduleSocket, InteractionEventsListener mil, BodyGeneratorSocket bgs) {
		super.initModule(element, moduleSocket, mil, bgs);

		ReportTableGenerator reportTableGenerator = new ReportTableGenerator(bgs, dataSourceDataSupplier, sessionDataSupplier);
		table = reportTableGenerator.generate(element);

		mainPanel.add(table);

		playerWorkModeService.tryToUpdateWorkMode(PlayerWorkMode.TEST_SUBMITTED);
	}

	@Override
	public Widget getView() {
		return mainPanel;
	}
}
