package eu.ydp.empiria.player.client;

import com.google.gwt.core.client.EntryPoint;

import eu.ydp.empiria.player.client.scripts.ScriptsLoader;

public class ScriptsEntryPoint implements EntryPoint {

	@Override
	public void onModuleLoad() {
		injectScripts();
	}

	private void injectScripts() {
		ScriptsLoader scriptsLoader = PlayerGinjectorFactory.getPlayerGinjector().getScriptsLoader();
		scriptsLoader.inject();
	}
}
