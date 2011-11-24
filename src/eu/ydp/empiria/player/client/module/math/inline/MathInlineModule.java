package eu.ydp.empiria.player.client.module.math.inline;

import pl.smath.expression.model.Term;
import pl.smath.expression.parser.ExpressionParser;
import pl.smath.expression.parser.ExpressionParserException;
import pl.smath.renderer.renderer.TermRendererException;
import pl.smath.renderer.renderer.TermWidgetFactory;
import pl.smath.renderer.utils.InteractionManager;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;

import eu.ydp.empiria.player.client.module.IUnattachedComponent;

public class MathInlineModule extends Widget implements IUnattachedComponent {

	public MathInlineModule(Element e, boolean autoLoad){
		this(e);
		if (autoLoad)
			onLoad();
	}
	
	public MathInlineModule(Element e){

		term = null;
		owner = new FlowPanel();
		owner.setStyleName("qp-math-inline");
		try {
			for (int n = 0 ; n < e.getChildNodes().getLength() ; n ++){
				if (e.getChildNodes().item(n).getNodeType() == Node.ELEMENT_NODE){
					term = (new ExpressionParser()).processMathML((Element)e.getChildNodes().item(n));
					break;
				}
			}
		} catch (ExpressionParserException e1) {
		}
		
		setElement(owner.getElement());
	}
	
	private Panel owner;
	private Term term;
	
	public void onLoad(){

		InteractionManager im = new InteractionManager(owner);
		try {
			if (term != null){
				TermWidgetFactory twf = new TermWidgetFactory();
				twf.setFontHeight("22px");
				twf.createWidget(term, im);
			}
		} catch (TermRendererException e) {
		}
	}

	@Override
	public void onOwnerAttached() {
		onLoad();
	}
	
}
