package eu.ydp.empiria.player.client.module.report;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_REPORT_ITEMS_INCLUDE;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_REPORT_SHOW_NON_ACTIVITES;

import java.util.ArrayList;
import java.util.List;
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
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class ReportModule extends ContainerModuleBase {

	private static final String COLSPAN_ATTR = "colspan";
	private static final String TABLE_COL_PREFIX = "qp-report-table-col-";
	private static final String TABLE_CELL_STYLE = "qp-report-table-cell";
	private static final String PAGE_ROW_STYLE_PREFIX = "qp-report-table-row-page-";
	private static final String CLASS = "class";
	private static final String INFO = "info";
	private static final String URL = "url";
	private static final String LINK = "link";
	private final static String RR = "rr";
	private final static String PRR = "prr";
	private final static String RD = "rd";

	protected SessionDataSupplier sessionDataSupplier;
	protected DataSourceDataSupplier dataSourceDataSupplier;
	protected FlowRequestInvoker flowRequestInvoker;
	protected String content;

	protected Panel mainPanel;
	protected FlexTable table;
	protected StyleNameConstants styleNames = PlayerGinjectorFactory.getPlayerGinjector().getStyleNameConstants();
	private Map<String, String> styles;
	private Element element;

	// dodane zmienne
	private boolean showNonActivites;
	private BodyGeneratorSocket bodyGeneratorSocket;
	private static final String ITEM_INDEX = "itemIndex";

	public ReportModule(FlowRequestInvoker flowRequestInvoker, DataSourceDataSupplier dataSourceDataSupplier, SessionDataSupplier sessionDataSupplier) {
		this.flowRequestInvoker = flowRequestInvoker;
		this.dataSourceDataSupplier = dataSourceDataSupplier;
		this.sessionDataSupplier = sessionDataSupplier;

		mainPanel = new FlowPanel();
		mainPanel.setStyleName(styleNames.QP_REPORT());
	}

	private void fillStyles(FlexTable table, int currCol, int currRow) {
		table.getFlexCellFormatter().addStyleName(currRow, currCol, TABLE_CELL_STYLE);
		table.getFlexCellFormatter().addStyleName(currRow, currCol, TABLE_COL_PREFIX + currCol);
		table.getRowFormatter().addStyleName(currRow, styleNames.QP_REPORT_TABLE_ROW());
		table.getRowFormatter().addStyleName(currRow, styleNames.QP_REPORT_TABLE_ROW() + "-" + currRow);

	}

	private int renderHeaderOrPageRow(Node nodeToRender, int currRow, List<Integer> itemIndexes) {
		int rowsCount = 0;

		boolean isElement = isElementNode(nodeToRender);
		if (isElement) {

			String nodeName = nodeToRender.getNodeName();
			if (nodeName.equals(RR)) {
				renderHeaderRow(nodeToRender, currRow);
				rowsCount++;
			} else if (nodeName.equals(PRR)) {
				NodeList cellNodes = nodeToRender.getChildNodes();
				int pageRows = renderPageRows(currRow, itemIndexes, cellNodes);
				rowsCount += pageRows;
			}
		}
		return rowsCount;
	}

	private int renderPageRows(int currRow, List<Integer> itemIndexes, NodeList cellNodes) {
		int pageRow = 0;

		for (int i = 0; i < itemIndexes.size(); i++) {
			int currCol = 0;

			int pageRowIndex = itemIndexes.get(i).intValue();
			int todo = getItemTodoValue(pageRowIndex);

			// hiding pages which are not activites
			boolean shouldRenderPageRow = (todo != 0 || showNonActivites);
			if (shouldRenderPageRow) {
				renderPageRow(currRow + pageRow, cellNodes, currCol, pageRowIndex);
				pageRow++;
			}
		}
		return pageRow;
	}

	private void renderPageRow(int currRow, NodeList cellNodes, int currCol, int pageRowIndex) {
		for (int i = 0; i < cellNodes.getLength(); i++) {
			Node cellNode = cellNodes.item(i);
			boolean isCell = isElementNode(cellNode) && cellNode.getNodeName().equals(RD);
			if (isCell) {
				renderPageCell(currRow, cellNode, currCol, pageRowIndex);
				currCol++;
			}
		}
	}

	private boolean isElementNode(Node cellNode) {
		return cellNode.getNodeType() == Node.ELEMENT_NODE;
	}

	private void renderPageCell(int currRow, Node cellNode, int currCol, int pageRowIndex) {
		int colspan = 1;
		if (((Element) cellNode).hasAttribute(COLSPAN_ATTR)) {
			colspan = NumberUtils.tryParseInt(((Element) cellNode).getAttribute(COLSPAN_ATTR), 1);
		}
		boolean deepCloning = true;
		Element cellElement = (Element) cellNode.cloneNode(deepCloning);

		addItemIndexAttrToLinkTags(pageRowIndex, cellElement);
		addItemIndexAttrToInfoTags(pageRowIndex, cellElement);

		Panel cellPanel = prepareCellPanel(cellElement);
		Element element = dataSourceDataSupplier.getItem(pageRowIndex);
		addCellToTableAndFormat(currRow, currCol, colspan, cellPanel, element);

		table.getRowFormatter().addStyleName(currRow, PAGE_ROW_STYLE_PREFIX + String.valueOf(pageRowIndex));
	}

	private void renderHeaderCell(int currRow, int currCol, Node cellNode) {
		int colspan = 1;
		if (((Element) cellNode).hasAttribute(COLSPAN_ATTR)) {
			colspan = NumberUtils.tryParseInt(((Element) cellNode).getAttribute(COLSPAN_ATTR), 1);
		}
		Panel cellPanel = prepareCellPanel(cellNode);

		Element element = dataSourceDataSupplier.getItem(currRow);

		addCellToTableAndFormat(currRow, currCol, colspan, cellPanel, element);
	}

	private void addCellToTableAndFormat(int currRow, int currCol, int colspan, Panel cellPanel, Element element) {
		table.setWidget(currRow, currCol, cellPanel);
		table.getFlexCellFormatter().setColSpan(currRow, currCol, colspan);
		fillStyles(table, currCol, currRow);

		if (element != null) {
			String className = XMLUtils.getAttributeAsString(element, CLASS);
			if (className != null && !className.isEmpty()) {
				table.getRowFormatter().addStyleName(currRow, className);
			}
		}
	}

	private Panel prepareCellPanel(Node cellNode) {
		Panel cellPanel = new FlowPanel();
		cellPanel.setStyleName(styleNames.QP_REPORT_CELL());
		bodyGeneratorSocket.generateBody(cellNode, cellPanel);
		return cellPanel;
	}

	private void addItemIndexAttrToLinkTags(int itemIndex, Element cellElement) {
		NodeList linkNodes = cellElement.getElementsByTagName(LINK);
		for (int i = 0; i < linkNodes.getLength(); i++) {
			Element linkNode = (Element) linkNodes.item(i);
			boolean hasURL = linkNode.hasAttribute(URL);
			if (!hasURL) {
				addItemIndexAttrIfNotExists(itemIndex, linkNode);
			}
		}
	}

	private void addItemIndexAttrToInfoTags(int itemIndex, Element cellElement) {
		NodeList infoNodes = cellElement.getElementsByTagName(INFO);
		for (int i = 0; i < infoNodes.getLength(); i++) {
			Element infoNode = (Element) infoNodes.item(i);
			addItemIndexAttrIfNotExists(itemIndex, infoNode);
		}
	}

	private void renderHeaderRow(Node nodeToRender, int currRow) {
		int currCol = 0;
		NodeList cellNodes = nodeToRender.getChildNodes();

		for (int d = 0; d < cellNodes.getLength(); d++) {
			Node cellNode = cellNodes.item(d);
			boolean isElement = isElementNode(cellNode);

			if (isElement && cellNode.getNodeName().equals(RD)) {

				renderHeaderCell(currRow, currCol, cellNode);
				currCol++;
			}
		}
	}

	private void addItemIndexAttrIfNotExists(int itemIndex, Element element) {
		if (!element.hasAttribute(ITEM_INDEX)) {
			element.setAttribute(ITEM_INDEX, String.valueOf(itemIndex));
		}
	}

	private Map<String, String> getStyles() {
		if (this.styles == null) {
			PlayerGinjector playerGinjector = PlayerGinjectorFactory.getPlayerGinjector();
			StyleSocket styleSocket = playerGinjector.getStyleSocket();

			styles = styleSocket.getStyles(element);
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
		// PO CO BSG W INITMODULE()?
		super.initModule(element, moduleSocket, mil, bgs);
		bodyGeneratorSocket = bgs;
		this.element = element;
		showNonActivites = isShowNonActivites();
		List<Integer> itemIndexes = getRange();

		table = new FlexTable();
		table.setStyleName(styleNames.QP_REPORT_TABLE());
		String cls = element.getAttribute(CLASS);
		if (cls != null) {
			table.addStyleName(cls);
		}

		int currRow = 0;
		NodeList rowNodes = element.getChildNodes();
		for (int r = 0; r < rowNodes.getLength(); r++) {
			int renderedRows = renderHeaderOrPageRow(rowNodes.item(r), currRow, itemIndexes);
			currRow += renderedRows;
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

}
