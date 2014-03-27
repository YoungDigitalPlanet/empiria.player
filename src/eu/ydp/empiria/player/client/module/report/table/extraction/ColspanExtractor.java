package eu.ydp.empiria.player.client.module.report.table.extraction;

import com.google.gwt.xml.client.Element;

import eu.ydp.gwtutil.client.NumberUtils;

public class ColspanExtractor {
	private static final String COLSPAN_ATTR = "colspan";

	public int extract(Element cellElement) {
		int colspan = 1;
		if (cellElement.hasAttribute(COLSPAN_ATTR)) {
			colspan = NumberUtils.tryParseInt(cellElement.getAttribute(COLSPAN_ATTR), 1);
		}
		return colspan;
	}
}
