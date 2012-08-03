package eu.ydp.empiria.player.client.module.slideshow;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.util.NumberUtils;
import eu.ydp.empiria.player.client.util.XMLUtils;

public class SlideWidget extends Composite {

	private int startTime;

	private Image img;
	private Widget title;
	private FlowPanel slidePanel;
	private FlowPanel imgPanel;
	private FlowPanel titlePanel;
	
	public SlideWidget(Element slideElement, InlineBodyGeneratorSocket inlineBodyGeneratorSocket){
		String startTimeString = slideElement.getAttribute("startTime");
		if (startTimeString != null)
			startTime = NumberUtils.tryParseInt(startTimeString);
		
		String src = null;
		Element sourceElement = XMLUtils.getFirstElementWithTagName(slideElement, "source");
		if (sourceElement != null)
			src = sourceElement.getAttribute("src");
		img = new Image();
		img.setStyleName("qp-slideshow-slide-image");
		if (src != null)
			img.setUrl(src);
		
		imgPanel = new FlowPanel();
		imgPanel.setStyleName("qp-slideshow-slide-image-panel");
		if (img != null)
			imgPanel.add(img);
		
		Element titleElement = XMLUtils.getFirstElementWithTagName(slideElement, "slideTitle");
		if (titleElement != null)
			title = inlineBodyGeneratorSocket.generateInlineBody(titleElement);
		
		titlePanel = new FlowPanel();
		titlePanel.setStyleName("qp-slideshow-slide-title-panel");
		if (title != null)
			titlePanel.add(title);
		
		slidePanel = new FlowPanel();
		slidePanel.setStyleName("qp-slideshow-slide-panel");
		slidePanel.add(imgPanel);
		slidePanel.add(titlePanel);
		
		initWidget(slidePanel);
	}
	
	public int getStartTime() {
		return startTime;
	}
}