package eu.ydp.empiria.player.client.module.identification;

import java.util.Vector;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.module.CommonsFactory;
import eu.ydp.empiria.player.client.module.IUnattachedComponent;
import eu.ydp.empiria.player.client.util.xml.XMLUtils;

public class SelectableChoice extends Composite {

	public SelectableChoice(Element element){
		
		identifier = XMLUtils.getAttributeAsString(element, "identifier");
		
		inlineModules = new Vector<IUnattachedComponent>();
		
		Vector<String> ignoredTags = new Vector<String>();
		ignoredTags.add("feedbackInline");
		Widget contentWidget = CommonsFactory.getInlineTextView(element, ignoredTags, inlineModules);

		coverId = Document.get().createUniqueId();
		
		cover = new AbsolutePanel();
		cover.getElement().setId(coverId);
		cover.setStyleName("qp-identification-option-cover");

		container = new AbsolutePanel();
		container.setStyleName("qp-identification-option-container");
		container.add(contentWidget, 0, 0);
		container.add(cover, 0, 0);
		
		panel = new FlowPanel();
		panel.setStyleName("qp-identification-option");
		panel.add(container);
		
		initWidget(panel);
		
		setSelected(false);
	}
	
	private boolean selected;
	private String identifier;
	
	private AbsolutePanel container;
	private AbsolutePanel cover;
	private FlowPanel panel;
	
	private String coverId;

	private Vector<IUnattachedComponent> inlineModules;
	
	public void onOwnerAttached(){
		for (IUnattachedComponent uac : inlineModules)
			uac.onOwnerAttached();
	}
	
	public String getIdentifier(){
		return identifier;
	}
	
	public String getId(){
		return coverId;
	}
	
	public boolean getSelected(){
		return selected;
	}
	public void setSelected(boolean sel){
		selected = sel;
		updatePanelStyleName();
	}
	
	public void markAnswers(boolean mark, boolean correct){

		if (mark){
			if (selected){
				if (correct)
					panel.setStyleName("qp-identification-option-selected-correct");
				else
					panel.setStyleName("qp-identification-option-selected-wrong");
			} else {
				if (correct)
					panel.setStyleName("qp-identification-option-notselected-wrong");
				else
					panel.setStyleName("qp-identification-option-notselected-correct");
			}
		} else {
			updatePanelStyleName();
		}
	}
	
	private void updatePanelStyleName(){

		if (selected){
			panel.setStyleName("qp-identification-option-selected");
		} else {
			panel.setStyleName("qp-identification-option");
		}
	}
}
