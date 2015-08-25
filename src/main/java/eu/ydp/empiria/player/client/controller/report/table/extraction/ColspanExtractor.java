package eu.ydp.empiria.player.client.controller.report.table.extraction;

import com.google.gwt.xml.client.Element;
import eu.ydp.gwtutil.client.NumberUtils;

public class ColspanExtractor {
    private static final String COLSPAN_ATTR = "colspan";

    public int extract(Element cellElement) {
        return NumberUtils.tryParseInt(cellElement.getAttribute(COLSPAN_ATTR), 1);
    }
}
