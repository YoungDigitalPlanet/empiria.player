package eu.ydp.empiria.player.client.module.slideshow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.components.TwoStateButton;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.ISingleViewSimpleModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;
import eu.ydp.empiria.player.client.util.xml.XMLUtils;

public class SlideshowPlayerModule implements ISingleViewSimpleModule,Factory<SlideshowPlayerModule> {

	protected List<SlideWidget> slides;
	protected int currSlideIndex;

	protected Panel titlePanel;
	protected Panel slidesPanel;
	protected Panel buttonsPanel;
	protected Panel mainPanel;

	protected TwoStateButton playButton;
	protected PushButton stopButton;
	protected PushButton nextButton;
	protected PushButton previousButton;

	protected Timer timer;

	@Override
	public void initModule(Element element, ModuleSocket ms, ModuleInteractionListener mil) {

		titlePanel = new FlowPanel();
		titlePanel.setStyleName("qp-slideshow-title-panel");

		slidesPanel = new FlowPanel();
		slidesPanel.setStyleName("qp-slideshow-slides-panel");

		buttonsPanel = new FlowPanel();
		buttonsPanel.setStyleName("qp-slideshow-button-panel");

		mainPanel = new FlowPanel();
		mainPanel.setStyleName("qp-slideshow");

		String className = element.getAttribute("class");
		if (className != null  &&  !"".equals(className)  &&  getView() != null){
			getView().addStyleName(className);
		}

		playButton = new TwoStateButton("qp-slideshow-button-play", "qp-slideshow-button-pause");
		playButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				onPlayClick();
			}
		});
		stopButton = new PushButton();
		stopButton.setStyleName("qp-slideshow-button-stop");
		stopButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				onStopClick();
			}
		});

		nextButton = new PushButton();
		nextButton.setStyleName("qp-slideshow-button-next");
		nextButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				showNextSlide();
			}
		});

		previousButton = new PushButton();
		previousButton.setStyleName("qp-slideshow-button-previous");
		previousButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				showPreviousSlide();
			}
		});

		buttonsPanel.add(playButton);
		buttonsPanel.add(stopButton);
		buttonsPanel.add(previousButton);
		buttonsPanel.add(nextButton);

		mainPanel.add(titlePanel);
		mainPanel.add(slidesPanel);
		mainPanel.add(buttonsPanel);


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
				Widget titleWidget = ms.getInlineBodyGeneratorSocket().generateInlineBody(titleElement);
				titlePanel.add(titleWidget);
			}

			NodeList slideNodes = element.getElementsByTagName("slide");
			for (int i = 0 ; i < slideNodes.getLength() ; i ++){
				if (slideNodes.item(i).getNodeType() == Node.ELEMENT_NODE){
					SlideWidget slide = new SlideWidget((Element)slideNodes.item(i), ms.getInlineBodyGeneratorSocket());
					slides.add(slide);
				}
			}

			Collections.sort(slides, new Comparator<SlideWidget>() {
				@Override
				public int compare(SlideWidget o1, SlideWidget o2) {
					return o1.getStartTime() - o2.getStartTime();
				}
			});
		}
		initSlideshow();
	}

	@Override
	public Widget getView() {
		return mainPanel;
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
		if (!canScheduleNextSlide())
			initSlideshow();
		if (canScheduleNextSlide())
			scheduleNextSlide();
		else
			playButton.setStateDown(false);
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
		if (index < slides.size())
			slidesPanel.add(slides.get(index));

		previousButton.setEnabled(index > 0);
		nextButton.setEnabled(index < slides.size()-1);
	}

	protected void switchNextSlide(){
		showNextSlide();
		if (canScheduleNextSlide())
			scheduleNextSlide();
		else
			playButton.setStateDown(false);
	}

	protected void showNextSlide(){
		if (currSlideIndex+1 < slides.size())
			currSlideIndex++;
		showSlide(currSlideIndex);
	}

	protected void showPreviousSlide(){
		if (currSlideIndex > 0)
			currSlideIndex--;
		showSlide(currSlideIndex);
	}

	protected boolean canScheduleNextSlide(){
		return (currSlideIndex+1 < slides.size());
	}

	protected void scheduleNextSlide(){
		if (canScheduleNextSlide()){
			int delay = slides.get(currSlideIndex+1).getStartTime() - slides.get(currSlideIndex).getStartTime();
			if (delay <= 0)
				delay = 1;
			timer.schedule(delay);
		}
	}

	@Override
	public SlideshowPlayerModule getNewInstance() {
		return new SlideshowPlayerModule();
	}
}
