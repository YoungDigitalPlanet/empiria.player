package eu.ydp.empiria.player.client.module.audio;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.media.MediaWrapperCreator;
import eu.ydp.empiria.player.client.module.audioplayer.AudioPlayerModule;
import eu.ydp.empiria.player.client.module.core.base.InlineModuleBase;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.object.ObjectElementReader;
import eu.ydp.empiria.player.client.module.object.ObjectModuleView;
import eu.ydp.empiria.player.client.module.object.template.AudioTemplateParser;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.SourceUtil;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.callback.CallbackReceiver;

import java.util.Map;

import static eu.ydp.empiria.player.client.util.SourceUtil.getSource;

public class AudioModule extends InlineModuleBase {
    @Inject
    private StyleSocket styleSocket;
    @Inject
    private EventsBus eventsBus;
    @Inject
    private MediaWrapperCreator mediaWrapperCreator;
    @Inject
    private ObjectModuleView objectModuleView;
    @Inject
    private AudioTemplateParser parser;
    @Inject
    private AudioPlayerModuleFactory audioPlayerModuleFactory;

    private Widget moduleView = null;
    private ObjectElementReader elementReader = new ObjectElementReader();

    @Override
    protected void initModule(Element element) {
        Map<String, String> styles = styleSocket.getStyles(element);
        setModuleView(element, styles);
    }

    private void setModuleView(Element element, Map<String, String> styles) {
        final String type = elementReader.getElementType(element);
        final Element defaultTemplate = elementReader.getDefaultTemplate(element);
        String playerSkin = styles.get("-player-" + type + "-skin");

        if (isProperSkin(playerSkin, defaultTemplate)) {
            AudioPlayerModule player = getAudioPlayerModule(element, type);
            this.moduleView = player.getView();
        } else {
            prepareObjectModuleView(element, defaultTemplate);
            this.moduleView = objectModuleView;
        }
    }

    private AudioPlayerModule getAudioPlayerModule(Element element, String type) {
        Map<String, String> sources = getSource(element, type);
        AudioPlayerModule player = audioPlayerModuleFactory.getAudioPlayerModule(sources);
        player.initModule(element, getModuleSocket(), eventsBus);
        return player;
    }

    private void prepareObjectModuleView(Element element, Element defaultTemplate) {
        addStyleName(element);
        Map<String, String> src = SourceUtil.getSource(element, "audio");
        mediaWrapperCreator.createMediaWrapper(src, getCallbackRecevier(defaultTemplate));
    }

    private CallbackReceiver<MediaWrapper<?>> getCallbackRecevier(final Element defaultTemplate) {
        return new CallbackReceiver<MediaWrapper<?>>() {
            @Override
            public void setCallbackReturnObject(MediaWrapper<?> mediaWrapper) {
                parser.setMediaWrapper(mediaWrapper);
                parser.parse(defaultTemplate, objectModuleView.getContainerPanel());
            }
        };
    }

    private void addStyleName(Element element) {
        String cls = element.getAttribute("class");
        if (cls != null && !"".equals(cls)) {
            objectModuleView.getContainerPanel().addStyleName(cls);
        }
    }

    private boolean isProperSkin(String playerSkin, Element defaultTemplate) {
        return (defaultTemplate == null && !isSkinNative(playerSkin)) || isSkinOld(playerSkin);
    }

    private boolean isSkinNative(String playerSkin) {
        return "native".equals(playerSkin);
    }

    private boolean isSkinOld(String playerSkin) {
        return "old".equals(playerSkin);
    }

    @Override
    public Widget getView() {
        return moduleView;
    }
}
