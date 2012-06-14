package eu.ydp.empiria.player.client.module.object;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.object.impl.AudioImpl;
import eu.ydp.empiria.player.client.module.object.impl.OggVideoImpl;
import eu.ydp.empiria.player.client.module.object.impl.VideoImpl;
import eu.ydp.empiria.player.client.util.KeyValue;
import eu.ydp.empiria.player.client.util.UserAgentChecker;
import eu.ydp.empiria.player.client.util.UserAgentChecker.MobileUserAgent;
import eu.ydp.empiria.player.client.util.UserAgentChecker.UserAgent;
import eu.ydp.empiria.player.client.util.XMLUtils;

public class ObjectModule extends SimpleModuleBase implements Factory<ObjectModule> {

	protected Widget widget;
	protected MediaBase media;
	ObjectModuleView moduleView = new ObjectModuleView();

	BodyGeneratorSocket bodyGeneratorSocket = null;

	@Override
	public Widget getView() {
		return moduleView;
	}

	/**
	 * Zwraca kolekcje zrodel powiazanych z tym elementem key=url,value=type
	 *
	 * @param element
	 * @return
	 */
	private List<KeyValue<String, String>> getSource(Element element, String mediaType) {
		NodeList sources = element.getElementsByTagName("source");
		String src = XMLUtils.getAttributeAsString(element, "data");
		List<KeyValue<String, String>> retValue = new ArrayList<KeyValue<String, String>>();
		if (sources.getLength() > 0) {
			for (int x = 0; x < sources.getLength(); ++x) {
				src = XMLUtils.getAttributeAsString((Element) sources.item(x), "src");
				String type = XMLUtils.getAttributeAsString((Element) sources.item(x), "type");
				retValue.add(new KeyValue<String, String>(src, type));
			}
		} else {
			String[] type = src.split("[.]");
			retValue.add(new KeyValue<String, String>(src, mediaType + "/" + type[type.length - 1]));
		}
		return retValue;
	}

	/**
	 * tworzy element video
	 *
	 * @param element
	 * @param template
	 *            czy bedzie urzywana skorka
	 */
	private void createVideo(Element element, boolean template) {
		List<KeyValue<String, String>> sources = getSource(element, "video");
		boolean containsOgg = false;
		VideoImpl video = null;
		if (UserAgentChecker.isUserAgent(UserAgent.GECKO1_8) || UserAgentChecker.isMobileUserAgent(MobileUserAgent.FIREFOX)) {
			for (KeyValue<String, String> src : sources) {
				if (src.getValue().matches(".*ogv|.*ogg")) {
					containsOgg = true;
					break;
				}
			}
			if (containsOgg) {
				video = GWT.create(OggVideoImpl.class);
			}
		}
		if (video == null) {
			video = GWT.create(VideoImpl.class);
		}

		video.setShowNativeControls(!template);
		for (KeyValue<String, String> src : sources) {
			video.addSrc(src.getKey(), src.getValue());
		}
		int width = XMLUtils.getAttributeAsInt(element, "width");
		int height = XMLUtils.getAttributeAsInt(element, "height");
		String poster = XMLUtils.getAttributeAsString(element, "poster");
		if (width > 0) {
			video.setWidth(width);
		}
		if (height > 0) {
			video.setHeight(height);
		}
		if (poster.length() > 0) {
			video.setPoster(poster);
		}
		media = video.getMedia();
		widget = video.asWidget();
	}

	/**
	 * tworzy element audio
	 *
	 * @param element
	 * @param template
	 *            czy bedzie urzywana skorka
	 */
	private void createAudio(Element element, boolean template) {
		AudioImpl audio = GWT.create(AudioImpl.class);
		audio.setSource(getSource(element, "audio").get(0).getKey());
		media = audio.getMedia();
		widget = audio.asWidget();
	}

	private void parseTemplate(Element template, FlowPanel parent) {
		TemplateParser parser = new TemplateParser(media);
		parser.parse(template, parent);
	}

	@Override
	public void initModule(Element element) {
		String type = XMLUtils.getAttributeAsString(element, "type");
		Element template = XMLUtils.getFirstElementWithTagName(element, "template");
		if (type.startsWith("video")) {
			createVideo(element, template != null);
		} else {
			createAudio(element, template != null);
		}

		String cls = element.getAttribute("class");
		if (cls != null && !"".equals(cls))
			moduleView.getContainerPanel().addStyleName(cls);

		if (widget != null)
			moduleView.getContainerPanel().add(widget);

		if (template != null && media!=null) {
			parseTemplate(template, moduleView.getContainerPanel());
		}
		NodeList titleNodes = element.getElementsByTagName("title");
		if (titleNodes.getLength() > 0) {
			Widget titleWidget = getModuleSocket().getInlineBodyGeneratorSocket().generateInlineBody(titleNodes.item(0));
			if (titleWidget != null) {
				moduleView.getTitlePanel().add(titleWidget);
			}
		}
		NodeList descriptionNodes = element.getElementsByTagName("description");
		if (descriptionNodes.getLength() > 0) {
			Widget descriptionWidget = getModuleSocket().getInlineBodyGeneratorSocket().generateInlineBody(descriptionNodes.item(0));
			if (descriptionWidget != null) {
				moduleView.getDescriptionPanel().add(descriptionWidget);
			}
		}
	}

	@Override
	public ObjectModule getNewInstance() {
		return new ObjectModule();
	}

}
