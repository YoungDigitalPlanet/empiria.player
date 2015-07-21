package eu.ydp.empiria.player.client.module.sourcelist.structure;

import com.google.common.base.Strings;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import eu.ydp.empiria.player.client.module.ModuleTagName;

import java.util.Arrays;
import java.util.List;

public class ComplexTextChecker {
    private static final String BOLD = "b";
    private static final String ITALIC = "i";
    private static final String UNDERLINE = "u";
    private static final String SUB = "sub";
    private static final String SUP = "sup";
    private static final String MATH_JAX = ModuleTagName.INLINE_MATH_JAX.toString();

    private static final List<String> formatters = Arrays.asList(BOLD, ITALIC, UNDERLINE, SUB, SUP, MATH_JAX);

    private static final List<String> characters = Arrays.asList("&lt;", "&gt;", "&amp;", "&quot;", "&apos;");

    public boolean hasComplexText(Element element) {
        if (checkFormatters(element)) {
            return true;
        }

        return checkCharacters(element);
    }

    private Boolean checkCharacters(Element element) {
        String stringElementValue = element.getChildNodes().toString();
        if (Strings.isNullOrEmpty(stringElementValue)) {
            return false;
        }

        for (String c : characters) {
            if (stringElementValue.contains(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkFormatters(Element element) {
        for (String formatter : formatters) {
            NodeList elements = element.getElementsByTagName(formatter);
            if (elements.getLength() > 0) {
                return true;
            }
        }
        return false;
    }
}
