package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import eu.ydp.empiria.player.client.module.speechscore.view.SpeechScoreLinkView;
import eu.ydp.empiria.player.client.module.speechscore.view.SpeechScoreLinkViewImpl;

public class SpeechScoreGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(SpeechScoreLinkView.class).to(SpeechScoreLinkViewImpl.class);
    }
}
