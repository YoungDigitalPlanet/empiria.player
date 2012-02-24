package eu.ydp.empiria.player.client.module.table;

import java.util.Map;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.IContainerModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;
import eu.ydp.empiria.player.client.util.IntegerUtils;

public class TableModule implements IContainerModule {

	protected Panel tablePanel;
	
	public TableModule(){
		tablePanel = new FlowPanel();
		tablePanel.setStyleName("qp-table");
	}
	
	@Override
	public void initModule(Element element, ModuleSocket ms, ModuleInteractionListener mil, BodyGeneratorSocket bgs) {
		FlexTable table = new FlexTable();
		table.setStyleName("qp-table-table");
		
		Map<String, String> styles = ms.getStyles(element);
		
		if (styles.containsKey("-empiria-table-cellpadding")){
			int padding = IntegerUtils.tryParseInt(styles.get("-empiria-table-cellpadding"), -1);
			if (padding != -1)
				table.setCellPadding(padding);
		}
		
		if (styles.containsKey("-empiria-table-cellspacing")){
			int spacing = IntegerUtils.tryParseInt(styles.get("-empiria-table-cellspacing"), -1);
			if (spacing != -1)
				table.setCellSpacing(spacing);
		}		
		
		NodeList trNodes = element.getElementsByTagName("tr");
		for (int r = 0 ; r < trNodes.getLength() ; r ++){
			NodeList tdNodes = ((Element)trNodes.item(r)).getElementsByTagName("td");
			for (int d = 0 ; d < tdNodes.getLength() ; d ++){
				Panel dPanel = new FlowPanel();
				dPanel.setStyleName("qp-table-cell");
				bgs.generateBody(tdNodes.item(d), dPanel);
				table.setWidget(r, d, dPanel);
			}
		}
		tablePanel.add(table);
		
	}

	@Override
	public Widget getView() {
		return tablePanel;
	}

	@Override
	public HasWidgets getContainer() {
		return tablePanel;
	}

}
