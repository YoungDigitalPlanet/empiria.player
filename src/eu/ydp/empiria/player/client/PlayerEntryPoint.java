/*
  The MIT License

  Copyright (c) 2009 Krzysztof Langner

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
 */
package eu.ydp.empiria.player.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

import eu.ydp.empiria.player.client.scripts.Scripts;
import eu.ydp.empiria.player.client.scripts.ScriptsLoadedListener;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import eu.ydp.gwtutil.client.Alternative;
import eu.ydp.gwtutil.client.debug.log.Logger;
import eu.ydp.gwtutil.client.debug.log.UncaughtExceptionHandler;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PlayerEntryPoint implements EntryPoint {

	/**
	 * Player object
	 */
	private static Player player;
	private static String url1;
	private static JavaScriptObject jsObject;
	private static String node_id;
	private static List<Alternative<String, JavaScriptObject>> extensionsToLoad = new ArrayList<Alternative<String, JavaScriptObject>>();

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		Scripts scripts = PlayerGinjectorFactory.getPlayerGinjector().getScripts();
		scripts.inject(createScriptsLoadedListener());

	}

	private ScriptsLoadedListener createScriptsLoadedListener() {
		return new ScriptsLoadedListener() {

			@Override
			public void onScriptsLoaded() {
				continueLoad();
			}
		};
	}

	private void continueLoad() {
		Logger logger = PlayerGinjectorFactory.getPlayerGinjector().getLogger();
		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler(logger));
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {

				// Define js API
				initJavaScriptAPI();
			}
		});
	}

	/**
	 * Init Javascript API
	 */
	private native void initJavaScriptAPI() /*-{
		// CreatePlayer
		$wnd.empiriaCreatePlayer = function(id) {
			var player = @eu.ydp.empiria.player.client.PlayerEntryPoint::createPlayer(Ljava/lang/String;)(id);
			player.load = function(url) {
				@eu.ydp.empiria.player.client.PlayerEntryPoint::load(Ljava/lang/String;)(url);
			}
			player.loadFromData = function(assessmentData, itemDatas) {
				@eu.ydp.empiria.player.client.PlayerEntryPoint::load(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)(assessmentData, itemDatas);
			}

			// ładowanie rozszerzeń (pluginów i addonów)
			player.loadExtension = function(obj) {
				if (typeof obj == 'object')
					@eu.ydp.empiria.player.client.PlayerEntryPoint::loadExtension(Lcom/google/gwt/core/client/JavaScriptObject;)(obj);
				else if (typeof obj == 'string')
					@eu.ydp.empiria.player.client.PlayerEntryPoint::loadExtension(Ljava/lang/String;)(obj);
			}

			return player;
		}

		$wnd.getPlayerVersion = function() {
			return @eu.ydp.empiria.player.client.version.Version::getVersion()();
		}

		// Call App loaded function
		if (typeof $wnd.empiriaPlayerAppLoaded == 'function') {
			$wnd.empiriaPlayerAppLoaded();
		}
	}-*/;

	/**
	 * createPlayer js interface
	 *
	 * @param node_id
	 */
	public static JavaScriptObject createPlayer(String node_id) {
		PlayerEntryPoint.node_id = node_id;
		jsObject = JavaScriptObject.createFunction();
		return jsObject;
	}

	/**
	 * Load assessment from this url
	 *
	 * @param url
	 */
	public static void load(final String url) {
		GWT.runAsync(new RunAsyncCallback() {
			@Override
			public void onSuccess() {
				doLoad(url);
			}

			@Override
			public void onFailure(Throwable reason) {
				throw new RuntimeException("Error while loading player!", reason);
			}
		});
	}

	private static void doLoad(final String url) {
		if (player == null) {
			player = new Player(node_id, jsObject);
		}
		for (Alternative<String, JavaScriptObject> extAlt : extensionsToLoad) {
			if (extAlt.hasMain()) {
				player.loadExtension(extAlt.getMain());
			} else {
				player.loadExtension(extAlt.getOther());
			}

		}
		player.load(url);
	}

	/**
	 * Load assessment from string data
	 *
	 * @param url
	 */
	public static void load(final JavaScriptObject assessmentData, final JavaScriptObject itemDatas) {
		GWT.runAsync(new RunAsyncCallback() {
			@Override
			public void onSuccess() {
				Document assessmentDoc = XMLParser.parse(decodeXmlDataDocument(assessmentData));
				XmlData assessmentXmlData = new XmlData(assessmentDoc, decodeXmlDataBaseURL(assessmentData));

				JsArray<JavaScriptObject> itemDatasArray = itemDatas.cast();

				XmlData itemXmlDatas[] = new XmlData[itemDatasArray.length()];
				for (int i = 0; i < itemDatasArray.length(); i++) {
					Document itemDoc = XMLParser.parse(decodeXmlDataDocument(itemDatasArray.get(i)));
					itemXmlDatas[i] = new XmlData(itemDoc, decodeXmlDataBaseURL(itemDatasArray.get(i)));
				}
				player = new Player(node_id, jsObject);
				player.load(assessmentXmlData, itemXmlDatas);
			}

			@Override
			public void onFailure(Throwable reason) {
				throw new RuntimeException("Error while loading player!", reason);
			}
		});
	}

	private native static String decodeXmlDataDocument(JavaScriptObject data)/*-{
		if (typeof data.document == 'string')
			return data.document;
		return "";
	}-*/;

	private native static String decodeXmlDataBaseURL(JavaScriptObject data)/*-{
		if (typeof data.baseURL == 'string')
			return data.baseURL;
		return "";
	}-*/;

	public static void loadExtension(JavaScriptObject extension) {
		extensionsToLoad.add(Alternative.<String, JavaScriptObject>createForOther(extension));
	}

	public static void loadExtension(String extension) {
		extensionsToLoad.add(Alternative.<String, JavaScriptObject>createForMain(extension));
	}

}
