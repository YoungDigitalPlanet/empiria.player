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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.RootPanel;

import eu.ydp.empiria.player.client.controller.body.IPlayerContainersAccessor;
import eu.ydp.empiria.player.client.controller.communication.DisplayOptions;
import eu.ydp.empiria.player.client.controller.communication.FlowOptions;
import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngine;
import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import eu.ydp.empiria.player.client.version.Version;
import eu.ydp.empiria.player.client.view.ViewEngine;
/**
 * Main class with player API
 * @author Krzysztof Langner
 */
public class Player {

  /** JavaScript object representing this java object */
  private final JavaScriptObject	jsObject;

  /** Delivery engine do manage the assessment content */
  public DeliveryEngine deliveryEngine;

  /** View engine maintains the view tasks */
  private final ViewEngine viewEngine;

  private IPlayerContainersAccessor accessor;

  {
	 logVersion();
  }

	/**
	 * constructor
	 * @param id
	 */
	public Player(String id){
		this.jsObject = JavaScriptObject.createFunction();
		PlayerGinjector injector = PlayerGinjector.INSTANCE;
		viewEngine = injector.getViewEngine();
		try {
			RootPanel.get(id);
		} catch (Exception e){}
		RootPanel root = RootPanel.get(id);
		viewEngine.mountView(root);
		getAccessor().setPlayerContainer(root);
		deliveryEngine = injector.getDeliveryEngine();
		deliveryEngine.init(jsObject);
	}

	public Player(ComplexPanel container){
		this.jsObject = JavaScriptObject.createFunction();
		PlayerGinjector injector = PlayerGinjector.INSTANCE;
		getAccessor().setPlayerContainer(container);
		viewEngine = injector.getViewEngine();
		viewEngine.mountView(container);
		deliveryEngine = injector.getDeliveryEngine();
		deliveryEngine.init(jsObject);
	}

	public void loadExtension(JavaScriptObject extension){
		deliveryEngine.loadExtension(extension);
	}

	public void loadExtension(String extension){
		deliveryEngine.loadExtension(extension);
	}

	public void load(String url){
		deliveryEngine.load(url);
	}

	public void load(XmlData assessmentData, XmlData[] itemsData){
		deliveryEngine.load(assessmentData, itemsData);
	}

	 /**
	 * @return js object representing this player
	 */
	public JavaScriptObject getJavaScriptObject(){
	  return jsObject;
  }

	public void setFlowOptions(FlowOptions o){
		deliveryEngine.setFlowOptions(o);
	}

	public void setDisplayOptions(DisplayOptions o){
		deliveryEngine.setDisplayOptions(o);
	}

	public String getEngineMode(){
  		return deliveryEngine.getEngineMode();
	}

	private void logVersion(){
		 String version = Version.getVersion();
		 String versionMessage = "EmpiriaPlayer ver. " + version;
		 log(versionMessage);
		 System.out.println(versionMessage);
	}

	private native void log(String message)/*-{
		if (typeof console == 'object')
			console.log(message);
	 }-*/;

	private IPlayerContainersAccessor getAccessor() {
		if (accessor == null){
			accessor = PlayerGinjector.INSTANCE.getPlayerContainersAccessor();
		}
		return accessor;
	}	
}
