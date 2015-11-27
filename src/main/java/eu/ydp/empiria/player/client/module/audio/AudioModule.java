package eu.ydp.empiria.player.client.module.audio;

import com.google.common.base.Strings;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.media.MediaWrapperCreator;
import eu.ydp.empiria.player.client.module.audioplayer.AudioPlayerModule;
import eu.ydp.empiria.player.client.module.core.base.InlineModuleBase;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.object.ObjectElementReader;
import eu.ydp.empiria.player.client.module.object.template.AudioTemplateParser;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.callback.CallbackReceiver;

import java.util.Map;

import static eu.ydp.empiria.player.client.util.SourceUtil.getSource;

public class AudioModule extends InlineModuleBase {

    private StyleSocket styleSocket;
    private EventsBus eventsBus;
    private MediaWrapperCreator mediaWrapperCreator;
    private AudioModuleView audioModuleView;
    private AudioTemplateParser parser;
    private AudioPlayerModuleFactory audioPlayerModuleFactory;
    private ObjectElementReader elementReader;
    private String AUDIO_SKIN = "-player-audio-skin";
    private Widget moduleView = null;

    @Inject
    public AudioModule (StyleSocket styleSocket,EventsBus eventsBus,MediaWrapperCreator mediaWrapperCreator,
                        AudioModuleView objectModuleView, AudioTemplateParser parser,AudioPlayerModuleFactory audioPlayerModuleFactory,
                        ObjectElementReader elementReader){
        this.styleSocket = styleSocket;
        this.eventsBus = eventsBus;
        this.mediaWrapperCreator = mediaWrapperCreator;
        this.audioModuleView = objectModuleView;
        this.parser = parser;
        this.audioPlayerModuleFactory = audioPlayerModuleFactory;
        this.elementReader = elementReader;
    }

    @Override
    protected void initModule(Element element) {
        Map<String, String> styles = styleSocket.getStyles(element);
        setModuleView(element, styles);
    }

    private void setModuleView(Element element, Map<String, String> styles) {
        final Element defaultTemplate = elementReader.getDefaultTemplate(element);
        String playerSkin = styles.get(AUDIO_SKIN);

        if (isProperSkin(playerSkin, defaultTemplate)) {
            AudioPlayerModule player = getAudioPlayerModule(element, "audio");
            this.moduleView = player.getView();
        } else {
            prepareObjectModuleView(element, defaultTemplate);
            this.moduleView = audioModuleView;
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
        Map<String, String> src = getSource(element, "audio");
        mediaWrapperCreator.createMediaWrapper(src, getCallbackRecevier(defaultTemplate));
    }

    private CallbackReceiver<MediaWrapper<?>> getCallbackRecevier(final Element defaultTemplate) {
        return new CallbackReceiver<MediaWrapper<?>>() {
            @Override
            public void setCallbackReturnObject(MediaWrapper<?> mediaWrapper) {
                parser.setMediaWrapper(mediaWrapper);
                parser.parse(defaultTemplate, audioModuleView.getContainerPanel());
            }
        };
    }

    private void addStyleName(Element element) {
        String cls = element.getAttribute("class");
        if (!Strings.isNullOrEmpty(cls)) {
            audioModuleView.getContainerPanel().addStyleName(cls);
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
