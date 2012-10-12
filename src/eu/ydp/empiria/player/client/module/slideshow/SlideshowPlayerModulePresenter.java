package eu.ydp.empiria.player.client.module.slideshow;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.components.TwoStateButton;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.module.slideshow.SlideshowPlayerModule.Presenter;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.slideshow.SlideshowPlayerEvent;
import eu.ydp.empiria.player.client.util.events.slideshow.SlideshowPlayerEventType;

public class SlideshowPlayerModulePresenter implements Presenter{
	
	@UiTemplate("SlideshowPlayerModule.ui.xml")
	interface SimulationModuleUiBinder extends UiBinder<Widget, SlideshowPlayerModulePresenter>{};
	
	private final SimulationModuleUiBinder uiBinder = GWT.create(SimulationModuleUiBinder.class);
	
	private final EventsBus eventBus = PlayerGinjector.INSTANCE.getEventsBus();
	
	@UiField
	protected Panel titlePanel;
	
	@UiField
	protected Panel slidesPanel;
	
	@UiField
	protected Panel buttonsPanel;
	
	@UiField
	protected Panel mainPanel;

	@UiField
	protected TwoStateButton playButton;
	
	@UiField
	protected PushButton stopButton;
	
	@UiField
	protected PushButton nextButton;
	
	@UiField
	protected PushButton previousButton;
	
	protected List<SlideWidget> slideWidgetsList;
	
	private final StyleNameConstants styleNames = PlayerGinjector.INSTANCE.getStyleNameConstants();
	
	private InlineBodyGeneratorSocket bodyGenerator;
	
	public SlideshowPlayerModulePresenter(){
		super();
		uiBinder.createAndBindUi(this);
	}
	
	public void setTitle(Element titleNode){
		if (titleNode != null){
			Widget titleWidget = bodyGenerator.generateInlineBody(titleNode);
			titlePanel.clear();
			titlePanel.add(titleWidget);
		}
	}
	
	public void setBodyGeneratorSocket(InlineBodyGeneratorSocket socket){
		bodyGenerator = socket;
	}
	
	public Widget getView(){
		return mainPanel;
	}
	
	public void setEnabledNextButton(boolean enabled){
		nextButton.setEnabled(enabled);
	}
	
	public void setEnabledPreviousButton(boolean enabled){
		previousButton.setEnabled(enabled);
	}
	
	public void setPlayButtonDown(boolean down){
		playButton.setStateDown(down);
	}
	
	public boolean isPlayButtonDown(){
		return playButton.isStateDown();
	}
	
	@Override
	public void setViewClass(String className) {
		if (className != null  &&  !"".equals(className)  &&  mainPanel != null){
			mainPanel.addStyleName(className);
		}	
	}
	
	@Override
	public void setSlides(List<Slide> slidesList) {
		if(slideWidgetsList == null){
			slideWidgetsList = new ArrayList<SlideWidget>();
		}
		
		slideWidgetsList.clear();
		
		for(Slide slide: slidesList){
			SlideWidget slideWidget = createSlideWidget(slide, bodyGenerator);
			slideWidgetsList.add(slideWidget);
		}
	}
	
	@Override
	public void showSlide(int slideIndex) {
		SlideWidget slideWidget = getSlideWidget(slideIndex);	
		
		slidesPanel.clear();
		slidesPanel.add(slideWidget);
	}
	
	@UiFactory
	protected TwoStateButton createPlayPauseButton(){
		return new TwoStateButton(styleNames.QP_SLIDESHOW_BUTTON_PLAY(), styleNames.QP_SLIDESHOW_BUTTON_PAUSE());
	}
	
	@UiHandler("nextButton")
	protected void onNextButtonClick(ClickEvent event){
		dispatchEvent(SlideshowPlayerEventType.ON_NEXT);
	}
	
	@UiHandler("previousButton")
	protected void onPreviousButtonClick(ClickEvent event){
		dispatchEvent(SlideshowPlayerEventType.ON_PREVIOUS);
	}
	
	@UiHandler("playButton")
	protected void onPlayButtonClick(ClickEvent event){
		dispatchEvent(SlideshowPlayerEventType.ON_PLAY);
	}
	
	@UiHandler("stopButton")
	protected void onStopButtonClick(ClickEvent event){
		dispatchEvent(SlideshowPlayerEventType.ON_STOP);
	}
	
	private SlideWidget createSlideWidget(Slide slide, InlineBodyGeneratorSocket bodyGenerator){
		return new SlideWidget(slide, bodyGenerator);
	}
	
	private SlideWidget getSlideWidget(int slideIndex){
		return slideWidgetsList.get(slideIndex);
	}
	
	private void dispatchEvent(SlideshowPlayerEventType type){
		eventBus.fireEventFromSource(new SlideshowPlayerEvent(type), this);
	}
	
}
