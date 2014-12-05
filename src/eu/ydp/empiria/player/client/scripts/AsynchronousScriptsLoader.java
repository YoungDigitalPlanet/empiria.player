package eu.ydp.empiria.player.client.scripts;

import com.google.gwt.core.client.Callback;
import com.google.inject.Inject;

import eu.ydp.gwtutil.client.inject.ScriptInjectorWrapper;
import eu.ydp.gwtutil.client.util.paths.UrlConverter;

public class AsynchronousScriptsLoader {

	private final String jQuery = "jquery/jquery-1.10.2.min.js";

	@Inject
	private ScriptInjectorWrapper scriptInjectorWrapper;
	@Inject
	private UrlConverter urlConverter;


	public void inject() {
		String correctUrl = urlConverter.getModuleRelativeUrl(jQuery);
		scriptInjectorWrapper.fromUrl(correctUrl, createJQueryCallback());
	}

	private Callback<Void, Exception> createJQueryCallback() {
		return new Callback<Void, Exception>() {

			@Override
			public void onFailure(Exception reason) {
			}

			@Override
			public void onSuccess(Void result) {
				injectOthers();
			}
		};
	}

	private void injectOthers() {
		for (ScriptsList script : ScriptsList.values()) {
			String correctUrl = urlConverter.getModuleRelativeUrl(script.getUrl());
			scriptInjectorWrapper.fromUrl(correctUrl);
		}
	}
}
