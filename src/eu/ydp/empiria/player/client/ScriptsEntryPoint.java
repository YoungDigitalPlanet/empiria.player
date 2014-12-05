package eu.ydp.empiria.player.client;

import com.google.gwt.core.client.EntryPoint;

import eu.ydp.empiria.player.client.scripts.AsynchronousScriptsLoader;

public class ScriptsEntryPoint implements EntryPoint {

	@Override
	public void onModuleLoad() {
		injectScripts();
	}

	private void injectScripts() {
		AsynchronousScriptsLoader scriptsLoader = PlayerGinjectorFactory.getPlayerGinjector().getAsynchronousScriptsLoader();
		scriptsLoader.inject();
	}
}
