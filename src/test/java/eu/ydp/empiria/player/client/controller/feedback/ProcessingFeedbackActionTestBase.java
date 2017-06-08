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

package eu.ydp.empiria.player.client.controller.feedback;

import static org.mockito.Mockito.*;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.feedback.player.FeedbackSoundPlayer;
import eu.ydp.empiria.player.client.controller.feedback.processor.SoundActionProcessor;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.gin.factory.FeedbackModuleFactory;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxNative;
import org.junit.Before;

import java.util.List;

public class ProcessingFeedbackActionTestBase extends AbstractTestBaseWithoutAutoInjectorInit {

    protected FeedbackActionCollector collector;

    protected ModuleFeedbackProcessor processor;

    protected SoundActionProcessorMock soundProcessor;

    protected MathJaxNative mathJaxNative;

    protected IModule source;

    protected FeedbackProperties properties;

    @Before
    @Override
    public void setUp() {
        setUp(new Class<?>[]{FeedbackActionCollector.class, SoundActionProcessor.class, FeedbackSoundPlayer.class}, new FeedbackActionCollectorModule());
        soundProcessor = spy(injector.getInstance(SoundActionProcessorMock.class));
    }

    protected void initializeWithActions(List<FeedbackAction> actions) {
        new Initializer().initWithActions(actions);
    }

    protected class FeedbackActionCollectorModule implements Module {

        @Override
        public void configure(Binder binder) {
            //
        }

        @Provides
        SoundActionProcessor getSoundActionProcessor() {
            return soundProcessor;
        }

        @Provides
        public FeedbackActionCollector getCollector() {
            return collector;
        }

        @Provides
        public FeedbackSoundPlayer getSoundPlayer() {
            return mock(FeedbackSoundPlayer.class);
        }

        @Provides
        public MathJaxNative getMathJaxNative() {
            return mathJaxNative;
        }

    }

    protected class Initializer {

        public void initWithActions(List<FeedbackAction> actions) {
            createCollector(actions);
            createProcessor();
            createMathJaxNative();
        }

        private void createMathJaxNative() {
            mathJaxNative = mock(MathJaxNative.class);
        }

        private void createCollector(List<FeedbackAction> actions) {
            source = mock(IModule.class);
            properties = mock(FeedbackProperties.class);
            when(properties.getBooleanProperty(FeedbackPropertyName.OK)).thenReturn(true);
            collector = new FeedbackActionCollector();
            collector.setSource(source);
            collector.appendActionsToSource(actions, source);
            collector.appendPropertiesToSource(properties, source);
        }

        private void createProcessor() {
            InlineBodyGeneratorSocket inlineBodyGeneratorSocket = mock(InlineBodyGeneratorSocket.class);
            FeedbackModuleFactory feedbackModuleFactory = injector.getInstance(FeedbackModuleFactory.class);
            processor = feedbackModuleFactory.getModuleFeedbackProcessor(inlineBodyGeneratorSocket);
        }

    }
}
