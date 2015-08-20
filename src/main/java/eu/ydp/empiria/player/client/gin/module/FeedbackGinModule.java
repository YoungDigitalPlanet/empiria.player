package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.TypeLiteral;
import eu.ydp.empiria.player.client.controller.feedback.matcher.MatcherRegistryFactory;
import eu.ydp.empiria.player.client.gin.factory.FeedbackModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.SingleFeedbackSoundPlayerFactory;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScopedProvider;
import eu.ydp.empiria.player.client.module.feedback.image.ImageFeedback;
import eu.ydp.empiria.player.client.module.feedback.image.ImageFeedbackPresenter;
import eu.ydp.empiria.player.client.module.feedback.text.TextFeedback;
import eu.ydp.empiria.player.client.module.feedback.text.TextFeedbackPresenter;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.PowerFeedbackMediator;

public class FeedbackGinModule extends BaseGinModule {

    @Override
    protected void configure() {
        bind(TextFeedback.class).to(TextFeedbackPresenter.class);
        bind(ImageFeedback.class).to(ImageFeedbackPresenter.class);

        bindPageScoped(PowerFeedbackMediator.class, new TypeLiteral<PageScopedProvider<PowerFeedbackMediator>>() {
        });

        install(new GinFactoryModuleBuilder().build(FeedbackModuleFactory.class));
        install(new GinFactoryModuleBuilder().build(SingleFeedbackSoundPlayerFactory.class));
        install(new GinFactoryModuleBuilder().build(MatcherRegistryFactory.class));
    }
}
