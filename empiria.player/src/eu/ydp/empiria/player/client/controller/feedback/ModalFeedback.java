package eu.ydp.empiria.player.client.controller.feedback;

import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;

import eu.ydp.empiria.player.client.controller.events.interaction.FeedbackInteractionEventListner;
import eu.ydp.empiria.player.client.controller.events.interaction.FeedbackInteractionSoundEvent;
import eu.ydp.empiria.player.client.module.registry.InlineBodyGeneratorSocketProvider;

public class ModalFeedback extends Composite implements IItemFeedback {

	public ModalFeedback(Node node, String _baseUrl, InlineBodyGeneratorSocketProvider inlineBodyGeneratorProvider, FeedbackInteractionEventListner feedbackListener){
		
		soundListener = feedbackListener;
		
		baseUrl = _baseUrl;
		
		variable = node.getAttributes().getNamedItem("variableIdentifier").getNodeValue();
		
		if (node.getAttributes().getNamedItem("value") != null)
			value = node.getAttributes().getNamedItem("value").getNodeValue();
		else 
			value = "";

		if (node.getAttributes().getNamedItem("sound") != null)
			soundAddress = node.getAttributes().getNamedItem("sound").getNodeValue();
		else 
			soundAddress = "";

		if (node.getAttributes().getNamedItem("senderIdentifier") != null)
			senderIdentifier = node.getAttributes().getNamedItem("senderIdentifier").getNodeValue();
		else 
			senderIdentifier = "";
		
		
		show = (node.getAttributes().getNamedItem("showHide").getNodeValue().toLowerCase().compareTo("show") == 0);
		
		//contentsHTML = XMLConverter.getDOM((Element)node, new Vector<String>()).getInnerHTML();
		
		contentWidget = inlineBodyGeneratorProvider.getInlineBodyGeneratorSocket().generateInlineBody((Element)node);
		
		contents = new FlowPanel();
		//contents = new InlineHTML();
		contents.setStyleName("qp-feedback-modal-contents");
		//contents.setHTML(contentsHTML);
		contents.add(contentWidget);
		
		container = new FlowPanel();
		container.setStyleName("qp-feedback-modal");
		container.add(contents);
		
		initWidget(container);
		
	}

	private FeedbackInteractionEventListner soundListener;
	
	private String variable;
	private String value;
	//private String contentsHTML;
	private Widget contentWidget;
	private String soundAddress;
	private String senderIdentifier;
	private boolean show;
	
	private String baseUrl;
	
	private FlowPanel container;
	private FlowPanel contents;
	//private InlineHTML contents;
	
	
	public Widget getView(){
		return this;
	}
	
	public void onAttach(){
		super.onAttach();
	}
	
	public String getVariableIdentifier(){
		return variable;
	}
	
	public String getSenderIdentifier(){
		return senderIdentifier;
	}

	public String getValue(){
		return value;
	}
	
	public boolean hasHTMLContent(){
		//return contentsHTML.length() > 0;
		return contentWidget.getElement().toString().length() > 0;
	}
	
	public boolean hasSoundContent(){
		return soundAddress.length() > 0;
	}
	
	public boolean showOnMatch(){
		return show;
	}
	
	public void processSound(){
		String combinedAddress;
		if (soundAddress.startsWith("http://")  ||  soundAddress.startsWith("https://"))
			combinedAddress = soundAddress;
		else
			combinedAddress = baseUrl + soundAddress;
	
		soundListener.onFeedbackSound(new FeedbackInteractionSoundEvent(combinedAddress));
	}

	@Override
	public void hide(ComplexPanel parent) {
		if (parent.getWidgetIndex(getView()) != -1)
			parent.remove(getView());
		
	}

	@Override
	public void show(ComplexPanel parent) {
		if (parent.getWidgetIndex(getView()) == -1)
			parent.add(getView());
		
	}
	
}
