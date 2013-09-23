package eu.ydp.empiria.player.client.components.animation.swiffy;

import java.util.Set;

import com.google.common.collect.Sets;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.core.client.ScriptInjector.FromUrl;

public class SwiffyRuntimeLoader {
	private boolean runtimeLoadStarted, runtimeLoaded;
	private final Set<SwiffyRuntimeLoadHandler> loadHandlers = Sets.newHashSet();

	public void loadRuntime() {
		if (!runtimeLoadStarted) {
			runtimeLoadStarted = true;
			loadRuntimeAndCallHandlersOnSuccess();
		}
	}

	private void loadRuntimeAndCallHandlersOnSuccess() {
		FromUrl fromUrl = ScriptInjector.fromUrl(getRuntimeJsUrl());
		fromUrl.setCallback(new Callback<Void, Exception>() {

			@Override
			public void onSuccess(Void result) {
				runtimeLoaded = true;
				callHandlers();
			}
			@Override
			public void onFailure(Exception reason) {}

		});
		fromUrl.inject();
	}

	public void addRuntimeLoadHandler(SwiffyRuntimeLoadHandler loadHandler) {
		if (isRuntimeLoaded()) {
			loadHandler.onLoad();
		} else {
			loadHandlers.add(loadHandler);
		}
	}

	public void removeRuntimeLoadHandler(SwiffyRuntimeLoadHandler loadHandler){
		loadHandlers.remove(loadHandler);
	}

	private void callHandlers() {
		for (SwiffyRuntimeLoadHandler handler : loadHandlers) {
			handler.onLoad();
		}
		loadHandlers.clear();
	}

	public boolean isRuntimeLoaded() {
		return runtimeLoaded;
	}

	private String getRuntimeJsUrl() {
		return GWT.getModuleBaseURL()+"swiffy/runtime.js";
	}
}
