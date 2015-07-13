package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.connection.exception.CssStyleException;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.style.CssHelper;
import eu.ydp.gwtutil.client.xml.XMLParser;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConnectionStyleChecker {

    private final CssHelper cssHelper;
    private final StyleNameConstants styleNames;
    private final XMLParser xmlParser;
    private final StyleSocket styleSocket;

    protected Set<String> cssClassNames = Sets.newHashSet();

    @Inject
    public ConnectionStyleChecker(StyleSocket styleSocket, XMLParser xmlParser, StyleNameConstants styleNames, CssHelper cssHelper) {
        this.styleNames = styleNames;
        this.cssHelper = cssHelper;
        this.xmlParser = xmlParser;
        this.styleSocket = styleSocket;
    }

    @PostConstruct
    public void postConstruct() {
        styleNames.QP_CONNECTION();
        cssClassNames.add(styleNames.QP_CONNECTION_CENTER_COLUMN());
        cssClassNames.add(styleNames.QP_CONNECTION_CORRECT());
        cssClassNames.add(styleNames.QP_CONNECTION_DISABLED());
        cssClassNames.add(styleNames.QP_CONNECTION_ITEM());
        cssClassNames.add(styleNames.QP_CONNECTION_ITEM_CONECTED());
        cssClassNames.add(styleNames.QP_CONNECTION_ITEM_CONECTED_DISABLED());
        cssClassNames.add(styleNames.QP_CONNECTION_ITEM_CONTENT());
        cssClassNames.add(styleNames.QP_CONNECTION_ITEM_SELECTED());
        cssClassNames.add(styleNames.QP_CONNECTION_ITEM_SELECTION_BUTTON());
        cssClassNames.add(styleNames.QP_CONNECTION_LEFT_COLUMN());
        cssClassNames.add(styleNames.QP_CONNECTION_NONE());
        cssClassNames.add(styleNames.QP_CONNECTION_NORMAL());
        cssClassNames.add(styleNames.QP_CONNECTION_RIGHT_COLUMN());
        cssClassNames.add(styleNames.QP_CONNECTION_WRONG());
    }

    public void areStylesCorrectThrowsExceptionWhenNot(IsWidget widget) throws CssStyleException {
        checkStylesFromCssParser();
        checkNativeStylesForWidgetHierarchy(widget);
    }

    @SuppressWarnings("PMD")
    private void checkStylesFromCssParser() {
        for (String className : cssClassNames) {
            StringBuilder xml = new StringBuilder("<root><").append(className).append(" class=\"").append(className).append("\"/></root>");
            Element firstChild = (Element) xmlParser.parse(xml.toString()).getDocumentElement().getFirstChild();
            Map<String, String> stylesForModule = getStylesForModule(styleSocket, firstChild);

            if (cssHelper.checkIfEquals(stylesForModule, "display", "table-cell")) {
                throw new CssStyleException("Css with display:table-cell is not supported in ConnectionModule");
            }
        }
    }

    @SuppressWarnings("PMD")
    private void checkNativeStylesForWidgetHierarchy(IsWidget widget) {
        if (widget != null) {
            List<Style> allStyles = collectAllStyles(widget);
            checkNativeStyles(allStyles);
        }
    }

    private List<Style> collectAllChildrenStyles(NodeList<Node> childNodes) {
        List<Style> allStyles = Lists.newArrayList();
        for (int x = 0; x < childNodes.getLength(); ++x) {
            Node item = childNodes.getItem(x);
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                Style styles = cssHelper.getComputedStyle(item);
                allStyles.add(styles);
                allStyles.addAll(collectAllChildrenStyles(((com.google.gwt.dom.client.Element) item).getChildNodes()));
            }
        }
        return allStyles;
    }

    private void checkNativeStyles(List<Style> allStyles) {
        for (Style style : allStyles) {
            if (cssHelper.checkIfEquals(style, "display", "table-cell")) {
                throw new CssStyleException("Css with display:table-cell is not supported in ConnectionModule");
            }
        }
    }

    private List<Style> collectAllStyles(IsWidget widget) {
        List<Style> allStyles = Lists.newArrayList();
        com.google.gwt.user.client.Element widgetElement = widget.asWidget().getElement();
        allStyles.add(cssHelper.getComputedStyle(widgetElement));
        allStyles.addAll(collectAllChildrenStyles(widgetElement.getChildNodes()));
        return allStyles;
    }

    private Map<String, String> getStylesForModule(StyleSocket styleSocket, Element moduleXml) {
        return styleSocket.getStyles(moduleXml);
    }
}
