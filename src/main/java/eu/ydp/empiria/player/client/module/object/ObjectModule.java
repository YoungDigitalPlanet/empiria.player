package eu.ydp.empiria.player.client.module.object;

import com.google.common.base.Strings;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.core.base.InlineModuleBase;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration.MediaType;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.object.template.ObjectTemplateParser;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;

import java.util.Map;

import static eu.ydp.empiria.player.client.util.SourceUtil.getSource;

public class ObjectModule extends InlineModuleBase {
    private static final int DEFAULT_HEIGHT = 240;
    private static final int DEFAULT_WIDTH = 320;

    private Widget moduleView = null;
    private MediaWrapper<?> mediaWrapper = null;
    private MediaWrapper<?> fullScreenMediaWrapper;

    @Inject
    private EventsBus eventsBus;
    @Inject
    private ObjectTemplateParser parser;
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

    }

    private void parseTemplate(Element template, Element fullScreenTemplate, FlowPanel parent) {
        parser.setMediaWrapper(mediaWrapper);
        parser.setFullScreenMediaWrapper(fullScreenMediaWrapper);
        parser.setFullScreenTemplate(fullScreenTemplate);
        parser.parse(template, parent);
    }

    @Override
    public void initModule(final Element element) {

        final Element defaultTemplate = elementReader.getDefaultTemplate(element);
        final Element fullScreenTemplate = elementReader.getFullscreenTemplate(element);
        createMediaWrapper(element, defaultTemplate, fullScreenTemplate);
        Widget mediaObject = mediaWrapper.getMediaObject();

        ObjectModuleView moduleView = new ObjectModuleView();
        String cls = element.getAttribute("class");
        if (!Strings.isNullOrEmpty(cls)) {
            moduleView.getContainerPanel().addStyleName(cls);
        }

        if (!isNull(mediaObject)) {
            if (isNull(defaultTemplate)) {
                moduleView.getContainerPanel().add(mediaObject);
            } else {
                parseTemplate(defaultTemplate, fullScreenTemplate, moduleView.getContainerPanel());
            }
        }

        Widget titleWidget = getWidgetFromNodeList(element.getElementsByTagName("title"));
        if (!isNull(titleWidget)) moduleView.setTitleWidget(titleWidget);

        Widget descriptionWidget = getWidgetFromNodeList(element.getElementsByTagName("description"));
        if (!isNull(descriptionWidget)) moduleView.getDescriptionPanel().add(descriptionWidget);


        this.moduleView = moduleView;
    }

    private void createMediaWrapper(Element element, Element defaultTemplate, Element fullScreenTemplate) {
        final String type = elementReader.getElementType(element);
        Map<String, String> styles = styleSocket.getStyles(element);
        String playerSkin = styles.get("-player-" + type + "-skin");

        int width = elementReader.getWidthOrDefault(element, DEFAULT_WIDTH);
        int height = elementReader.getHeightOrDefault(element, DEFAULT_HEIGHT);

        String poster = elementReader.getPoster(element);

        final MediaWrapperHandler callbackHandler = new MediaWrapperHandler(this);
        final String narrationText = elementReader.getNarrationText(element);
        BaseMediaConfiguration bmc = new BaseMediaConfiguration(getSource(element, type), MediaType.valueOf(type.toUpperCase()), poster, height, width,
                !isNull(defaultTemplate) && !"native".equals(playerSkin), !isNull(fullScreenTemplate), narrationText);

        eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.CREATE_MEDIA_WRAPPER, bmc, callbackHandler));
    }

    private Widget getWidgetFromNodeList(NodeList nodes) {
        Widget widget = null;
        if (nodes.getLength() > 0) {
            widget = getModuleSocket().getInlineBodyGeneratorSocket().generateInlineBody(nodes.item(0));
            if (!isNull(widget)) {
                return widget;
            }
        }
        return widget;
    }

    private boolean isNull(Object object) {
        return object == null;
    }

}
