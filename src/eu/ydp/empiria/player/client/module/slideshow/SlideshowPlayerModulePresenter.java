package eu.ydp.empiria.player.client.module.slideshow;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.google.common.base.Strings;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.components.TwoStateButton;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.slideshow.SlideshowPlayerEvent;
import eu.ydp.empiria.player.client.util.events.slideshow.SlideshowPlayerEventType;
import eu.ydp.gwtutil.client.ui.button.CustomPushButton;

public class SlideshowPlayerModulePresenter implements SlideshowPresenter {

	@UiTemplate("SlideshowPlayerModule.ui.xml")
	interface SimulationModuleUiBinder extends UiBinder<Widget, SlideshowPlayerModulePresenter> {
	};

	private final SimulationModuleUiBinder uiBinder = GWT.create(SimulationModuleUiBinder.class);

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
	protected CustomPushButton stopButton;

	@UiField
	protected CustomPushButton nextButton;

	@UiField
	protected CustomPushButton previousButton;

	protected List<SlideWidget> slideWidgetsList;

	@Inject
	private EventsBus eventBus;

	@Inject
	private StyleNameConstants styleNames;

	private InlineBodyGeneratorSocket bodyGenerator;

	@PostConstruct
	public void postConstruct() {
		uiBinder.createAndBindUi(this);
	}

	@Override
	public void setTitle(Element titleNode) {
		if (titleNode != null) {
			Widget titleWidget = bodyGenerator.generateInlineBody(titleNode);
			titlePanel.clear();
			titlePanel.add(titleWidget);
		}
	}

	@Override
	public void setBodyGeneratorSocket(InlineBodyGeneratorSocket socket) {
		bodyGenerator = socket;
	}

	@Override
	public Widget asWidget() {
		return mainPanel;
	}

	@Override
	public void setEnabledNextButton(boolean enabled) {
		nextButton.setEnabled(enabled);
	}

	@Override
	public void setEnabledPreviousButton(boolean enabled) {
		previousButton.setEnabled(enabled);
	}

	@Override
	public void setPlayButtonDown(boolean down) {
		playButton.setStateDown(down);
	}

	@Override
	public boolean isPlayButtonDown() {
		return playButton.isStateDown();
	}

	@Override
	public void setViewClass(String className) {
		if (!Strings.isNullOrEmpty(className) && mainPanel != null) {
			mainPanel.addStyleName(className);
		}
	}

	@Override
	public void setSlides(List<Slide> slidesList) {
		if (slideWidgetsList == null) {
			slideWidgetsList = new ArrayList<SlideWidget>();
		}

		slideWidgetsList.clear();

		for (Slide slide : slidesList) {
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
	protected TwoStateButton createPlayPauseButton() {
		return new TwoStateButton(styleNames.QP_SLIDESHOW_BUTTON_PLAY(), styleNames.QP_SLIDESHOW_BUTTON_PAUSE());
	}

	@UiHandler("nextButton")
	protected void onNextButtonClick(ClickEvent event) {
		dispatchEvent(SlideshowPlayerEventType.ON_NEXT);
	}

	@UiHandler("previousButton")
	protected void onPreviousButtonClick(ClickEvent event) {
		dispatchEvent(SlideshowPlayerEventType.ON_PREVIOUS);
	}

	@UiHandler("playButton")
	protected void onPlayButtonClick(ClickEvent event) {
		dispatchEvent(SlideshowPlayerEventType.ON_PLAY);
	}

	@UiHandler("stopButton")
	protected void onStopButtonClick(ClickEvent event) {
		dispatchEvent(SlideshowPlayerEventType.ON_STOP);
	}

	private SlideWidget createSlideWidget(Slide slide, InlineBodyGeneratorSocket bodyGenerator) {
		return new SlideWidget(slide, bodyGenerator);
	}

	private SlideWidget getSlideWidget(int slideIndex) {
		return slideWidgetsList.get(slideIndex);
	}

	private void dispatchEvent(SlideshowPlayerEventType type) {
		eventBus.fireEventFromSource(new SlideshowPlayerEvent(type), this);
	}

}
