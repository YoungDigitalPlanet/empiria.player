package eu.ydp.empiria.player.client.module.match.area;

import java.util.Vector;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.model.feedback.InlineFeedback;
import eu.ydp.empiria.player.client.model.feedback.InlineFeedbackSocket;
import eu.ydp.empiria.player.client.module.CommonsFactory;
import eu.ydp.empiria.player.client.module.ModuleEventsListener;
import eu.ydp.empiria.player.client.module.IUnattachedComponent;
import eu.ydp.empiria.player.client.util.xml.XMLUtils;

public class MatchElement {

	public MatchElement(Element element, MatchSide _side, InlineFeedbackSocket inlineFeedbackSocket, ModuleEventsListener moduleEventsListener){
		side = _side;
		title = "asd";

		identifier = XMLUtils.getAttributeAsString(element, "identifier");
		matchMax = Integer.parseInt(XMLUtils.getAttributeAsString(element, "matchMax"));

		inlineModules = new Vector<IUnattachedComponent>();
		
		Vector<String> ignoredTags = new Vector<String>();
		ignoredTags.add("feedbackInline");
		//com.google.gwt.dom.client.Element dom = XMLConverter.getDOM(element, ignoredTags);
		Widget contentWidget = CommonsFactory.getInlineTextView(element, ignoredTags, inlineModules);
		
		text = new FlowPanel();
		text.add(contentWidget);	
		if (side == MatchSide.LEFT)
			text.setStylePrimaryName("qp-match-element-left-text");
		else if (side == MatchSide.RIGHT)
			text.setStylePrimaryName("qp-match-element-right-text");
		
		textContainer = new FlowPanel();
		if (side == MatchSide.LEFT)
			textContainer.setStylePrimaryName("qp-match-element-left-text-container");
		else if (side == MatchSide.RIGHT)
			textContainer.setStylePrimaryName("qp-match-element-right-text-container");
		textContainer.add(text);
		
		slot = new FlowPanel();
		if (side == MatchSide.LEFT)
			slot.setStylePrimaryName("qp-match-element-left-slot");
		else if (side == MatchSide.RIGHT)
			slot.setStylePrimaryName("qp-match-element-right-slot");
		
		view = new HorizontalPanel();
		view.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		if (side == MatchSide.LEFT)
			view.setStylePrimaryName("qp-match-element-left-container");
		else if (side == MatchSide.RIGHT)
			view.setStylePrimaryName("qp-match-element-right-container");

		if (side == MatchSide.LEFT){
			view.add(textContainer);
			view.add(slot);
		} else if (side == MatchSide.RIGHT){
			view.add(slot);
			view.add(textContainer);
		}
		
		//view.add(labelCover, 0, 0);

		NodeList inlineFeedbackNodes = element.getElementsByTagName("feedbackInline");
		for (int f = 0 ; f < inlineFeedbackNodes.getLength() ; f ++){
			inlineFeedbackSocket.addInlineFeedback(new InlineFeedback(textContainer, inlineFeedbackNodes.item(f), moduleEventsListener));
		}
	}
	
	public MatchSide side;
	public String identifier;
	public int matchMax;
	
	public String title;
	
	public HorizontalPanel view;
	public FlowPanel textContainer;
	public FlowPanel text;
	public FlowPanel slot;

	private Vector<IUnattachedComponent> inlineModules;
	
	private int slotAnchorX;
	private int slotAnchorY;
	
	public void onOwnerAttached(){
		for (IUnattachedComponent uac : inlineModules)
			uac.onOwnerAttached();
	}
	
	public Panel getView(){
		return view;
	}
	
	public void setSlotAnchor(int x, int y){
		slotAnchorX = x;
		slotAnchorY = y;
	}
	
	public boolean isBelongingLocation(int x, int y, int parentX, int parentY){
		
		int vx=0, vy=0, vw=0, vh=0;
		int margin = slot.getOffsetWidth()/2;
		
		if (side == MatchSide.LEFT){
			vx = view.getAbsoluteLeft() - parentX + view.getOffsetWidth() - slot.getOffsetWidth() - margin;
			vy = view.getAbsoluteTop() - parentY;
			vw = slot.getOffsetWidth() + margin*2;
			vh = view.getOffsetHeight();
		} else if (side == MatchSide.RIGHT){
			vx = view.getAbsoluteLeft() - parentX - margin;
			vy = view.getAbsoluteTop() - parentY;
			vw = slot.getOffsetWidth() + margin*2;
			vh = view.getOffsetHeight();
		}
		/*
		int vx = view.getAbsoluteLeft() - parentX;
		int vy = view.getAbsoluteTop() - parentY;
		int vw = view.getOffsetWidth();
		int vh = view.getOffsetHeight();
		
		if (vx == 0) {
			vw += slot.getOffsetWidth();
		}else {
			vx -= slot.getOffsetWidth();
			vw += slot.getOffsetWidth();
		}*/
		
		return (x >= vx &&  y >= vy && x <= vx+vw &&  y <= vy+vh);
	}

	public int getSlotAnchorX(){
		return slotAnchorX;
	}

	public int getSlotAnchorY(){
		return slotAnchorY;
	}
}
