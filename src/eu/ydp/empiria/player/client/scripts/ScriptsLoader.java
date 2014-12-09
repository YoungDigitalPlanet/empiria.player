package eu.ydp.empiria.player.client.scripts;

import eu.ydp.gwtutil.client.scripts.ScriptUrl;

import com.google.gwt.core.client.Callback;
import com.google.inject.Inject;
import eu.ydp.gwtutil.client.inject.ScriptInjectorWrapper;
import eu.ydp.gwtutil.client.scripts.*;
import eu.ydp.gwtutil.client.util.paths.UrlConverter;

public class ScriptsLoader {

	@Inject
	private ScriptInjectorWrapper scriptInjectorWrapper;
	@Inject
	private UrlConverter urlConverter;
	@Inject
	private AsynchronousScriptsLoader asynchronousScriptsLoader;
	@Inject
	private ScriptInjectorDescriptor scriptInjectorDescriptor;


	public void inject() {
		ScriptUrl firstScript = scriptInjectorDescriptor.getFirstScript();
		String correctUrl = urlConverter.getModuleRelativeUrl(firstScript);
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
		ScriptUrl[] scripts = scriptInjectorDescriptor.getOtherScripts();
		asynchronousScriptsLoader.inject(scripts);
	}
}
