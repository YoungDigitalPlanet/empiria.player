package eu.ydp.empiria.player.client.module.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.controller.session.datasockets.ItemSessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.module.ContainerModuleBase;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.util.IntegerUtils;
import eu.ydp.empiria.player.client.util.XMLUtils;

public class ReportModule extends ContainerModuleBase {

	protected SessionDataSupplier sessionDataSupplier;
	protected DataSourceDataSupplier dataSourceDataSupplier;
	protected FlowRequestInvoker flowRequestInvoker;
	protected String content;
	private String userClass;

	protected Panel mainPanel;
	protected FlexTable table;

	public ReportModule(FlowRequestInvoker flowRequestInvoker, DataSourceDataSupplier dataSourceDataSupplier, SessionDataSupplier sessionDataSupplier) {
		this.flowRequestInvoker = flowRequestInvoker;
		this.dataSourceDataSupplier = dataSourceDataSupplier;
		this.sessionDataSupplier = sessionDataSupplier;

		mainPanel = new FlowPanel();
		mainPanel.setStyleName("qp-report");
	}

	@Override
	public void initModule(Element element, ModuleSocket ms, InteractionEventsListener mil, BodyGeneratorSocket bgs) {
		super.initModule(element, ms, mil, bgs);

		String range ="1:-1";
		boolean showNonActivites = true;

		Map<String, String> styles = ms.getStyles(element);

		if (styles.containsKey("-empiria-report-items-include")) {
			range = styles.get("-empiria-report-items-include");
		}

		if(styles.containsKey("-empiria-report-show-non-activites")){
			showNonActivites = Boolean.parseBoolean(styles.get("-empiria-report-show-non-activites"));
		}

		List<Integer> itemIndexes = parseRange(range);
		GWT.log(itemIndexes.toString());
		NodeList rowNodes = element.getChildNodes();

		table = new FlexTable();
		table.setStyleName("qp-report-table");
		String cls = element.getAttribute("class");
		if (cls != null)
			table.addStyleName(cls);

		int currCol = 0;
		int currRow = 0;

		for (int r = 0; r < rowNodes.getLength(); r++) {
			if (rowNodes.item(r).getNodeType() == Node.ELEMENT_NODE && "rr".equals(rowNodes.item(r).getNodeName())) {
				currCol = 0;
				NodeList cellNodes = rowNodes.item(r).getChildNodes();
				for (int d = 0; d < cellNodes.getLength(); d++) {
					if (cellNodes.item(d).getNodeType() == Node.ELEMENT_NODE && "rd".equals(cellNodes.item(d).getNodeName())) {
						int colspan = 1;
						if (((Element) cellNodes.item(d)).hasAttribute("colspan")) {
							colspan = IntegerUtils.tryParseInt(((Element) cellNodes.item(d)).getAttribute("colspan"), 1);
						}
						Panel cellPanel = new FlowPanel();
						cellPanel.setStyleName("qp-report-cell");
						bgs.generateBody(cellNodes.item(d), cellPanel);
						table.setWidget(currRow, currCol, cellPanel);
						table.getFlexCellFormatter().setColSpan(currRow, currCol, colspan);
						table.getFlexCellFormatter().addStyleName(currRow, currCol, "qp-report-table-cell");
						table.getFlexCellFormatter().addStyleName(currRow, currCol, "qp-report-table-col-" + String.valueOf(currCol));
						table.getRowFormatter().addStyleName(currRow, "qp-report-table-row");
						table.getRowFormatter().addStyleName(currRow, "qp-report-table-row-" + String.valueOf(currRow));
						Element el = dataSourceDataSupplier.getItem(currRow);
						if (el != null) {
							String className = XMLUtils.getAttributeAsString(el, "class");
							if (className != null  &&  !"".equals(className)) {
								table.getRowFormatter().addStyleName(currRow,className);
							}
						}
						currCol++;
					}

				}
				currRow++;
			} else if (rowNodes.item(r).getNodeType() == Node.ELEMENT_NODE && "prr".equals(rowNodes.item(r).getNodeName())) {
				NodeList cellNodes = rowNodes.item(r).getChildNodes();
				for (int ir = 0; ir < itemIndexes.size(); ir++) {
					currCol = 0;

					int todo = getItemTodoValue(itemIndexes.get(ir));
					int itemIndex = itemIndexes.get(ir);

					//hiding pages which are not activites
					if(todo == 0 && !showNonActivites)
						continue;

					for (int d = 0; d < cellNodes.getLength(); d++) {
						if (cellNodes.item(d).getNodeType() == Node.ELEMENT_NODE && "rd".equals(cellNodes.item(d).getNodeName())) {
							int colspan = 1;
							if (((Element) cellNodes.item(d)).hasAttribute("colspan")) {
								colspan = IntegerUtils.tryParseInt(((Element) cellNodes.item(d)).getAttribute("colspan"), 1);
							}
							Node dNode = cellNodes.item(d).cloneNode(true);
							NodeList linkNodes = ((Element) dNode).getElementsByTagName("link");
							for (int in = 0; in < linkNodes.getLength(); in++) {
								if (!((Element) linkNodes.item(in)).hasAttribute("itemIndex") && !((Element) linkNodes.item(in)).hasAttribute("url"))
									((Element) linkNodes.item(in)).setAttribute("itemIndex", String.valueOf(itemIndex));
							}
							NodeList infoNodes = ((Element) dNode).getElementsByTagName("info");
							for (int in = 0; in < infoNodes.getLength(); in++) {
								if (!((Element) infoNodes.item(in)).hasAttribute("itemIndex"))
									((Element) infoNodes.item(in)).setAttribute("itemIndex",  String.valueOf(itemIndex));
							}
							Panel cellPanel = new FlowPanel();
							cellPanel.setStyleName("qp-report-cell");
							bgs.generateBody(dNode, cellPanel);
							table.setWidget(currRow, currCol, cellPanel);
							table.getFlexCellFormatter().setColSpan(currRow, currCol, colspan);
							table.getFlexCellFormatter().addStyleName(currRow, currCol, "qp-report-table-cell");
							table.getFlexCellFormatter().addStyleName(currRow, currCol, "qp-report-table-col-" + String.valueOf(currCol));
							table.getRowFormatter().addStyleName(currRow, "qp-report-table-row");
							table.getRowFormatter().addStyleName(currRow, "qp-report-table-row-" + String.valueOf(currRow));
							Element el = dataSourceDataSupplier.getItem(itemIndex);
							if (el != null) {
								String className = XMLUtils.getAttributeAsString(el, "class");
								if (className != null  &&  !"".equals(className)) {
									table.getRowFormatter().addStyleName(currRow,className);
								}
							}
							table.getRowFormatter().addStyleName(currRow, "qp-report-table-row-page-" + String.valueOf(itemIndex));
							currCol++;

						}
					}
					currRow++;
				}
			}
		}

		mainPanel.add(table);
	}

	private int getItemTodoValue(int itemIndex){
		int todo = 0;
		String value = getItemValue(itemIndex, "TODO");

		if(value != null)
			todo = Integer.parseInt(value);

		return todo;
	}

	private String getItemValue(int itemIndex, String variableName){
		String outputValue = null;
		ItemSessionDataSocket itemDataSocket = sessionDataSupplier.getItemSessionDataSocket(itemIndex);
		VariableProviderSocket variableSocket = itemDataSocket.getVariableProviderSocket();
		Variable variable = variableSocket.getVariableValue(variableName);

		if(variable != null)
			outputValue = variable.getValuesShort();

		return outputValue;
	}

	protected List<Integer> parseRange(String range) {
		List<Integer> items = new ArrayList<Integer>();
		String[] level1 = range.split(",");
		for (int i = 0; i < level1.length; i++) {
			if (level1[i].contains(":")) {
				if (level1[i].split(":").length == 2) {
					String from = level1[i].split(":")[0];
					String to = level1[i].split(":")[1];
					int fromInt = IntegerUtils.tryParseInt(from, 0);
					int toInt = IntegerUtils.tryParseInt(to, 0);
					if (fromInt != 0 && toInt != 0) {
						if (toInt > 0) {
							for (int ii = fromInt; ii <= toInt; ii++) {
								items.add(ii - 1);
							}
						} else {
							int itemsCount = dataSourceDataSupplier.getItemsCount();
							for (int ii = fromInt; ii <= itemsCount + toInt + 1; ii++) {
								items.add(ii - 1);
							}
						}
					}
				}
			} else {
				Integer intValue = IntegerUtils.tryParseInt(level1[i].trim(), 0);
				if (intValue != 0)
					items.add(intValue - 1);
			}
		}
		return items;
	}

	@Override
	public Widget getView() {
		return mainPanel;
	}

	@Override
	public HasWidgets getContainer() {
		return mainPanel;
	}

}
