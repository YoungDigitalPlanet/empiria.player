package eu.ydp.empiria.player.client.module.object;

import static eu.ydp.empiria.player.client.util.SourceUtil.*;

import java.util.Map;

import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
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
import eu.ydp.empiria.player.client.module.object.template.ObjectTemplateParser;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.SourceUtil;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.util.MediaChecker;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

public class ObjectModule extends InlineModuleBase implements Factory<ObjectModule> {
	private static final int DEFAULT_HEIGHT = 240;
	private static final int DEFAULT_WIDTH = 320;

	private Widget widget;
	private Widget moduleView = null;
	private MediaWrapper<?> mediaWrapper = null;
	private MediaWrapper<?> fullScreenMediaWrapper;

	@Inject
	private EventsBus eventsBus;
	@Inject
	private ObjectTemplateParser parser;
	@Inject
	private Provider<ObjectModule> moduleFactory;
	@Inject
	private Provider<DefaultAudioPlayerModule> defaultAudioPlayerModuleProvider;
	@Inject
	private StyleSocket styleSocket;
	private ObjectElementReader elementReader = new ObjectElementReader();

	@Override
	public Widget getView() {
		return moduleView;
	}

	void createMedia(MediaWrapper<?> mediaWrapper, MediaWrapper<?> fullScreenMediaWrapper) {
		this.mediaWrapper = mediaWrapper;
		this.fullScreenMediaWrapper = fullScreenMediaWrapper;

		this.widget = mediaWrapper.getMediaObject();
	}

	private void parseTemplate(Element template, Element fullScreenTemplate, FlowPanel parent) {
		parser.setMediaWrapper(mediaWrapper);
		parser.setFullScreenMediaWrapper(fullScreenMediaWrapper);
		parser.setFullScreenTemplate(fullScreenTemplate);
		parser.parse(template, parent);
	}

	@Override
	public void initModule(final Element element) {
		final String type = elementReader.getElementType(element);

		final Element defaultTemplate = elementReader.getDefaultTemplate(element);
		final Element fullScreenTemplate = elementReader.getFullscreenTemplate(element);

		Map<String, String> styles = styleSocket.getStyles(element);
		String playerSkin = styles.get("-player-" + type + "-skin");

		if ("audioPlayer".equals(element.getTagName()) && ((defaultTemplate == null && !"native".equals(playerSkin)) || ("old".equals(playerSkin)))) {
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
			int width = elementReader.getWidthOrDefault(element, DEFAULT_WIDTH);
			int height = elementReader.getHeightOrDefault(element, DEFAULT_HEIGHT);
			
			String poster = elementReader.getPoster(element);
			
			final MediaWrapperHandler callbackHandler = new MediaWrapperHandler(this);
			final String narrationText = elementReader.getNarrationText(element);
			BaseMediaConfiguration bmc = new BaseMediaConfiguration(getSource(element, type), MediaType.valueOf(type.toUpperCase()), poster, height, width,
					defaultTemplate != null && !"native".equals(playerSkin), fullScreenTemplate != null, narrationText);
			
			eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.CREATE_MEDIA_WRAPPER, bmc, callbackHandler));
			
			ObjectModuleView moduleView = new ObjectModuleView();
			String cls = element.getAttribute("class");
			if (cls != null && !"".equals(cls)) {
				moduleView.getContainerPanel().addStyleName(cls);
			}

			if (widget != null) {
				if (defaultTemplate == null) {
					moduleView.getContainerPanel().add(widget);
				} else {
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
					moduleView.setTitleWidget(titleWidget);
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
