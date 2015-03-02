package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.speechscore.presenter.SpeechScoreProtocolProvider;
import eu.ydp.empiria.player.client.module.speechscore.view.SpeechScoreLinkView;
import eu.ydp.empiria.player.client.module.speechscore.view.SpeechScoreLinkViewImpl;

public class SpeechScoreGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(SpeechScoreLinkView.class).to(SpeechScoreLinkViewImpl.class);
		bind(SpeechScoreProtocolProvider.class).in(Singleton.class);
	}
}