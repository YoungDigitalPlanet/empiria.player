package eu.ydp.empiria.player.client.module.mathexpr;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

public class MathExprInlineModule extends Widget{
	
	public MathExprInlineModule(Element e){
		this(e, true);
	}

	public MathExprInlineModule(Element e, boolean autoProcessElement){
		/* */
		contentsElement = Document.get().createElement("script");
		contentsElement.setAttribute("type", "math/mml");
		String contentsHTML = "<math display=\"inline\">" + e.getChildNodes().toString() + "</math>";
		setText(contentsElement, contentsHTML);
		
		contentsWrapper = Document.get().createElement("span");
		contentsWrapper.appendChild(contentsElement);
		
		setElement(contentsWrapper);

		if (autoProcessElement)
			MathJaxProcessor.addMathExprElement(contentsElement);
	}

	public MathExprInlineModule(String math){
		/* */
		contentsElement = Document.get().createElement("script");
		contentsElement.setAttribute("type", "math/mml");
		String contentsHTML = "<math display=\"inline\">" + math + "</math>";
		setText(contentsElement, contentsHTML);
		
		contentsWrapper = Document.get().createElement("span");
		contentsWrapper.appendChild(contentsElement);
		
		setElement(contentsWrapper);

		MathJaxProcessor.addMathExprElement(contentsElement);
	}
	
	private com.google.gwt.dom.client.Element contentsElement;
	private com.google.gwt.dom.client.Element contentsWrapper;
	
	public com.google.gwt.dom.client.Element getContentsElement(){
		return contentsElement;
	}
	
	public native void processMathJax(com.google.gwt.dom.client.Element e)/*-{
		if (typeof $wnd.MathJax !== 'undefined'  &&  $wnd.MathJax != null ){
	  		//alert("level 1");
	  		if (typeof $wnd.MathJax.Hub !== 'undefined'  &&  $wnd.MathJax.Hub != null ){
	  			//alert("level 2");
	  			if (typeof $wnd.MathJax.Hub.Typeset == 'function' ){
	  				//alert("level 3");
	  				$wnd.MathJax.Hub.Typeset(e);
	  			}
	  		}
	  	}
	  }-*/;
	
	public native void setText(com.google.gwt.dom.client.Element e, String text)/*-{
		e.text = text;
	}-*/;
	
}
