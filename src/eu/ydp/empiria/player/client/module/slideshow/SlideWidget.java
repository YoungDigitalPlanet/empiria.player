package eu.ydp.empiria.player.client.module.slideshow;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class SlideWidget extends Composite {

	interface SlideWidgetUiBinder extends UiBinder<Widget, SlideWidget>{};
	
	private static SlideWidgetUiBinder slideWidgetBinder = GWT.create(SlideWidgetUiBinder.class);
	
	private int startTime;
	
	@UiField
	public Image image;
	
	@UiField
	public FlowPanel slidePanel;
	
	@UiField
	public FlowPanel imgPanel;
	
	@UiField
	public FlowPanel titlePanel;
	
	public SlideWidget(Element slideElement, InlineBodyGeneratorSocket inlineBodyGeneratorSocket){
		initWidget(slideWidgetBinder.createAndBindUi(this));
		
		String startTimeString = slideElement.getAttribute("startTime");
		
		if (startTimeString != null){
			startTime = NumberUtils.tryParseInt(startTimeString);
		}
		
		createImage(slideElement);
		createTitle(slideElement, inlineBodyGeneratorSocket);
	}
	
	public int getStartTime() {
		return startTime;
	}
	
	private void createImage(Element slideNode){
		String src = null;
		Element sourceElement = XMLUtils.getFirstElementWithTagName(slideNode, "source");
		
		if (sourceElement != null){
			src = sourceElement.getAttribute("src");
			image.setUrl(src);
		}
	}
	
	private void createTitle(Element slideNode, InlineBodyGeneratorSocket inlineBodyGeneratorSocket){
		Element titleElement = XMLUtils.getFirstElementWithTagName(slideNode, "slideTitle");
		Widget title = null;
		
		if (titleElement != null){
			title = inlineBodyGeneratorSocket.generateInlineBody(titleElement);
		}
		
		if (title != null){
			titlePanel.add(title);
		}
	}
}