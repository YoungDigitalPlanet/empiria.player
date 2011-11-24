package eu.ydp.empiria.player.client.module.mathexpr;

import java.util.Vector;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;

public abstract class MathJaxProcessor {

	private static Vector<Element> mathExprElements = new Vector<Element>();
	private static boolean queueCreated = false;
	private static JavaScriptObject queue;
	
	public static void addMathExprElement(Element e){
		if (!isMathJaxInstalled())
			return;
		
		//queue = createQueue();
		
		if (!queueCreated){
			try{
			queue = createQueue();
			queueCreated = true;
			} catch(Exception exc){
			}
		}
		try {
			mathExprElements.add(e);
		} catch(Exception exc){
			//String s = exc.getMessage();
		}
		
	}
	
	public static void pushAll(){
		for (Element e : mathExprElements){
			pushTypesetToQueue(queue, e);
		}
		mathExprElements.clear();
	}

	private static native JavaScriptObject createQueue()/*-{
		return $wnd.MathJax.CallBack.Queue();
	}-*/;

	private static native JavaScriptObject pushTypesetToQueue(JavaScriptObject queue, Element e)/*-{
		var obj = new Array("Typeset",$wnd.MathJax.Hub,e);
		queue.Push(obj);
	}-*/;

	public static native boolean isMathJaxReady()/*-{
		if ($wnd.MathJax.isReady)
			return true;
		return false;
	}-*/;
	
	public static native boolean isMathJaxInstalled()/*-{
		if (typeof $wnd.MathJax !== 'undefined'  &&  $wnd.MathJax != null ){
	  		if (typeof $wnd.MathJax.Hub !== 'undefined'  &&  $wnd.MathJax.Hub != null ){
	  			if (typeof $wnd.MathJax.Hub.Typeset == 'function' ){
					return true;
	  			}
	  		}
	  	}
	  	return false;
	}-*/;
	
}
