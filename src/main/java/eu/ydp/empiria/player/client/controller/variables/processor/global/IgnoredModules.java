package eu.ydp.empiria.player.client.controller.variables.processor.global;

import java.util.ArrayList;
import java.util.List;

public class IgnoredModules {

	private final List<String> ignoredList = new ArrayList<>();

	public void addIgnoredID(String id) {
		ignoredList.add(id);
	}

	public boolean isIgnored(String id) {
		return ignoredList.contains(id);
	}
}
