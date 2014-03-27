package eu.ydp.empiria.player.client.module.report.table.extractor;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_REPORT_SHOW_NON_ACTIVITES;

import java.util.Map;

import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.style.StyleSocket;

public class ShowNonActivitiesExtractor {

	private final StyleSocket styleSocket = PlayerGinjectorFactory.getPlayerGinjector().getStyleSocket();

	public boolean extract(Element element) {
		boolean showNonActivites = true;
		Map<String, String> styles = styleSocket.getStyles(element);
		if (styles.containsKey(EMPIRIA_REPORT_SHOW_NON_ACTIVITES)) {
			showNonActivites = Boolean.parseBoolean(styles.get(EMPIRIA_REPORT_SHOW_NON_ACTIVITES));
		}
		return showNonActivites;
	}
}
