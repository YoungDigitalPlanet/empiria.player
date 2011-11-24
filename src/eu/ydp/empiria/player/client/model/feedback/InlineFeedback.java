package eu.ydp.empiria.player.client.model.feedback;

import java.util.Vector;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;

import eu.ydp.empiria.player.client.components.MouseEventPanel;
import eu.ydp.empiria.player.client.module.CommonsFactory;
import eu.ydp.empiria.player.client.module.FeedbackModuleInteractionEventsListener;
import eu.ydp.empiria.player.client.module.IUnattachedComponent;

public class InlineFeedback extends PopupPanel implements IItemFeedback {

	public InlineFeedback(Widget _mountingPoint, Node node, FeedbackModuleInteractionEventsListener feedbackListener){
		super(false, false);
		
		soundListener = feedbackListener;
		
		mountingPoint = _mountingPoint;
		shown = false;
		closed = false;
		
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

		if (node.getAttributes().getNamedItem("mark") != null)
			mark = FeedbackMark.fromString( node.getAttributes().getNamedItem("mark").getNodeValue() );
		else 
			mark = FeedbackMark.NONE;
		
		if (node.getAttributes().getNamedItem("align") != null)
			align = InlineFeedbackAlign.fromString(node.getAttributes().getNamedItem("align").getNodeValue());
		else
			align = InlineFeedbackAlign.TOP_LEFT;
		
		if (node.getAttributes().getNamedItem("fadeEffect") != null)
			fadeEffectTime = Integer.parseInt( node.getAttributes().getNamedItem("fadeEffect").getNodeValue() );
		else
			fadeEffectTime = 0;
		
		showHide = (node.getAttributes().getNamedItem("showHide").getNodeValue().toLowerCase().compareTo("show") == 0);

		mathElements = new Vector<IUnattachedComponent>();
		
		containerPanel = new FlowPanel();
		containerPanel.setStyleName("qp-feedback-inline-container");
		
		contentsPanel = new MouseEventPanel();
		contentsPanel.setStyleName("qp-feedback-inline-contents");
		contentsPanel.add(CommonsFactory.getFeedbackView((Element)node, this, mathElements));
		contentsPanel.addMouseUpHandler(new MouseUpHandler() {
			public void onMouseUp(MouseUpEvent event) {
				close();
			}
		});
		containerPanel.add(contentsPanel);
		
		if (mark == FeedbackMark.CORRECT)
			setStyleName("qp-feedback-inline-correct");
		else if (mark == FeedbackMark.WRONG)
			setStyleName("qp-feedback-inline-wrong");
		else
			setStyleName("qp-feedback-inline");
				
		setWidget(containerPanel);
		
		getElement().setId(Document.get().createUniqueId());
	}

	private FeedbackModuleInteractionEventsListener soundListener;

	private String variable;
	private String value;
	private String soundAddress;
	private String senderIdentifier;
	private boolean showHide;
	private FeedbackMark mark;
	private int fadeEffectTime;
	private InlineFeedbackAlign align;

	private String baseUrl;
	
	private Widget mountingPoint;
	private Widget bodyView;
	private FlowPanel containerPanel;
	private MouseEventPanel contentsPanel;
	private Vector<IUnattachedComponent> mathElements;
	
	private boolean shown;
	private boolean closed;

	private final int BODY_MARGIN = 10;
	
	public void onAttach(){
		super.onAttach();
		for (IUnattachedComponent e : mathElements)
			e.onOwnerAttached();
		
		mathElements.clear();

		updatePosition();
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

	public boolean showOnMatch(){
		return showHide;
	}

	public void show(ComplexPanel parent){
		if (!shown && !closed){
			super.show();
			if (fadeEffectTime > 0)
				fadeInJs(getElement(), fadeEffectTime);
			shown = true;
		}
		updatePosition();
	}

	public void hide(ComplexPanel parent){
		if (shown){
			if (fadeEffectTime <= 0){
				super.hide();
			} else {
				final PopupPanel superPanel = this;
				(new Timer() {
					public void run() {
						superPanel.hide();
					}
				}).schedule(fadeEffectTime);
				fadeOutJs(getElement(), fadeEffectTime);
			}
			shown = false;
			closed = false;
		}
	}
	
	public void close(){
		if (shown){
			if (fadeEffectTime <= 0){
				super.hide();
			} else {
				final PopupPanel superPanel = this;
				(new Timer() {
					public void run() {
						superPanel.hide();
					}
				}).schedule(fadeEffectTime);
				fadeOutJs(getElement(), fadeEffectTime);
			}
			closed = true;
		}
	}

	@Override
	public Widget getView() {
		return null;
	}

	@Override
	public boolean hasHTMLContent() {
		return true;
	}

	@Override
	public boolean hasSoundContent() {
		return soundAddress.length() > 0;
	}
	
	public void processSound(){
		String combinedAddress;
		if (soundAddress.startsWith("http://")  ||  soundAddress.startsWith("https://"))
			combinedAddress = soundAddress;
		else
			combinedAddress = baseUrl + soundAddress;
		
		soundListener.onSoundPlay(combinedAddress);
	}

	private void updatePosition(){
		int mountingPointX = 0;
		int mountingPointY = 0;

		if (InlineFeedbackAlign.isLeft(align)){
			mountingPointX = mountingPoint.getAbsoluteLeft();
		} else if (InlineFeedbackAlign.isRight(align)){
			mountingPointX = mountingPoint.getAbsoluteLeft() + mountingPoint.getOffsetWidth() - getOffsetWidth();
		} else {
			mountingPointX = mountingPoint.getAbsoluteLeft() + (mountingPoint.getOffsetWidth() - getOffsetWidth())/2;
		}
		
		if (InlineFeedbackAlign.isTop(align)){
			mountingPointY = mountingPoint.getAbsoluteTop() - getOffsetHeight();
		} else {
			mountingPointY = mountingPoint.getAbsoluteTop() + mountingPoint.getOffsetHeight();
		}
		
		if (bodyView != null){
			if (mountingPointX < bodyView.getAbsoluteLeft() + BODY_MARGIN){
				mountingPointX = bodyView.getAbsoluteLeft() + BODY_MARGIN;
			} else if (mountingPointX + getOffsetWidth() > bodyView.getAbsoluteLeft() + bodyView.getOffsetWidth() - BODY_MARGIN){
				mountingPointX = bodyView.getAbsoluteLeft() + bodyView.getOffsetWidth() - getOffsetWidth() - BODY_MARGIN;
			}
		}
		
		setPopupPosition(mountingPointX, mountingPointY);
	}
	
	public void setBodyContainer(Widget bodyView){
		this.bodyView = bodyView;
	}
	
	public void setBaseUrl(String bUrl){
		baseUrl = bUrl;
	}
		
	public native void opacityto(com.google.gwt.dom.client.Element elm, int v)/*-{
	    elm.style.opacity = v/100; 
	    elm.style.MozOpacity =  v/100; 
	    elm.style.KhtmlOpacity =  v/100; 
	    elm.style.filter=" alpha(opacity ="+v+")";
	}-*/;
	
	public native void fadeInJs(com.google.gwt.dom.client.Element element, int fadeEffectTime)/*-{
		var instance = this;
	    var _this = element;
	    this.@eu.ydp.empiria.player.client.model.feedback.InlineFeedback::opacityto(Lcom/google/gwt/dom/client/Element;I)(_this, 0);
	    var delay = fadeEffectTime;
	    _this.style.zoom = 1; // for ie, set haslayout
	    _this.style.display="block"; 
	    for (i = 0; i <= 100; i+=5) {
	      (function(j) {
	            setTimeout(function() {  
	                  instance.@eu.ydp.empiria.player.client.model.feedback.InlineFeedback::opacityto(Lcom/google/gwt/dom/client/Element;I)(_this, j);
	                  },j*delay/100);
	                 
	        })(i);     
	    }
	}-*/;
	

	public native void fadeOutJs(com.google.gwt.dom.client.Element element, int fadeEffectTime)/*-{
	    var instance = this;
	    var _this = element;
	    instance.@eu.ydp.empiria.player.client.model.feedback.InlineFeedback::opacityto(Lcom/google/gwt/dom/client/Element;I)(_this, 0);
	    var delay = fadeEffectTime;
	    _this.style.zoom = 1; // for ie, set haslayout
	    _this.style.display="block"; 
	    for (i = 0; i <= 100; i+=5) {
	      (function(j) {
	            setTimeout(function() {
	                  j=100-j;
	                  instance.@eu.ydp.empiria.player.client.model.feedback.InlineFeedback::opacityto(Lcom/google/gwt/dom/client/Element;I)(_this, j);
	                  },j*delay/100);
	                 
	        })(i);     
	    }
	}-*/;
}
