package eu.ydp.empiria.player.client.controller.report.table.extraction;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.style.StyleSocket;

import java.util.Map;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_REPORT_SHOW_NON_ACTIVITES;

public class ShowNonActivitiesExtractor {

    private final StyleSocket styleSocket;

    @Inject
    public ShowNonActivitiesExtractor(StyleSocket styleSocket) {
        this.styleSocket = styleSocket;
    }

    public boolean extract(Element element) {
        Map<String, String> styles = styleSocket.getStyles(element);
        if (styles.containsKey(EMPIRIA_REPORT_SHOW_NON_ACTIVITES)) {
            return Boolean.parseBoolean(styles.get(EMPIRIA_REPORT_SHOW_NON_ACTIVITES));
        }
        return true;
    }
}
