package eu.ydp.empiria.player.client.controller.style;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

public class StyleLinkManager {

	@Inject
	private StyleLinkAppender styleLinkAppender;

	private final List<String> solidStyles = new ArrayList<String>();

	public void registerAssessmentStyles(List<String> styleLinks) {
		doRegisterStyleLinks(styleLinks, false);
	}

	public void registerItemStyles(List<String> styleLinks) {
		doRegisterStyleLinks(styleLinks, true);
	}

	private void doRegisterStyleLinks(List<String> styleLinks, boolean areRemovable) {
		for (String link : styleLinks) {
			addStyleIfNotPresent(link);
		}
	}

	private void addStyleIfNotPresent(String link) {
		if (!solidStyles.contains(link)) {
			styleLinkAppender.appendStyleLink(link);

			solidStyles.add(link);
		}
	}
}
