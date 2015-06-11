package eu.ydp.empiria.player.client;

import com.google.gwt.query.client.impl.ConsoleBrowser;

public class Console {

	private static ConsoleBrowser console = new ConsoleBrowser();

	public static void log(String str) {
		console.log(str);
	}

	public static void log(Object obj) {
		console.log(obj);
	}
}
