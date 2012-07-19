package eu.ydp.empiria.player.client.module.object;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.audioplayer.AudioPlayerModule;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration.MediaType;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.object.template.ObjectTemplateParser;
import eu.ydp.empiria.player.client.util.XMLUtils;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.callback.CallbackRecevier;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;

public class ObjectModule extends SimpleModuleBase implements Factory<ObjectModule> {
	private class MediaWrapperHandler implements CallbackRecevier {
		Element element;

		boolean template = false;

		@Override
		public void setCallbackReturnObject(Object o) {
			if (o instanceof MediaWrapper<?>) {
				createMedia(element, template, (MediaWrapper<?>) o);
			}
		}

		public void setElement(Element element) {
			this.element = element;
		}

		public void setTemplate(boolean template) {
			this.template = template;
		}
	}

	protected Widget widget;
	protected MediaBase media;
	boolean flashPlayer = false;
	Widget moduleView = null;
	BodyGeneratorSocket bodyGeneratorSocket = null;
	MediaWrapper<?> mediaWrapper = null;
	MediaWrapperHandler callbackHandler = new MediaWrapperHandler();
	protected EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();

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
	private Map<String, String> getSource(Element element, String mediaType) {
		NodeList sources = element.getElementsByTagName("source");
		String src = XMLUtils.getAttributeAsString(element, "data");
		Map<String, String> retValue = new HashMap<String, String>();
		if (sources.getLength() > 0) {
			for (int x = 0; x < sources.getLength(); ++x) {
				src = XMLUtils.getAttributeAsString((Element) sources.item(x), "src");
				String type = XMLUtils.getAttributeAsString((Element) sources.item(x), "type");
				retValue.put(src, type);
			}
		} else {
			String[] type = src.split("[.]");
			retValue.put(src, mediaType + "/" + type[type.length - 1]);
		}
		return retValue;
	}

	public void getMediaWrapper(Element element, boolean template, String type) {
		callbackHandler.setElement(element);
		callbackHandler.setTemplate(template);
		int width = XMLUtils.getAttributeAsInt(element, "width");
		int height = XMLUtils.getAttributeAsInt(element, "height");
		if (width == 0 || height == 0) {
			width = 320;
			height = 240;
		}
		String poster = XMLUtils.getAttributeAsString(element, "poster");
		BaseMediaConfiguration bmc = new BaseMediaConfiguration(getSource(element, type), MediaType.valueOf(type.toUpperCase()), poster, height, width, template);
		eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.CREATE_MEDIA_WRAPPER, callbackHandler, bmc));
	}

	/**
	 * tworzy element
	 *
	 * @param element
	 * @param template
	 *            czy bedzie urzywana skorka
	 */
	private void createMedia(Element element, boolean template, MediaWrapper<?> mediaWrapper) {
		widget = mediaWrapper.getMediaObject();
		this.mediaWrapper = mediaWrapper;
	}

	private void parseTemplate(Element template, FlowPanel parent) {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		ObjectTemplateParser<?> parser = new ObjectTemplateParser(mediaWrapper);
		parser.parse(template, parent);
	}

	@Override
	public void initModule(Element element) {
		String type = XMLUtils.getAttributeAsString(element, "type");
		Element template = XMLUtils.getFirstElementWithTagName(element, "template");
		Map<String, String> styles = getModuleSocket().getStyles(element);
		String playerType = styles.get("-player-" + type + "-skin");
		if ("audioPlayer".equals(element.getNodeName()) && ((template == null && !"native".equals(playerType)) || ("old".equals(playerType)))) {
			AudioPlayerModule player = GWT.create(AudioPlayerModule.class);
			player.initModule(element, getModuleSocket(), getInteractionEventsListener());
			this.moduleView = player.getView();
		} else {
			if(element.getNodeName().equals("audioPlayer")){
				type="audio";
			}
			getMediaWrapper(element, template != null && !"native".equals(playerType), type);
			ObjectModuleView moduleView = new ObjectModuleView();
			String cls = element.getAttribute("class");
			if (cls != null && !"".equals(cls)) {
				moduleView.getContainerPanel().addStyleName(cls);
			}
			if (widget != null){
				moduleView.getContainerPanel().add(widget);
			}

			if (template != null && widget != null && !flashPlayer) {
				parseTemplate(template, moduleView.getContainerPanel());
			}

			if (mediaWrapper != null) {
				eventsBus.fireEvent(new MediaEvent(MediaEventTypes.MEDIA_ATTACHED, mediaWrapper));
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
			this.moduleView = moduleView;
		}
	}

	@Override
	public ObjectModule getNewInstance() {
		return new ObjectModule();
	}

}
