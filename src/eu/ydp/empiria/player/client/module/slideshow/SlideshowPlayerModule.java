package eu.ydp.empiria.player.client.module.slideshow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.factory.SlideshowPlayerModuleFactory;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.util.events.slideshow.SlideshowPlayerEventType;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class SlideshowPlayerModule extends SimpleModuleBase implements Factory<SlideshowPlayerModule> {

	protected List<Slide> slides;

	protected int currSlideIndex;

	protected Timer timer;

	@Inject
	private SlideshowPresenter presenter;

	@Inject
	private Provider<SlideshowPlayerModule> slideshowPlayerModuleProvider;

	@Inject
	private SlideshowPlayerModuleFactory slideshowPlayerModuleFactory;


	@Override
	public void initModule(Element element) {
		SlideshowMediaHandler mediaHandler = slideshowPlayerModuleFactory.getSlideshowMediaHandler(this, presenter);

		initializeSlideshow(element, getModuleSocket().getInlineBodyGeneratorSocket());

		mediaHandler.addEventHandler(SlideshowPlayerEventType.ON_NEXT);
		mediaHandler.addEventHandler(SlideshowPlayerEventType.ON_PREVIOUS);
		mediaHandler.addEventHandler(SlideshowPlayerEventType.ON_PLAY);
		mediaHandler.addEventHandler(SlideshowPlayerEventType.ON_STOP);

		timer = new Timer() {
			@Override
			public void run() {
				switchNextSlide();
			}
		};

		reset();
	}

	@Override
	public Widget getView() {
		return presenter.asWidget();
	}

	protected void onPlayEvent(){
		if (presenter.isPlayButtonDown()){
			playSlideshow();
		} else {
			pauseSlideshow();
		}
	}

	protected void onStopEvent(){
		presenter.setPlayButtonDown(false);
		stopSlideshow();
	}

	protected void playSlideshow(){
		if (!canScheduleNextSlide()){
			reset();
		}

		if (canScheduleNextSlide()){
			scheduleNextSlide();
		}else{
			presenter.setPlayButtonDown(false);
		}
	}

	protected void pauseSlideshow(){
		timer.cancel();
	}

	protected void stopSlideshow(){
		timer.cancel();
		reset();
	}

	protected void reset(){
		currSlideIndex = 0;
		showSlide(currSlideIndex);
	}

	protected void showSlide(int index){
		if (index < slides.size()){
			presenter.showSlide(index);
		}

		presenter.setEnabledNextButton(index < slides.size() - 1);
		presenter.setEnabledPreviousButton(index > 0);
	}

	protected void switchNextSlide(){
		showNextSlide();
		if (canScheduleNextSlide()){
			scheduleNextSlide();
		}else{
			presenter.setPlayButtonDown(false);
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
		return (currSlideIndex + 1 < slides.size());
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
		return slideshowPlayerModuleProvider.get();
	}

	private void initializeSlideshow(Element element, InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
		Element slideshowElement = XMLUtils.getFirstElementWithTagName(element, "slideshow");
		String className = element.getAttribute("class");

		presenter.setViewClass(className);
		presenter.setBodyGeneratorSocket(inlineBodyGeneratorSocket);

		if (slideshowElement != null){
			Element titleElement = XMLUtils.getFirstElementWithTagName(slideshowElement, "title");

			slides = createSlidesList(slideshowElement);
			presenter.setTitle(titleElement);
			presenter.setSlides(slides);
		}
	}

	private List<Slide> createSlidesList(Element slideshowNode){
		List<Slide> slidesList = new ArrayList<Slide>();
		NodeList slideNodes = slideshowNode.getElementsByTagName("slide");

		for (int i = 0 ; i < slideNodes.getLength() ; i ++){
			if (slideNodes.item(i).getNodeType() == Node.ELEMENT_NODE){
				Slide slide = createSlide((Element)slideNodes.item(i));
				slidesList.add(slide);
			}
		}

		Collections.sort(slidesList, new Comparator<Slide>() {
			@Override
			public int compare(Slide slide1, Slide slide2) {
				return slide1.getStartTime() - slide2.getStartTime();
			}
		});

		return slidesList;
	}

	private Slide createSlide(Element slideNode){
		return new Slide(slideNode);
	}
}
