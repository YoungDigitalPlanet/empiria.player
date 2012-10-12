package eu.ydp.empiria.player.client.module.report;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_REPORT_ITEMS_INCLUDE;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_REPORT_SHOW_NON_ACTIVITES;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.module.ContainerModuleBase;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class ReportModule extends ContainerModuleBase {

	protected SessionDataSupplier sessionDataSupplier;
	protected DataSourceDataSupplier dataSourceDataSupplier;
	protected FlowRequestInvoker flowRequestInvoker;
	protected String content;

	protected Panel mainPanel;
	protected FlexTable table;
	protected StyleNameConstants styleNames = PlayerGinjector.INSTANCE.getStyleNameConstants();
	private Map<String, String> styles;
	private Element element;

	public ReportModule(FlowRequestInvoker flowRequestInvoker, DataSourceDataSupplier dataSourceDataSupplier, SessionDataSupplier sessionDataSupplier) {
		this.flowRequestInvoker = flowRequestInvoker;
		this.dataSourceDataSupplier = dataSourceDataSupplier;
		this.sessionDataSupplier = sessionDataSupplier;

		mainPanel = new FlowPanel();
		mainPanel.setStyleName(styleNames.QP_REPORT());
	}

	private void fillStyles(FlexTable table, int currCol, int currRow) {
		table.getFlexCellFormatter().addStyleName(currRow, currCol, "qp-report-table-cell");
		table.getFlexCellFormatter().addStyleName(currRow, currCol, "qp-report-table-col-" + currCol);
		table.getRowFormatter().addStyleName(currRow, styleNames.QP_REPORT_TABLE_ROW());
		table.getRowFormatter().addStyleName(currRow, styleNames.QP_REPORT_TABLE_ROW() + "-" + currRow);

	}

	private int renderRow(Node nodeToRender, int currRow, boolean showNonActivites, BodyGeneratorSocket bodyGenSocket, List<Integer> itemIndexes) {
		if (nodeToRender.getNodeType() == Node.ELEMENT_NODE && "rr".equals(nodeToRender.getNodeName())) {
			int currCol = 0;
			NodeList cellNodes = nodeToRender.getChildNodes();
			for (int d = 0; d < cellNodes.getLength(); d++) {
				if (cellNodes.item(d).getNodeType() == Node.ELEMENT_NODE && "rd".equals(cellNodes.item(d).getNodeName())) {
					int colspan = 1;
					if (((Element) cellNodes.item(d)).hasAttribute("colspan")) {
						colspan = NumberUtils.tryParseInt(((Element) cellNodes.item(d)).getAttribute("colspan"), 1);
					}
					Panel cellPanel = new FlowPanel();
					cellPanel.setStyleName(styleNames.QP_REPORT_CELL());
					bodyGenSocket.generateBody(cellNodes.item(d), cellPanel);
					table.setWidget(currRow, currCol, cellPanel);
					table.getFlexCellFormatter().setColSpan(currRow, currCol, colspan);
					fillStyles(table, currCol, currRow);
					Element el = dataSourceDataSupplier.getItem(currRow);
					if (el != null) {
						String className = XMLUtils.getAttributeAsString(el, "class");
						if (className != null && !"".equals(className)) {
							table.getRowFormatter().addStyleName(currRow, className);
						}
					}
					currCol++;
				}
			}
			currRow++;
		} else if (nodeToRender.getNodeType() == Node.ELEMENT_NODE && "prr".equals(nodeToRender.getNodeName())) {
			NodeList cellNodes = nodeToRender.getChildNodes();
			for (int ir = 0; ir < itemIndexes.size(); ir++) {
				int currCol = 0;

				int todo = getItemTodoValue(itemIndexes.get(ir));
				int itemIndex = itemIndexes.get(ir);

				// hiding pages which are not activites
				if (todo == 0 && !showNonActivites) {
					continue;
				}

				for (int d = 0; d < cellNodes.getLength(); d++) {
					if (cellNodes.item(d).getNodeType() == Node.ELEMENT_NODE && "rd".equals(cellNodes.item(d).getNodeName())) {
						int colspan = 1;
						if (((Element) cellNodes.item(d)).hasAttribute("colspan")) {
							colspan = NumberUtils.tryParseInt(((Element) cellNodes.item(d)).getAttribute("colspan"), 1);
						}
						Node dNode = cellNodes.item(d).cloneNode(true);
						NodeList linkNodes = ((Element) dNode).getElementsByTagName("link");
						String name = "itemIndex";
						for (int in = 0; in < linkNodes.getLength(); in++) {
							if (!((Element) linkNodes.item(in)).hasAttribute(name) && !((Element) linkNodes.item(in)).hasAttribute("url")) {
								((Element) linkNodes.item(in)).setAttribute(name, String.valueOf(itemIndex));
							}
						}
						NodeList infoNodes = ((Element) dNode).getElementsByTagName("info");
						for (int in = 0; in < infoNodes.getLength(); in++) {
							if (!((Element) infoNodes.item(in)).hasAttribute(name)) {
								((Element) infoNodes.item(in)).setAttribute(name, String.valueOf(itemIndex));
							}
						}
						Panel cellPanel = new FlowPanel();
						cellPanel.setStyleName(styleNames.QP_REPORT_CELL());
						bodyGenSocket.generateBody(dNode, cellPanel);
						table.setWidget(currRow, currCol, cellPanel);
						table.getFlexCellFormatter().setColSpan(currRow, currCol, colspan);
						fillStyles(table, currCol, currRow);
						Element element = dataSourceDataSupplier.getItem(itemIndex);
						if (element != null) {
							String className = XMLUtils.getAttributeAsString(element, "class");
							if (className != null && !"".equals(className)) {
								table.getRowFormatter().addStyleName(currRow, className);
							}
						}
						table.getRowFormatter().addStyleName(currRow, "qp-report-table-row-page-" + String.valueOf(itemIndex));
						currCol++;

					}
				}
				currRow++;
			}
		}
		return currRow;
	}

	private Map<String, String> getStyles() {
		if (this.styles == null) {
			styles = getModuleSocket().getStyles(element);
		}
		return styles;
	}

	private List<Integer> getRange() {
		String range = "1:-1";
		Map<String, String> styles = getStyles();
		if (styles.containsKey(EMPIRIA_REPORT_ITEMS_INCLUDE)) {
			range = styles.get(EMPIRIA_REPORT_ITEMS_INCLUDE);
		}
		return parseRange(range);
	}

	private boolean isShowNonActivites() {
		boolean showNonActivites = true;
		Map<String, String> styles = getStyles();
		if (styles.containsKey(EMPIRIA_REPORT_SHOW_NON_ACTIVITES)) {
			showNonActivites = Boolean.parseBoolean(styles.get(EMPIRIA_REPORT_SHOW_NON_ACTIVITES));
		}
		return showNonActivites;
	}

	@Override
	public void initModule(Element element, ModuleSocket moduleSocket, InteractionEventsListener mil, BodyGeneratorSocket bgs) {
		super.initModule(element, moduleSocket, mil, bgs);
		this.element = element;
		boolean showNonActivites = isShowNonActivites();
		List<Integer> itemIndexes = getRange();

		table = new FlexTable();
		table.setStyleName(styleNames.QP_REPORT_TABLE());
		String cls = element.getAttribute("class");
		if (cls != null) {
			table.addStyleName(cls);
		}

		int currRow = 0;
		NodeList rowNodes = element.getChildNodes();
		for (int r = 0; r < rowNodes.getLength(); r++) {
			currRow = renderRow(rowNodes.item(r), currRow, showNonActivites, bgs, itemIndexes);
		}
		mainPanel.add(table);
	}

	private int getItemTodoValue(int itemIndex) {
		int todo = 0;
		String value = getItemValue(itemIndex, "TODO");

		if (value != null) {
			todo = Integer.parseInt(value);
		}

		return todo;
	}

	private String getItemValue(int itemIndex, String variableName) {
		String outputValue = null;
		ItemSessionDataSocket itemDataSocket = sessionDataSupplier.getItemSessionDataSocket(itemIndex);
		VariableProviderSocket variableSocket = itemDataSocket.getVariableProviderSocket();
		Variable variable = variableSocket.getVariableValue(variableName);

		if (variable != null) {
			outputValue = variable.getValuesShort();
		}

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
					int fromInt = NumberUtils.tryParseInt(from, 0);
					int toInt = NumberUtils.tryParseInt(to, 0);
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
				Integer intValue = NumberUtils.tryParseInt(level1[i].trim(), 0);
				if (intValue != 0) {
					items.add(intValue - 1);
				}
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
