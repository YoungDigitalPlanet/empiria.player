package eu.ydp.empiria.player.client.module.report;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_REPORT_SHOW_NON_ACTIVITES;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasockets.ItemSessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class ReportTableGenerator {

	private static final String COLSPAN_ATTR = "colspan";
	private static final String TABLE_COL_PREFIX = "qp-report-table-col-";
	private static final String TABLE_CELL_STYLE = "qp-report-table-cell";
	private static final String PAGE_ROW_STYLE_PREFIX = "qp-report-table-row-page-";
	private static final String ITEM_INDEX = "itemIndex";
	private static final String CLASS = "class";
	private static final String INFO = "info";
	private static final String URL = "url";
	private static final String LINK = "link";
	private final static String RR = "rr";
	private final static String PRR = "prr";
	private final static String RD = "rd";

	private final StyleNameConstants styleNames = PlayerGinjectorFactory.getPlayerGinjector().getStyleNameConstants();
	private final StyleSocket styleSocket = PlayerGinjectorFactory.getPlayerGinjector().getStyleSocket();
	private final BodyGeneratorSocket bodyGeneratorSocket;
	private boolean showNonActivites;

	private FlexTable table;
	private final DataSourceDataSupplier dataSourceDataSupplier;
	private final SessionDataSupplier sessionDataSupplier;
	private List<Integer> pagesIndexes;

	public ReportTableGenerator(BodyGeneratorSocket bgs, DataSourceDataSupplier dataSourceDataSupplier, SessionDataSupplier sessionDataSupplier) {
		this.bodyGeneratorSocket = bgs;
		this.dataSourceDataSupplier = dataSourceDataSupplier;
		this.sessionDataSupplier = sessionDataSupplier;
	}

	public FlexTable generate(Element element) {
		showNonActivites = isShowNonActivites(element);

		pagesIndexes = getPagesIndexes(element);

		table = createTableWithStyles(element);
		generateTableContent(element);
		return table;
	}

	private List<Integer> getPagesIndexes(Element element) {
		PagesRangeExtractor extractor = new PagesRangeExtractor(dataSourceDataSupplier);
		return extractor.getPagesRange(element);
	}

	private FlexTable createTableWithStyles(Element element) {
		table = new FlexTable();
		table.setStyleName(styleNames.QP_REPORT_TABLE());
		String cls = element.getAttribute(CLASS);
		if (cls != null) {
			table.addStyleName(cls);
		}
		return table;
	}

	private void generateTableContent(Element element) {
		int currRow = 0;
		NodeList rowNodes = element.getChildNodes();
		for (int r = 0; r < rowNodes.getLength(); r++) {
			int generatedRows = generateHeaderOrPageRow(rowNodes.item(r), currRow);
			currRow += generatedRows;
		}
	}

	private int generateHeaderOrPageRow(Node rowNode, int currRow) {
		int rowsCount = 0;

		boolean isElement = isElementNode(rowNode);
		if (isElement) {

			String nodeName = rowNode.getNodeName();
			if (nodeName.equals(RR)) {
				generateHeaderRow(rowNode, currRow);
				rowsCount++;
			} else if (nodeName.equals(PRR)) {
				NodeList cellNodes = rowNode.getChildNodes();
				int pageRows = generatePageRows(currRow, cellNodes);
				rowsCount += pageRows;
			}
		}
		return rowsCount;
	}

	private void generateHeaderRow(Node rowNode, int currRow) {
		int currCol = 0;
		NodeList cellNodes = rowNode.getChildNodes();

		for (int d = 0; d < cellNodes.getLength(); d++) {
			Node cellNode = cellNodes.item(d);
			boolean isElement = isElementNode(cellNode);

			if (isElement && cellNode.getNodeName().equals(RD)) {

				generateHeaderCell(currRow, currCol, cellNode);
				currCol++;
			}
		}
	}

	private void generateHeaderCell(int currRow, int currCol, Node cellNode) {
		int colspan = 1;
		if (((Element) cellNode).hasAttribute(COLSPAN_ATTR)) {
			colspan = NumberUtils.tryParseInt(((Element) cellNode).getAttribute(COLSPAN_ATTR), 1);
		}
		Panel cellPanel = prepareCellPanel(cellNode);

		Element element = dataSourceDataSupplier.getItem(currRow);

		addCellToTableAndFormat(currRow, currCol, colspan, cellPanel, element);
	}

	private Panel prepareCellPanel(Node cellNode) {
		Panel cellPanel = new FlowPanel();
		cellPanel.setStyleName(styleNames.QP_REPORT_CELL());
		bodyGeneratorSocket.generateBody(cellNode, cellPanel);
		return cellPanel;
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

	private int generatePageRows(int currRow, NodeList cellNodes) {
		int pageRow = 0;

		for (int i = 0; i < pagesIndexes.size(); i++) {
			int currCol = 0;

			int pageRowIndex = pagesIndexes.get(i).intValue();
			int todo = getItemTodoValue(pageRowIndex);

			// hiding pages which are not activites
			boolean shouldRenderPageRow = (todo != 0 || showNonActivites);
			if (shouldRenderPageRow) {
				generatePageRow(currRow + pageRow, cellNodes, currCol, pageRowIndex);
				pageRow++;
			}
		}
		return pageRow;
	}

	private void generatePageRow(int currRow, NodeList cellNodes, int currCol, int pageRowIndex) {
		for (int i = 0; i < cellNodes.getLength(); i++) {
			Node cellNode = cellNodes.item(i);
			boolean isCell = isElementNode(cellNode) && cellNode.getNodeName().equals(RD);
			if (isCell) {
				generatePageCell(currRow, cellNode, currCol, pageRowIndex);
				currCol++;
			}
		}
	}

	private void generatePageCell(int currRow, Node cellNode, int currCol, int pageRowIndex) {
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

	private void addItemIndexAttrIfNotExists(int itemIndex, Element element) {
		if (!element.hasAttribute(ITEM_INDEX)) {
			element.setAttribute(ITEM_INDEX, String.valueOf(itemIndex));
		}
	}

	private boolean isElementNode(Node cellNode) {
		return cellNode.getNodeType() == Node.ELEMENT_NODE;
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

	private void fillStyles(FlexTable table, int currCol, int currRow) {
		table.getFlexCellFormatter().addStyleName(currRow, currCol, TABLE_CELL_STYLE);
		table.getFlexCellFormatter().addStyleName(currRow, currCol, TABLE_COL_PREFIX + currCol);
		table.getRowFormatter().addStyleName(currRow, styleNames.QP_REPORT_TABLE_ROW());
		table.getRowFormatter().addStyleName(currRow, styleNames.QP_REPORT_TABLE_ROW() + "-" + currRow);

	}

	private boolean isShowNonActivites(Element element) {
		boolean showNonActivites = true;
		Map<String, String> styles = styleSocket.getStyles(element);
		if (styles.containsKey(EMPIRIA_REPORT_SHOW_NON_ACTIVITES)) {
			showNonActivites = Boolean.parseBoolean(styles.get(EMPIRIA_REPORT_SHOW_NON_ACTIVITES));
		}
		return showNonActivites;
	}
}
