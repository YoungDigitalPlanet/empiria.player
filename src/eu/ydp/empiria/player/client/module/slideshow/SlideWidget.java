package eu.ydp.empiria.player.client.module.slideshow;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;

public class SlideWidget extends Composite {

	interface SlideWidgetUiBinder extends UiBinder<Widget, SlideWidget>{};
	
	private static SlideWidgetUiBinder slideWidgetBinder = GWT.create(SlideWidgetUiBinder.class);
	
	@UiField
	public Image image;
	
	@UiField
	public FlowPanel slidePanel;
	
	@UiField
	public FlowPanel imgPanel;
	
	@UiField
	public FlowPanel titlePanel;
	
	@UiField
	public FlowPanel narrationPanel;
	
	public SlideWidget(Slide slide, InlineBodyGeneratorSocket inlineBodyGeneratorSocket){
		initWidget(slideWidgetBinder.createAndBindUi(this));
		
		createImage(slide.getSrc());
		createText(slide.getTitle(), inlineBodyGeneratorSocket, titlePanel);
		createText(slide.getNarration(), inlineBodyGeneratorSocket, narrationPanel);
	}
	
	private void createImage(String src){		
		if (src != null){
			image.setUrl(src);
		}
	}
	
	private void createText(Element textNode, InlineBodyGeneratorSocket bodyGenerator, HasWidgets parent){
		Widget textWidget = null;
		
		if (textNode != null){
			textWidget = bodyGenerator.generateInlineWidget(textNode);
		}
		
		if (textWidget != null){
			parent.add(textWidget);
		}
	}
}