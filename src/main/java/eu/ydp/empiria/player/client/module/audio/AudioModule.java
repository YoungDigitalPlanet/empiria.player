package eu.ydp.empiria.player.client.module.audio;

import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.media.MediaWrapperCreator;
import eu.ydp.empiria.player.client.module.audioplayer.AudioPlayerModule;
import eu.ydp.empiria.player.client.module.audioplayer.DefaultAudioPlayerModule;
import eu.ydp.empiria.player.client.module.audioplayer.FlashAudioPlayerModule;
import eu.ydp.empiria.player.client.module.core.base.InlineModuleBase;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.object.ObjectElementReader;
import eu.ydp.empiria.player.client.module.object.ObjectModuleView;
import eu.ydp.empiria.player.client.module.object.template.AudioTemplateParser;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.SourceUtil;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.callback.CallbackReceiver;
import eu.ydp.gwtutil.client.util.MediaChecker;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

import java.util.Map;

import static eu.ydp.empiria.player.client.util.SourceUtil.getSource;

public class AudioModule extends InlineModuleBase {
    @Inject
    private StyleSocket styleSocket;
    @Inject
    private EventsBus eventsBus;
    @Inject
    private MediaChecker mediaChecker;
    @Inject
    private Provider<DefaultAudioPlayerModule> defaultAudioPlayerModuleProvider;
    @Inject
    private Provider<FlashAudioPlayerModule> flashAudioPlayerModuleProvider;
    @Inject
    private MediaWrapperCreator mediaWrapperCreator;
    @Inject
    private ObjectModuleView objectModuleView;
    @Inject
    private AudioTemplateParser parser;

    private Widget moduleView = null;
    private ObjectElementReader elementReader = new ObjectElementReader();

    @Override
    protected void initModule(Element element) {
        final String type = elementReader.getElementType(element);
        final Element defaultTemplate = elementReader.getDefaultTemplate(element);

        Map<String, String> styles = styleSocket.getStyles(element);
        String playerSkin = styles.get("-player-" + type + "-skin");

        if ("audioPlayer".equals(element.getTagName()) && ((defaultTemplate == null && !"native".equals(playerSkin)) || ("old".equals(playerSkin)))) {
            Map<String, String> sources = getSource(element, type);
            AudioPlayerModule player;

            if (((!mediaChecker.isHtml5Mp3Supported() && !SourceUtil.containsOgg(sources)) || !Audio.isSupported()) && UserAgentChecker.isLocal()) {
                player = flashAudioPlayerModuleProvider.get();
            } else {
                player = defaultAudioPlayerModuleProvider.get();
            }

            player.initModule(element, getModuleSocket(), eventsBus);
            this.moduleView = player.getView();

        } else {

            String cls = element.getAttribute("class");
            if (cls != null && !"".equals(cls)) {
                objectModuleView.getContainerPanel().addStyleName(cls);
            }
            Map<String, String> src = SourceUtil.getSource(element, "audio");
            mediaWrapperCreator.createMediaWrapper(src, new CallbackReceiver<MediaWrapper<?>>() {
                @Override
                public void setCallbackReturnObject(MediaWrapper<?> mediaWrapper) {
                    parseTemplate(defaultTemplate, objectModuleView.getContainerPanel(), mediaWrapper);
                }
            });
            this.moduleView = objectModuleView;
        }
    }

    private void parseTemplate(Element template, FlowPanel parent, MediaWrapper<?> mediaWrapper) {
        parser.setMediaWrapper(mediaWrapper);
        parser.parse(template, parent);
    }

    @Override
    public Widget getView() {
        return moduleView;
    }
}
