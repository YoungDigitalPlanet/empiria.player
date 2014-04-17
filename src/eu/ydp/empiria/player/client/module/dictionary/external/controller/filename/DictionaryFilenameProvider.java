package eu.ydp.empiria.player.client.module.dictionary.external.controller.filename;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import eu.ydp.gwtutil.client.util.NumberFormatWrapper;

public class DictionaryFilenameProvider {

	private static final String RELATIVE_EXPLANATIONS_DIR = "dictionary/explanations/";

	private final NumberFormatWrapper numberFormatWrapper;

	private final EmpiriaPaths empiriaPaths;

	@Inject
	public DictionaryFilenameProvider(EmpiriaPaths empiriaPaths,
			NumberFormatWrapper numberFormatWrapper) {
		this.empiriaPaths = empiriaPaths;
		this.numberFormatWrapper = numberFormatWrapper;
	}

	public String getFilePathForIndex(int index) {
		final String explanationsDir = extractExplanationsDir();
		return explanationsDir + formatNumber(calculateOffset(index)) + ".xml";
	}

	private String extractExplanationsDir() {
		return empiriaPaths.getCommonsPath() + RELATIVE_EXPLANATIONS_DIR;
	}

	private int calculateOffset(int index) {
		return new Integer(index / 50) * 50;
	}

	private String formatNumber(int num) {
		return numberFormatWrapper.formatNumber("00000", num);
	}
}
