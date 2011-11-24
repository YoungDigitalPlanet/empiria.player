package eu.ydp.empiria.player.client.module.dragdrop;

import java.util.Vector;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.module.CommonsFactory;
import eu.ydp.empiria.player.client.module.IUnattachedComponent;

public class DragElement extends Composite {

	private String identifier;

	private AbsolutePanel containerPanel;
	private FlowPanel contentsPanel;
	private FlowPanel coverPanel;
	private String coverId;
	
	
	public DragElement(Element element, Vector<IUnattachedComponent> inlineModules){
		this.identifier = element.getAttribute("identifier");
		
		containerPanel = new AbsolutePanel();
		containerPanel.setStyleName("qp-dragdrop-element-container");
		
		contentsPanel = new FlowPanel();
		contentsPanel.setStyleName("qp-dragdrop-element-contents");
		containerPanel.add(contentsPanel, 0, 0);
		
		coverPanel = new FlowPanel();
		coverPanel.setStyleName("qp-dragdrop-element-cover");
		coverId = Document.get().createUniqueId();
		coverPanel.getElement().setId(coverId);
		containerPanel.add(coverPanel, 0, 0);

		Vector<String> ignoredTags = new Vector<String>();
		ignoredTags.add("feedbackInline");
		
		Widget contentsWidget = CommonsFactory.getInlineTextView(element, ignoredTags, inlineModules);
		contentsPanel.add(contentsWidget);
		
		initWidget(containerPanel);
	}
	
	public String getCoverId(){
		return coverId;
	}
	
	public String getIdentifier(){
		return identifier;
	}
}
