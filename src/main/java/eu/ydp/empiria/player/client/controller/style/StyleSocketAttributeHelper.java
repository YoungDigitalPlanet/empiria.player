package eu.ydp.empiria.player.client.controller.style;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.util.BooleanUtils;
import eu.ydp.gwtutil.client.xml.XMLParser;

import java.util.Map;

public class StyleSocketAttributeHelper {

    private final XMLParser xmlParser;
    private final BooleanUtils booleanUtil;
    private final StyleSocket styleSocket;

    @Inject
    public StyleSocketAttributeHelper(XMLParser xmlParser, BooleanUtils booleanUtil, StyleSocket styleSocket) {
        this.xmlParser = xmlParser;
        this.booleanUtil = booleanUtil;
        this.styleSocket = styleSocket;
    }

    private Map<String, String> getStyleValue(String attribute) {
        String xml = "<root><" + attribute + " class=\"" + attribute + "\"/></root>";
        Document document = xmlParser.parse(xml);
        Element documentElement = document.getDocumentElement();
        Element firstChild = (Element) documentElement.getFirstChild();
        return styleSocket.getStyles(firstChild);
    }

    private String getString(String nodeName, String attribute) {
        Map<String, String> styles = getStyleValue(nodeName);
        return styles.get(attribute);
    }

    public boolean getBoolean(String nodeName, String attribute) {
        String value = getString(nodeName, attribute);
        return booleanUtil.getBoolean(value);
    }
}
