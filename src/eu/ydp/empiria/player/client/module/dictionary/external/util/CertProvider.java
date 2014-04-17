package eu.ydp.empiria.player.client.module.dictionary.external.util;

import java.util.Date;

import com.google.gwt.user.client.Random;

public class CertProvider {

	public String getCert() {
		String tstamp = String.valueOf((new Date()).getTime() / 1000);
		StringBuilder certBuilder = new StringBuilder();
		for (int i = 0; i < tstamp.length(); i++) {
			certBuilder.append(tstamp.substring(i, i + 1));
			if (i != tstamp.length() - 1) {
				certBuilder.append(Math.abs(Random.nextInt() % 10));
			}
		}
		return certBuilder.toString();
	}
}
