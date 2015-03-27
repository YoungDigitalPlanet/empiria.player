package eu.ydp.empiria.player.client.scripts;

import eu.ydp.gwtutil.client.scripts.ScriptUrl;

public class ScriptInjectorDescriptor {

	private final ScriptUrl jQueryUrl;
	private final ScriptUrl[] otherUrls;

	public ScriptInjectorDescriptor() {
		jQueryUrl = new ScriptUrl() {

			@Override
			public String getUrl() {
				return "jquery/jquery-1.10.2.min.js";
			}
		};

		otherUrls = ScriptsSources.values();
	}

	public ScriptUrl getFirstScript() {
		return jQueryUrl;
	}

	public ScriptUrl[] getOtherScripts() {
		return otherUrls;
	}

}
