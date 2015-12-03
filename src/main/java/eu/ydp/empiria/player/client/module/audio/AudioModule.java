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

    private static final String AUDIO_SKIN = "-player-audio-skin";
    private StyleSocket styleSocket;
    private EventsBus eventsBus;
    private MediaWrapperCreator mediaWrapperCreator;
    private AudioTemplateParser parser;
    private AudioPlayerModuleFactory audioPlayerModuleFactory;
    private ObjectElementReader elementReader;
    private Widget moduleView = null;

    @Inject
    public AudioModule (StyleSocket styleSocket,EventsBus eventsBus,MediaWrapperCreator mediaWrapperCreator,
                        AudioTemplateParser parser,AudioPlayerModuleFactory audioPlayerModuleFactory,
                        ObjectElementReader elementReader){
        this.styleSocket = styleSocket;
        this.eventsBus = eventsBus;
        this.mediaWrapperCreator = mediaWrapperCreator;
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
            this.moduleView = prepareObjectModuleView(element, defaultTemplate);
        }
    }

    private AudioPlayerModule getAudioPlayerModule(Element element, String type) {
        Map<String, String> sources = getSource(element, type);
        AudioPlayerModule player = audioPlayerModuleFactory.getAudioPlayerModule(sources);
        player.initModule(element, getModuleSocket(), eventsBus);
        return player;
    }

    private Widget prepareObjectModuleView(Element element, Element defaultTemplate) {
        AudioModuleView audioModuleView = new AudioModuleView();
        addStyleName(element, audioModuleView);
        Map<String, String> src = getSource(element, "audio");
        mediaWrapperCreator.createMediaWrapper(src, getCallbackRecevier(defaultTemplate, audioModuleView));
        return audioModuleView;
    }

    private CallbackReceiver<MediaWrapper<?>> getCallbackRecevier(final Element defaultTemplate, final AudioModuleView audioModuleView) {
        return new CallbackReceiver<MediaWrapper<?>>() {
            @Override
            public void setCallbackReturnObject(MediaWrapper<?> mediaWrapper) {
                parser.setMediaWrapper(mediaWrapper);
                parser.parse(defaultTemplate, audioModuleView.getContainerPanel());
            }
        };
    }

    private void addStyleName(Element element, AudioModuleView audioModuleView) {
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
