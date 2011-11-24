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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PlayerEntryPoint implements EntryPoint {

	/** Player object */
	public static Player player;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Define js API
		initJavaScriptAPI();
	}

	/**
	 * Init Javascript API
	 */
	private static native void initJavaScriptAPI() /*-{
		// CreatePlayer
		$wnd.empiriaCreatePlayer = function(id) {
		  var player = @eu.ydp.empiria.player.client.PlayerEntryPoint::createPlayer(Ljava/lang/String;)(id);
		  player.load = function(url){
		    @eu.ydp.empiria.player.client.PlayerEntryPoint::load(Ljava/lang/String;)(url);
		  }
		  		  
		  // ³adowanie rozszerzeñ (pluginów i addonów)
		  player.loadExtension = function(obj){
		  	if (typeof obj == 'object')
		  		@eu.ydp.empiria.player.client.PlayerEntryPoint::loadExtension(Lcom/google/gwt/core/client/JavaScriptObject;)(obj);
		  	else if (typeof obj == 'string')
		  		@eu.ydp.empiria.player.client.PlayerEntryPoint::loadExtension(Ljava/lang/String;)(obj);
		  }

		  return player;
		}

		// Call App loaded function
		if(typeof $wnd.empiriaPlayerAppLoaded == 'function') {
		  $wnd.empiriaPlayerAppLoaded();	
		}
	}-*/;

	/**
	 * createPlayer js interface
	 * 
	 * @param node_id
	 */
	public static JavaScriptObject createPlayer(String node_id) {
		player = new Player(node_id);
		return player.getJavaScriptObject();
	}

	/**
	 * Load assessment from this url
	 * 
	 * @param url
	 */
	public static void load(String url) {
		player.load(url);
	}

	public static void loadExtension(JavaScriptObject extension){
		player.loadExtension(extension);
	}
	public static void loadExtension(String extension){
		player.loadExtension(extension);
	}
	
}
