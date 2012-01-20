package eu.ydp.empiria.player.client.module.interaction.inlinechoice;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;

import eu.ydp.empiria.player.client.module.ModuleSocket;

public class InlineChoiceMobileOption {

	protected Widget popupBody;
	protected Widget baseBody;
	protected Panel popupBodyContainer;
	protected Panel baseBodyContainer;
	protected String identifier;
	
	public InlineChoiceMobileOption(Node node, ModuleSocket moduleSocket){
		if (node instanceof Element){
			identifier = ((Element)node).getAttribute("identifier");
			popupBody = moduleSocket.getInlineBodyGeneratorSocket().generateInlineBody(node);
			popupBodyContainer = new FlowPanel();
			popupBodyContainer.add(popupBody);
			baseBody = moduleSocket.getInlineBodyGeneratorSocket().generateInlineBody(node);
			baseBodyContainer = new FlowPanel();
			baseBodyContainer.add(baseBody);
		}
	}
	
	public String getIdentifier(){
		return identifier;
	}

	public Widget getPopupBody(){
		return popupBodyContainer;
	}
	
	public Widget getBaseBody(){
		return baseBodyContainer;
	}
	
	public void addOptionButton(Widget b){
		popupBodyContainer.add(b);
	}
	
}
