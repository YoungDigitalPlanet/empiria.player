package eu.ydp.empiria.player.client.module.report.table.extraction;

import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.NumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_REPORT_ITEMS_INCLUDE;

public class PagesRangeExtractor {

    private static final String DEFAULT_RANGE = "1:-1";
    private final DataSourceDataSupplier dataSourceDataSupplier;
    private final StyleSocket styleSocket;

    public PagesRangeExtractor(StyleSocket styleSocket, DataSourceDataSupplier dataSourceDataSupplier) {
        this.styleSocket = styleSocket;
        this.dataSourceDataSupplier = dataSourceDataSupplier;
    }

    public List<Integer> extract(Element element) {
        String range = DEFAULT_RANGE;
        Map<String, String> styles = styleSocket.getStyles(element);
        if (styles.containsKey(EMPIRIA_REPORT_ITEMS_INCLUDE)) {
            range = styles.get(EMPIRIA_REPORT_ITEMS_INCLUDE);
        }
        return parseRange(range);
    }

    private List<Integer> parseRange(String range) {
        List<Integer> items = new ArrayList<Integer>();
        String[] level1 = range.split(",");
        for (int i = 0; i < level1.length; i++) {
            if (level1[i].contains(":")) {
                if (level1[i].split(":").length == 2) {
                    String from = level1[i].split(":")[0];
                    String to = level1[i].split(":")[1];
                    int fromInt = NumberUtils.tryParseInt(from, 0);
                    int toInt = NumberUtils.tryParseInt(to, 0);
                    if (fromInt != 0 && toInt != 0) {
                        if (toInt > 0) {
                            for (int ii = fromInt; ii <= toInt; ii++) {
                                items.add(ii - 1);
                            }
                        } else {
                            int itemsCount = dataSourceDataSupplier.getItemsCount();
                            for (int ii = fromInt; ii <= itemsCount + toInt + 1; ii++) {
                                items.add(ii - 1);
                            }
                        }
                    }
                }
            } else {
                Integer intValue = NumberUtils.tryParseInt(level1[i].trim(), 0);
                if (intValue != 0) {
                    items.add(intValue - 1);
                }
            }
        }
        return items;
    }
}
