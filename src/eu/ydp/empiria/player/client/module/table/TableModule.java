package eu.ydp.empiria.player.client.module.table;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.*;

import java.util.Map;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.binding.BindingManager;
import eu.ydp.empiria.player.client.module.binding.BindingProxy;
import eu.ydp.empiria.player.client.module.binding.BindingType;
import eu.ydp.empiria.player.client.module.binding.gapwidth.GapWidthBindingManager;
import eu.ydp.empiria.player.client.module.containers.AbstractActivityContainerModuleBase;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.NumberUtils;

public class TableModule extends AbstractActivityContainerModuleBase implements Factory<TableModule>, BindingProxy {

	protected Panel tablePanel;
	private GapWidthBindingManager gapWidthBindingManager;
	protected StyleNameConstants styleNames = PlayerGinjectorFactory.getPlayerGinjector().getStyleNameConstants();
	private final StyleSocket styleSocket = PlayerGinjectorFactory.getPlayerGinjector().getStyleSocket();

	public TableModule() {
		tablePanel = new FlowPanel();
		tablePanel.setStyleName(styleNames.QP_TABLE());
	}

	@Override
	public void initModule(Element element, ModuleSocket moduleSocket, InteractionEventsListener mil, BodyGeneratorSocket bgs) {
		super.initModule(element, moduleSocket, mil, bgs);

		FlexTable table = new FlexTable();
		table.setStyleName(styleNames.QP_TABLE_TABLE());

		Map<String, String> styles = styleSocket.getStyles(element);

		if (styles.containsKey(EMPIRIA_TABLE_CELLPADDING)) {
			int padding = NumberUtils.tryParseInt(styles.get(EMPIRIA_TABLE_CELLPADDING), -1);
			if (padding != -1) {
				table.setCellPadding(padding);
			}
		}

		if (styles.containsKey(EMPIRIA_TABLE_CELLSPACING)) {
			int spacing = NumberUtils.tryParseInt(styles.get(EMPIRIA_TABLE_CELLSPACING), -1);
			if (spacing != -1) {
				table.setCellSpacing(spacing);
			}
		}

		NodeList trNodes = element.getElementsByTagName("tr");
		for (int r = 0; r < trNodes.getLength(); r++) {
			NodeList tdNodes = ((Element) trNodes.item(r)).getElementsByTagName("td");
			for (int d = 0; d < tdNodes.getLength(); d++) {
				Panel dPanel = new FlowPanel();
				dPanel.setStyleName(styleNames.QP_TABLE_CELL());
				bgs.generateBody(tdNodes.item(d), dPanel);
				table.setWidget(r, d, dPanel);

				int colspan = 1;
				if (tdNodes.item(d).getNodeType() == Node.ELEMENT_NODE && ((Element) tdNodes.item(d)).hasAttribute("colspan")) {
					colspan = NumberUtils.tryParseInt(((Element) tdNodes.item(d)).getAttribute("colspan"), 1);
				}
				if (colspan > 1) {
					table.getFlexCellFormatter().setColSpan(r, d, colspan);
				}

				int rowspan = 1;
				if (tdNodes.item(d).getNodeType() == Node.ELEMENT_NODE && ((Element) tdNodes.item(d)).hasAttribute("rowspan")) {
					rowspan = NumberUtils.tryParseInt(((Element) tdNodes.item(d)).getAttribute("rowspan"), 1);
				}
				if (rowspan > 1) {
					table.getFlexCellFormatter().setRowSpan(r, d, rowspan);
				}
			}
		}
		tablePanel.add(table);
	}

	@Override
	public Widget getView() {
		return tablePanel;
	}

	@Override
	public TableModule getNewInstance() {
		return new TableModule();
	}

	@Override
	public BindingManager getBindingManager(BindingType bindingType) {
		if (bindingType == BindingType.GAP_WIDTHS) {
			if (gapWidthBindingManager == null) {
				gapWidthBindingManager = new GapWidthBindingManager(true);
			}
			return gapWidthBindingManager;
		}
		return null;
	}
}
