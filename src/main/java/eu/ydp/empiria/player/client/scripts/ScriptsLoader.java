package eu.ydp.empiria.player.client.scripts;

import com.google.gwt.core.client.Callback;
import com.google.inject.Inject;
import eu.ydp.gwtutil.client.scripts.AsynchronousScriptsLoader;
import eu.ydp.gwtutil.client.scripts.SynchronousScriptsLoader;

public class ScriptsLoader {

	@Inject
	private SynchronousScriptsLoader synchronousScriptsLoader;
	@Inject
	private AsynchronousScriptsLoader asynchronousScriptsLoader;

	public void inject() {
		synchronousScriptsLoader.injectScripts(SyncLoadingScripts.values() ,new Callback<Void, Exception>() {
			@Override
			public void onFailure(Exception reason) {

			}

			@Override
			public void onSuccess(Void result) {
				asyncScriptLoading();
			}
		});
	}

	private void asyncScriptLoading() {
		asynchronousScriptsLoader.inject(AsyncLoadingScripts.values());
	}


}
