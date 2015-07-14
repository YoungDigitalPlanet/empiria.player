package eu.ydp.empiria.player.client.controller.data;

import com.google.common.base.Strings;
import com.google.gwt.xml.client.Element;

import java.util.ArrayList;
import java.util.List;

public class ElementStyleSelectorBuilder {
    public List<String> getElementSelectors(Element element) {
        String name = element.getNodeName().toLowerCase();
        String[] classes = null;
        String id = null; // NOPMD
        if (!Strings.isNullOrEmpty(element.getAttribute("class"))) {
            classes = element.getAttribute("class").split(" ");
        }
        if (!Strings.isNullOrEmpty(element.getAttribute("id"))) {
            id = element.getAttribute("id");
        }

        return buildSelectors(name, classes, id);
    }

    protected List<String> buildSelectors(String name, String[] classes, String id) {// NOPMD
        List<String> selectors = new ArrayList<String>();

        selectors.add(name);
        if (classes != null) {
            for (int i = 0; i < classes.length; i++) {
                selectors.add("." + classes[i]);
            }
            for (int i = 0; i < classes.length; i++) {
                selectors.add(name + "." + classes[i]);
            }
        }
        if (id != null) {
            selectors.add("#" + id);
            selectors.add(name + "#" + id);
        }

        return selectors;
    }
}
