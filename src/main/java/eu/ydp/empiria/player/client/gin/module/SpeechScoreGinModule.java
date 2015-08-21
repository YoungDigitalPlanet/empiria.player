package eu.ydp.empiria.player.client.gin.module;

import com.google.inject.TypeLiteral;
import eu.ydp.empiria.player.client.module.speechscore.presenter.SpeechScorePresenter;
import eu.ydp.empiria.player.client.module.speechscore.view.SpeechScoreLinkView;
import eu.ydp.empiria.player.client.module.speechscore.view.SpeechScoreLinkViewImpl;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopedProvider;

public class SpeechScoreGinModule extends BaseGinModule {

    @Override
    protected void configure() {
        bind(SpeechScoreLinkView.class).to(SpeechScoreLinkViewImpl.class);

        bindModuleScoped(SpeechScorePresenter.class, new TypeLiteral<ModuleScopedProvider<SpeechScorePresenter>>() {
        });
        bindModuleScoped(SpeechScoreLinkView.class, new TypeLiteral<ModuleScopedProvider<SpeechScoreLinkView>>() {
        });
    }
}
