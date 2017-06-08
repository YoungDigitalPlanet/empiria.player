/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.TypeLiteral;
import eu.ydp.empiria.player.client.module.feedback.text.blend.FeedbackBlendView;
import eu.ydp.empiria.player.client.module.feedback.text.blend.FeedbackBlendViewImpl;
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
        bind(FeedbackBlendView.class).to(FeedbackBlendViewImpl.class);

        bindPageScoped(PowerFeedbackMediator.class, new TypeLiteral<PageScopedProvider<PowerFeedbackMediator>>() {
        });

        install(new GinFactoryModuleBuilder().build(FeedbackModuleFactory.class));
        install(new GinFactoryModuleBuilder().build(SingleFeedbackSoundPlayerFactory.class));
        install(new GinFactoryModuleBuilder().build(MatcherRegistryFactory.class));
    }
}
