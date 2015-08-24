package eu.ydp.empiria.player.client.controller.report.table;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.report.table.cell.CellCoords;
import eu.ydp.empiria.player.client.controller.report.table.extraction.ColspanExtractor;
import eu.ydp.empiria.player.client.controller.report.table.extraction.PageTodoExtractor;
import eu.ydp.empiria.player.client.controller.report.table.extraction.PagesRangeExtractor;
import eu.ydp.empiria.player.client.controller.report.table.extraction.ShowNonActivitiesExtractor;
import eu.ydp.empiria.player.client.controller.report.table.modification.ItemIndexAppender;
import eu.ydp.empiria.player.client.controller.report.table.modification.RowStylesAppender;
import eu.ydp.empiria.player.client.module.report.ReportStyleNameConstants;
import eu.ydp.gwtutil.client.xml.XMLUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportTableGenerator {

    private static final String PAGE_ROW_STYLE_PREFIX = "qp-report-table-row-page-";
    private static final String CLASS = "class";
    private final static String RR = "rr";
    private final static String PRR = "prr";
    private final static String RD = "rd";

    private final ReportStyleNameConstants styleNames;

    private final BodyGeneratorSocket bodyGeneratorSocket;
    private final DataSourceDataSupplier dataSourceDataSupplier;

    private final RowStylesAppender rowStylesAppender;
    private final PageTodoExtractor pageTodoExtractor;
    private final PagesRangeExtractor pagesRangeExtractor;
    private final ShowNonActivitiesExtractor showNonActivitiesExtractor;
    private final ItemIndexAppender itemIndexAppender;
    private final ColspanExtractor colspanExtractor;

    private boolean showNonActivites;
    private List<Integer> pagesIndexes;
    private Map<Integer, Integer> pageToRow;

    private FlexTable table;

    @Inject
    public ReportTableGenerator(@Assisted BodyGeneratorSocket bgs, DataSourceDataSupplier dataSourceDataSupplier, ReportStyleNameConstants styleNames,
                                RowStylesAppender rowStylesAppender, ShowNonActivitiesExtractor showNonActivitiesExtractor,
                                ItemIndexAppender itemIndexAppender, ColspanExtractor colspanExtractor, PagesRangeExtractor pagesRangeExtractor, PageTodoExtractor pageTodoExtractor) {
        this.bodyGeneratorSocket = bgs;
        this.dataSourceDataSupplier = dataSourceDataSupplier;
        this.styleNames = styleNames;

        this.rowStylesAppender = rowStylesAppender;
        this.pageTodoExtractor = pageTodoExtractor;
        this.pagesRangeExtractor = pagesRangeExtractor;
        this.showNonActivitiesExtractor = showNonActivitiesExtractor;
        this.itemIndexAppender = itemIndexAppender;
        this.colspanExtractor = colspanExtractor;
    }

    public FlexTable generate(Element element) {
        pageToRow = new HashMap<>();
        showNonActivites = showNonActivitiesExtractor.extract(element);
        pagesIndexes = pagesRangeExtractor.extract(element);

        table = createTableWithStyles(element);
        generateTableContent(element);
        return table;
    }

    public Map<Integer, Integer> getPageToRowMap() {
        return pageToRow;
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

            NodeList cellNodes = rowNode.getChildNodes();
            String nodeName = rowNode.getNodeName();

            if (nodeName.equals(RR)) {
                generateHeaderRow(currRow, cellNodes);
                rowsCount++;
            } else if (nodeName.equals(PRR)) {
                int pageRows = generatePageRows(currRow, cellNodes);
                rowsCount += pageRows;
            }
        }
        return rowsCount;
    }

    private void generateHeaderRow(int currRow, NodeList cellNodes) {
        int currCol = 0;

        for (int i = 0; i < cellNodes.getLength(); i++) {
            Node cellNode = cellNodes.item(i);
            boolean isCell = isElementNode(cellNode) && cellNode.getNodeName().equals(RD);

            if (isCell) {
                CellCoords cellCoords = new CellCoords(currRow, currCol);
                generateHeaderCell(cellCoords, cellNode);
                currCol++;
            }
        }
    }

    private void generateHeaderCell(CellCoords cellCoords, Node cellNode) {
        Panel cellPanel = prepareCellPanel(cellNode);
        Element cellElement = dataSourceDataSupplier.getItem(cellCoords.getRow());
        int colspan = colspanExtractor.extract(cellElement);

        addCellToTableAndFormat(cellCoords, colspan, cellPanel, cellElement);
    }

    private Panel prepareCellPanel(Node cellNode) {
        Panel cellPanel = new FlowPanel();
        cellPanel.setStyleName(styleNames.QP_REPORT_CELL());
        bodyGeneratorSocket.generateBody(cellNode, cellPanel);
        return cellPanel;
    }

    private void addCellToTableAndFormat(CellCoords cellCoords, int colspan, Panel cellPanel, Element element) {
        table.setWidget(cellCoords.getRow(), cellCoords.getCol(), cellPanel);
        table.getFlexCellFormatter().setColSpan(cellCoords.getRow(), cellCoords.getCol(), colspan);
        rowStylesAppender.append(table, cellCoords);

        if (element != null) {
            String className = XMLUtils.getAttributeAsString(element, CLASS);
            if (className != null && !className.isEmpty()) {
                table.getRowFormatter().addStyleName(cellCoords.getRow(), className);
            }
        }
    }

    private int generatePageRows(int currRow, NodeList cellNodes) {
        int pageRow = 0;

        for (Integer pagesIndexe : pagesIndexes) {

            int pageRowIndex = pagesIndexe;
            int todo = pageTodoExtractor.extract(pageRowIndex);

            boolean shouldRenderPageRow = (todo != 0 || showNonActivites);
            if (shouldRenderPageRow) {
                int row = currRow + pageRow;
                generatePageRow(row, cellNodes, pageRowIndex);
                pageToRow.put(pageRowIndex, row);
                pageRow++;
            }
        }
        return pageRow;
    }

    private void generatePageRow(int currRow, NodeList cellNodes, int pageRowIndex) {
        int currCol = 0;
        for (int i = 0; i < cellNodes.getLength(); i++) {
            Node cellNode = cellNodes.item(i);
            boolean isCell = isElementNode(cellNode) && cellNode.getNodeName().equals(RD);
            if (isCell) {
                CellCoords cellCoords = new CellCoords(currRow, currCol);
                generatePageCell(cellCoords, cellNode, pageRowIndex);
                currCol++;
            }
        }
    }

    private void generatePageCell(CellCoords cellCoords, Node cellNode, int pageRowIndex) {
        boolean deepCloning = true;
        Element cellElement = (Element) cellNode.cloneNode(deepCloning);
        int colspan = colspanExtractor.extract(cellElement);

        itemIndexAppender.appendToLinkTags(pageRowIndex, cellElement);
        itemIndexAppender.appendToInfoTags(pageRowIndex, cellElement);

        Panel cellPanel = prepareCellPanel(cellElement);
        Element element = dataSourceDataSupplier.getItem(pageRowIndex);
        addCellToTableAndFormat(cellCoords, colspan, cellPanel, element);

        table.getRowFormatter().addStyleName(cellCoords.getRow(), PAGE_ROW_STYLE_PREFIX + String.valueOf(pageRowIndex));
    }

    private boolean isElementNode(Node cellNode) {
        return cellNode.getNodeType() == Node.ELEMENT_NODE;
    }
}
