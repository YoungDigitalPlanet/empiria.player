package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.connector;

import com.google.gwt.core.client.JsArrayString;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.params.JsMediaParams;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.params.JsMediaStatus;
import eu.ydp.gwtutil.client.collections.CollectionsUtil;

public class JsMediaConnector implements MediaConnector {

	@Inject
	private Provider<MediaConnectorListener> listener;

	public JsMediaConnector() {
		initJs();
	}

	private native void initJs()/*-{
								var instance = this;
								$wnd.empiriaExternalMediaOnReady = function(id, paramsObject){
								instance.@eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.connector.JsMediaConnector::onReady(Ljava/lang/String;Leu/ydp/empiria/player/client/controller/extensions/internal/sound/external/params/JsMediaParams;)(id, paramsObject);
								}
								$wnd.empiriaExternalMediaOnPlay = function(id){
								instance.@eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.connector.JsMediaConnector::onPlay(Ljava/lang/String;)(id);
								}
								$wnd.empiriaExternalMediaOnPause = function(id){
								instance.@eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.connector.JsMediaConnector::onPause(Ljava/lang/String;)(id);
								}
								$wnd.empiriaExternalMediaOnTimeUpdate = function(id, status){
								instance.@eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.connector.JsMediaConnector::onTimeUpdate(Ljava/lang/String;Leu/ydp/empiria/player/client/controller/extensions/internal/sound/external/params/JsMediaStatus;)(id, status);
								}
								$wnd.empiriaExternalMediaOnEnd = function(id){
								instance.@eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.connector.JsMediaConnector::onEnd(Ljava/lang/String;)(id);
								}
								}-*/;

	@Override
	public void init(String id, Iterable<String> sources) {
		init(id, CollectionsUtil.iterableToJsArray(sources));
	}

	private native void init(String id, JsArrayString sources) /*-{
																if (typeof $wnd.empiriaExternalMediaInit == 'function'){
																$wnd.empiriaExternalMediaInit(sources, id);
																}
																}-*/;

	@Override
	public void play(String id) {
		playJs(id);
	}

	private native void playJs(String id) /*-{
											if (typeof $wnd.empiriaExternalMediaPlay == 'function'){
											$wnd.empiriaExternalMediaPlay(id);
											}
											}-*/;

	@Override
	public void pause(String id) {
		pauseJs(id);
	}

	private native void pauseJs(String id) /*-{
											if (typeof $wnd.empiriaExternalMediaPause == 'function'){
											$wnd.empiriaExternalMediaPause(id);
											}
											}-*/;

	@Override
	public void seek(String id, int timeMillis) {
		seekJs(id, timeMillis);
	}

	private native void seekJs(String id, int timeMillis) /*-{
															if (typeof $wnd.empiriaExternalMediaSeek == 'function'){
															$wnd.empiriaExternalMediaSeek(id, timeMillis);
															}
															}-*/;

	private void onReady(String id, JsMediaParams params) {
		listener.get().onReady(id, params);
	}

	private void onPlay(String id) {
		listener.get().onPlay(id);
	}

	private void onPause(String id) {
		listener.get().onPause(id);
	}

	private void onTimeUpdate(String id, JsMediaStatus status) {
		listener.get().onTimeUpdate(id, status);
	}

	private void onEnd(String id) {
		listener.get().onEnd(id);
	}
}
