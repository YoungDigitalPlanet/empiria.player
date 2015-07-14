package eu.ydp.empiria.player.client.module.report.table.modification;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

public class ItemIndexAppender {

    private static final String ITEM_INDEX = "itemIndex";
    private static final String INFO = "info";
    private static final String URL = "url";
    private static final String LINK = "link";

    public void appendToLinkTags(int itemIndex, Element cellElement) {
        NodeList linkNodes = cellElement.getElementsByTagName(LINK);
        for (int i = 0; i < linkNodes.getLength(); i++) {
            Element linkNode = (Element) linkNodes.item(i);
            boolean hasURL = linkNode.hasAttribute(URL);
            if (!hasURL) {
                addItemIndexAttrIfNotExists(itemIndex, linkNode);
            }
        }
    }

    public void appendToInfoTags(int itemIndex, Element cellElement) {
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

}
