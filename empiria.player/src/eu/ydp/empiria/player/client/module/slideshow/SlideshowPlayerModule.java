package eu.ydp.empiria.player.client.module.slideshow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.components.TwoStateButton;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class SlideshowPlayerModule extends SimpleModuleBase implements Factory<SlideshowPlayerModule> {

	interface SlideshowPlayerModuleUiBinder extends UiBinder<Widget, SlideshowPlayerModule>{};
	
	private static SlideshowPlayerModuleUiBinder uiBinder = GWT.create(SlideshowPlayerModuleUiBinder.class);
	
	protected List<SlideWidget> slides;
	
	private StyleNameConstants styleNames = PlayerGinjector.INSTANCE.getStyleNameConstants();
	
	protected int currSlideIndex;

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

	protected Timer timer;
	
	public SlideshowPlayerModule(){
		super();
		uiBinder.createAndBindUi(this);
	}

	@Override
	public void initModule(Element element) {
		String className = element.getAttribute("class");
		if (className != null  &&  !"".equals(className)  &&  getView() != null){
			getView().addStyleName(className);
		}
		
		slides = new ArrayList<SlideWidget>();

		timer = new Timer() {
			@Override
			public void run() {
				switchNextSlide();
			}
		};

		Element slideshowElement = XMLUtils.getFirstElementWithTagName(element, "slideshow");
		
		if (slideshowElement != null){
			Element titleElement = XMLUtils.getFirstElementWithTagName(slideshowElement, "title");
			if (titleElement != null){
				Widget titleWidget = getModuleSocket().getInlineBodyGeneratorSocket().generateInlineBody(titleElement);
				titlePanel.add(titleWidget);
			}

			createAndRegisterSlideWidgets(slideshowElement, slides);
		}
		initSlideshow();
	}

	@Override
	public Widget getView() {
		return mainPanel;
	}
	
	@UiHandler("nextButton")
	protected void onNextButtonClick(ClickEvent event){
		showNextSlide();
	}
	
	@UiHandler("previousButton")
	protected void onPreviousButtonClick(ClickEvent event){
		showPreviousSlide();
	}
	
	@UiHandler("stopButton")
	protected void onStopButtonClick(ClickEvent event){
		onStopClick();
	}
	
	@UiHandler("playButton")
	protected void onPlayPauseButtonClick(ClickEvent event){
		onPlayClick();
	}
	
	@UiFactory
	protected TwoStateButton createPlayPauseButton(){
		return new TwoStateButton(styleNames.QP_SLIDESHOW_BUTTON_PLAY(), styleNames.QP_SLIDESHOW_BUTTON_PAUSE());
	}

	protected void onPlayClick(){
		if (playButton.isStateDown()){
			playSlideshow();
		} else {
			pauseSlideshow();
		}
	}

	protected void onStopClick(){
		playButton.setStateDown(false);
		stopSlideshow();
	}

	protected void playSlideshow(){
		if (!canScheduleNextSlide()){
			initSlideshow();
		}
		
		if (canScheduleNextSlide()){
			scheduleNextSlide();
		}else{
			playButton.setStateDown(false);
		}
	}

	protected void pauseSlideshow(){
		timer.cancel();
	}

	protected void stopSlideshow(){
		timer.cancel();
		initSlideshow();
	}

	protected void initSlideshow(){
		currSlideIndex = 0;
		showSlide(currSlideIndex);
	}

	protected void showSlide(int index){
		slidesPanel.clear();
		if (index < slides.size()){
			slidesPanel.add(slides.get(index));
		}

		previousButton.setEnabled(index > 0);
		nextButton.setEnabled(index < slides.size()-1);
	}

	protected void switchNextSlide(){
		showNextSlide();
		if (canScheduleNextSlide()){
			scheduleNextSlide();
		}else{
			playButton.setStateDown(false);
		}
	}

	protected void showNextSlide(){
		if (currSlideIndex+1 < slides.size()){
			currSlideIndex++;
		}
		
		showSlide(currSlideIndex);
	}

	protected void showPreviousSlide(){
		if (currSlideIndex > 0){
			currSlideIndex--;
		}
		
		showSlide(currSlideIndex);
	}

	protected boolean canScheduleNextSlide(){
		return (currSlideIndex+1 < slides.size());
	}

	protected void scheduleNextSlide(){
		if (canScheduleNextSlide()){
			int delay = slides.get(currSlideIndex+1).getStartTime() - slides.get(currSlideIndex).getStartTime();
			
			if (delay <= 0){
				delay = 1;
			}
			timer.schedule(delay);
		}
	}

	@Override
	public SlideshowPlayerModule getNewInstance() {
		return new SlideshowPlayerModule();
	}
	
	private void createAndRegisterSlideWidgets(Element slideshowNode, List<SlideWidget> slideList){
		NodeList slideNodes = slideshowNode.getElementsByTagName("slide");
		for (int i = 0 ; i < slideNodes.getLength() ; i ++){
			if (slideNodes.item(i).getNodeType() == Node.ELEMENT_NODE){
				SlideWidget slide = createSlideWidget((Element)slideNodes.item(i), getModuleSocket().getInlineBodyGeneratorSocket());
				slideList.add(slide);
			}
		}

		Collections.sort(slideList, new Comparator<SlideWidget>() {
			@Override
			public int compare(SlideWidget widget1, SlideWidget widget2) {
				return widget1.getStartTime() - widget2.getStartTime();
			}
		});
	}
	
	private SlideWidget createSlideWidget(Element slideNode, InlineBodyGeneratorSocket inlineBodyGeneratorSocket){
		return new SlideWidget(slideNode, inlineBodyGeneratorSocket);
	}
}
