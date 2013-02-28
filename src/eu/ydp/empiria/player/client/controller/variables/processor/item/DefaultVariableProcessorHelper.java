package eu.ydp.empiria.player.client.controller.variables.processor.item;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.thirdparty.guava.common.base.Strings;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

public abstract class DefaultVariableProcessorHelper {

	public static List<String> getDifference(Response currentResponse, Outcome previousResponse) {
		List<String> current = currentResponse.values;
		List<String> previous = previousResponse.values;
		List<String> differences = new ArrayList<String>();

		if (currentResponse.cardinality == Cardinality.ORDERED) {

			for (int s = 0; s < current.size() && s < previous.size(); s++) {
				if (current.get(s).compareTo(previous.get(s)) != 0) {
					differences.add(previous.get(s) + "->" + current.get(s));
				} else {
					differences.add("");
				}
			}

		} else {

			for (String currentAnswer : current) {
				if (!Strings.isNullOrEmpty(currentAnswer)) {
					if (!previous.contains(currentAnswer)) {
						differences.add("+"+currentAnswer);
					}
				}
			}

			for (int s = 0; s < current.size(); s++) {
				if (previous.indexOf(current.get(s)) == -1 && (current.get(s).length() > 0 || previous.size() > 0)) {
					differences.add("+" + current.get(s));
				}
			}

			for (int s = 0; s < previous.size(); s++) {
				if (current.indexOf(previous.get(s)) == -1) {
					differences.add("-" + previous.get(s));
				}
			}
		}

		return differences;
	}
}
