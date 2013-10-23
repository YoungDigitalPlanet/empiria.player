package eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gwt.core.client.JsArray;

import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.js.ProgressBonusConfigJs;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.js.ProgressConfigJs;

public class ProgressBonusConfig {

	private final List<ProgressConfig> progresses;

	public ProgressBonusConfig(List<ProgressConfig> progresses) {
		this.progresses = progresses;
	}

	public List<ProgressConfig> getProgresses() {
		return this.progresses;
	}
	
	
	public static ProgressBonusConfig fromJs(ProgressBonusConfigJs configJs) {
		List<ProgressConfig> progresses = getProgresses(configJs);
		return new ProgressBonusConfig(progresses);
	}

	private static List<ProgressConfig> getProgresses(ProgressBonusConfigJs configJs) {
		List<ProgressConfig> progresses = Lists.newArrayList();
		JsArray<ProgressConfigJs> jsProgresses = configJs.getProgresses();
		
		for(int i=0; i<jsProgresses.length(); i++) {
			ProgressConfigJs progressConfigJs = jsProgresses.get(i);
		 	ProgressConfig progress = ProgressConfig.fromJs(progressConfigJs);
			progresses.add(progress);
		}
		return progresses;
	}
}
