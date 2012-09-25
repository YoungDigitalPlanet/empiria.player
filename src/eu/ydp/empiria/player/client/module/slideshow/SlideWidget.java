package eu.ydp.empiria.player.client.module.slideshow;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

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
	
	public SlideWidget(Slide slide, InlineBodyGeneratorSocket inlineBodyGeneratorSocket){
		initWidget(slideWidgetBinder.createAndBindUi(this));
		
		createImage(slide);
		createTitle(slide, inlineBodyGeneratorSocket);
	}
	
	private void createImage(Slide slide){		
		if (slide.getSrc() != null){
			image.setUrl(slide.getSrc());
		}
	}
	
	private void createTitle(Slide slide, InlineBodyGeneratorSocket inlineBodyGeneratorSocket){
		Widget title = null;
		
		if (slide.getTitle() != null){
			title = inlineBodyGeneratorSocket.generateInlineBody(slide.getTitle());
		}
		
		if (title != null){
			titlePanel.add(title);
		}
	}
}