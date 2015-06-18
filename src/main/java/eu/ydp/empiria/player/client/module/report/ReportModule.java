package eu.ydp.empiria.player.client.module.report;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.workmode.*;
import eu.ydp.empiria.player.client.module.*;
import eu.ydp.empiria.player.client.module.report.table.ReportTableGenerator;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class ReportModule extends ContainerModuleBase implements OnModuleShowHandler {

	private final SessionDataSupplier sessionDataSupplier;
	private final DataSourceDataSupplier dataSourceDataSupplier;

	private final Panel mainPanel;
	private FlexTable table;
	private final StyleNameConstants styleNames;
	private final PlayerWorkModeService playerWorkModeService;

	@Inject
	public ReportModule(@Assisted DataSourceDataSupplier dataSourceDataSupplier,
 @Assisted SessionDataSupplier sessionDataSupplier,
			StyleNameConstants styleNames, PlayerWorkModeService playerWorkModeService) {
		this.dataSourceDataSupplier = dataSourceDataSupplier;
		this.sessionDataSupplier = sessionDataSupplier;
		this.styleNames = styleNames;
		this.playerWorkModeService = playerWorkModeService;

		mainPanel = new FlowPanel();
		mainPanel.setStyleName(this.styleNames.QP_REPORT());
	}

	@Override
	public void initModule(Element element, ModuleSocket moduleSocket, BodyGeneratorSocket bgs) {
		super.initModule(element, moduleSocket, bgs);

		ReportTableGenerator reportTableGenerator = new ReportTableGenerator(bgs, dataSourceDataSupplier, sessionDataSupplier);
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
