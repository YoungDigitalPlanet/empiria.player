package eu.ydp.empiria.player.client.scripts;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import com.google.gwt.core.client.Callback;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.ConsoleLog;
import eu.ydp.gwtutil.client.inject.ScriptInjectorWrapper;
import eu.ydp.gwtutil.client.util.paths.UrlConverter;

public class Scripts {

	private final String jQuery = "jquery/jquery-1.10.2.min.js";
	private final Set<String> scripts;
	private int counter;

	@Inject
	private ScriptInjectorWrapper scriptInjectorWrapper;
	@Inject
	private UrlConverter urlConverter;
	private ScriptsLoadedListener listener;

	public Scripts() {
		counter = 0;
		scripts = new LinkedHashSet<>();
		Collections.addAll(scripts, "video-js/video.dev.js", "video/AC_RunActiveContent.js", "video/FAVideo.js",
				"jscss/cssparser.js", "jqueryte/jquery-te-1.4.0.min.js", "jquery/jquery-ui-1.10.3.custom.min.js", "jquery/jquery.ui.touch-punch.min.js",
				"jquery/jquery.smooth-scroll.min.js", "lightbox2/js/lightbox-min.js");
	}


	public void inject(ScriptsLoadedListener listener) {
		this.listener = listener;
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
		for (String script : scripts) {
			ConsoleLog.alert(script);
			String correctUrl = urlConverter.getModuleRelativeUrl(script);
			scriptInjectorWrapper.fromUrl(correctUrl, createOthersCallback());
		}
	}

	private Callback<Void, Exception> createOthersCallback() {
		return new Callback<Void, Exception>() {

			@Override
			public void onSuccess(Void result) {
				counter++;
				if (scripts.size() == counter) {
					listener.onScriptsLoaded();
				}
			}

			@Override
			public void onFailure(Exception reason) {
			}
		};
	}

}
