package eu.ydp.empiria.player.client.module.dictionary.external.controller.filename;

import com.google.gwt.i18n.client.NumberFormat;

class NumberFormatWrapper {

	public String formatNumber(String pattern, int value) {
		return NumberFormat.getFormat(pattern).format(value);
	}
}
