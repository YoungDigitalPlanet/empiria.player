package eu.ydp.empiria.player.client.module.object;

import static eu.ydp.empiria.player.client.util.SourceUtil.getSource;

import java.util.Map;

import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.InlineModuleBase;
import eu.ydp.empiria.player.client.module.audioplayer.AudioPlayerModule;
import eu.ydp.empiria.player.client.module.audioplayer.DefaultAudioPlayerModule;
import eu.ydp.empiria.player.client.module.audioplayer.FlashAudioPlayerModule;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration.MediaType;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.MediaWrappersPair;
import eu.ydp.empiria.player.client.module.object.template.ObjectTemplateParser;
import eu.ydp.empiria.player.client.util.SourceUtil;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.callback.CallbackRecevier;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.util.MediaChecker;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class ObjectModule extends InlineModuleBase implements Factory<ObjectModule> {// NOPMD

	private Widget widget;
	private final boolean flashPlayer = false; // NOPMD
	private Widget moduleView = null;
	private MediaWrapper<?> mediaWrapper = null;
	private final MediaWrapperHandler callbackHandler = new MediaWrapperHandler();
	@Inject
	private EventsBus eventsBus;
	@Inject
	private ObjectTemplateParser<?> parser;
	@Inject
	private Provider<ObjectModule> moduleFactory;
	@Inject Provider<DefaultAudioPlayerModule> defaultAudioPlayerModuleProvider;

	private MediaWrapper<?> fullScreenMediaWrapper;

	private class MediaWrapperHandler implements CallbackRecevier {
		@Override
		public void setCallbackReturnObject(Object object) {
			if (object instanceof MediaWrapper<?>) {
				createMedia((MediaWrapper<?>) object, null);
			}

			if (object instanceof MediaWrappersPair) {
				createMedia(((MediaWrappersPair) object).getDefaultMediaWrapper(), ((MediaWrappersPair) object).getFullScreanMediaWrapper());
			}
		}
	}

	@Override
	public Widget getView() {
		return moduleView;
	}

	public void getMediaWrapper(Element element, boolean defaultTemplate, boolean fullScreenTemplate, String type) {
		int width = XMLUtils.getAttributeAsInt(element, "width");
		int height = XMLUtils.getAttributeAsInt(element, "height");
		if (width == 0 || height == 0) {
			width = 320;
			height = 240;
		}
		String poster = XMLUtils.getAttributeAsString(element, "poster");
		BaseMediaConfiguration bmc = new BaseMediaConfiguration(getSource(element, type), MediaType.valueOf(type.toUpperCase()), poster, height, width,
				defaultTemplate, fullScreenTemplate, getNarrationText(element));// NOPMD
		eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.CREATE_MEDIA_WRAPPER, bmc, callbackHandler));
	}

	public String getNarrationText(Element element) {
		StringBuilder builder = new StringBuilder();
		NodeList nodeList = element.getElementsByTagName("narrationScript");
		for (int x = 0; x < nodeList.getLength(); ++x) {
			if (nodeList.item(x).getNodeType() == Node.ELEMENT_NODE) {
				builder.append(XMLUtils.getText((Element) nodeList.item(x)));
				builder.append(' ');
			}
		}
		return builder.toString();
	}

	/**
	 * tworzy element
	 *
	 * @param element
	 */
	private void createMedia(MediaWrapper<?> mediaWrapper, MediaWrapper<?> fullScreenMediaWrapper) {
		widget = mediaWrapper.getMediaObject();
		this.fullScreenMediaWrapper = fullScreenMediaWrapper;
		this.mediaWrapper = mediaWrapper;
	}

	private void parseTemplate(Element template, Element fullScreenTemplate, FlowPanel parent) {
		parser.setMediaWrapper(mediaWrapper);
		parser.setFullScreenMediaWrapper(fullScreenMediaWrapper);
		parser.setFullScreenTemplate(fullScreenTemplate);
		parser.parse(template, parent);
	}

	@Override
	public void initModule(Element element) {// NOPMD
		String type = XMLUtils.getAttributeAsString(element, "type");
		Element defaultTemplate = null, fullScreenTemplate = null;
		NodeList templateList = element.getElementsByTagName("template");
		for (int x = 0; x < templateList.getLength(); ++x) {
			Element node = (Element) templateList.item(x);
			String templateType = XMLUtils.getAttributeAsString(node, "type", "default");
			if ("default".equalsIgnoreCase(templateType)) {
				defaultTemplate = node;
			} else if ("fullscreen".equalsIgnoreCase(templateType)) {
				fullScreenTemplate = node;
			}
		}
		if (element.getNodeName().equals("audioPlayer")) {
			type = "audio";
		}
		Map<String, String> styles = getModuleSocket().getStyles(element);
		String playerType = styles.get("-player-" + type + "-skin");
		if ("audioPlayer".equals(element.getNodeName()) && ((defaultTemplate == null && !"native".equals(playerType)) || ("old".equals(playerType)))) {
			Map<String, String> sources = getSource(element, type);
			AudioPlayerModule player;
			if (((!MediaChecker.isHtml5Mp3Support() && !SourceUtil.containsOgg(sources)) || !Audio.isSupported()) && UserAgentChecker.isLocal()) {
				player = new FlashAudioPlayerModule();
			} else {
				player = defaultAudioPlayerModuleProvider.get();
			}
			player.initModule(element, getModuleSocket(), getInteractionEventsListener());
			this.moduleView = player.getView();
		} else {
			getMediaWrapper(element, defaultTemplate != null && !"native".equals(playerType), fullScreenTemplate != null, type);
			ObjectModuleView moduleView = new ObjectModuleView();
			String cls = element.getAttribute("class");
			if (cls != null && !"".equals(cls)) {
				moduleView.getContainerPanel().addStyleName(cls);
			}

			if (widget != null) {
				if (defaultTemplate == null || flashPlayer) {
					moduleView.getContainerPanel().add(widget);
				}else{
					parseTemplate(defaultTemplate, fullScreenTemplate, moduleView.getContainerPanel());
				}
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
		return moduleFactory.get();
	}

}
