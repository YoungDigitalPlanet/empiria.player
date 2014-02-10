package eu.ydp.empiria.player.client.module.slideshow;

import com.google.gwt.xml.client.Element;

import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.xml.XMLUtils;

class Slide {

	private int startTime;

	private String src;

	private final Element narration;

	private final Element title;

	public Slide(Element slideNode) {
		String startTimeString = slideNode.getAttribute("startTime");
		Element sourceNode = XMLUtils.getFirstElementWithTagName(slideNode, "source");

		title = XMLUtils.getFirstElementWithTagName(slideNode, "slideTitle");
		narration = XMLUtils.getFirstElementWithTagName(slideNode, "narration");

		if (sourceNode != null) {
			src = sourceNode.getAttribute("src");
		}

		if (startTimeString != null) {
			startTime = NumberUtils.tryParseInt(startTimeString);
		}
	}

	public int getStartTime() {
		return startTime;
	}

	public String getSrc() {
		return src;
	}

	public Element getTitle() {
		return title;
	}

	public Element getNarration() {
		return narration;
	}

}
