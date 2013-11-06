package eu.ydp.empiria.player.client.module.video.model;

import com.google.gwt.xml.client.Element;

import eu.ydp.gwtutil.client.xml.XMLUtils;

public class VideoXmlParser {

	public VideoModel parse(Element element) {

		String src = XMLUtils.getAttributeAsString(element, "src");
		Integer height = XMLUtils.getAttributeAsInt(element, "height");
		Integer width = XMLUtils.getAttributeAsInt(element, "width");
		String poster = XMLUtils.getAttributeAsString(element, "poster");

		VideoModel videoJsModel = new VideoModel();
		videoJsModel.setWidth(width);
		videoJsModel.setHeight(height);
		videoJsModel.setSrc(src);
		videoJsModel.setPoster(poster);

		return videoJsModel;
	}
}
