package eu.ydp.empiria.player.client.style;

import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.controller.communication.PageReference;

import java.util.Map;

public interface StyleSocket {
    /**
     * @return styles in lower case.
     */
    public Map<String, String> getStyles(Element element);

    /**
     * @return styles in unchanged letter case.
     */
    public Map<String, String> getOrgStyles(Element element);

    public Map<String, String> getStyles(String selector);

    public void setCurrentPages(PageReference pr);
}
