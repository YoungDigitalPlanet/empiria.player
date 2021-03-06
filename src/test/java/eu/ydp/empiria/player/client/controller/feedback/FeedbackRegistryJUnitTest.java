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

import com.google.gwt.xml.client.Node;
import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.controller.feedback.structure.Feedback;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.gwtutil.xml.XMLParser;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class FeedbackRegistryJUnitTest extends AbstractTestBase {

    private FeedbackRegistry registry;

    @Override
    @Before
    public void setUp() {
        super.setUp(FeedbackRegistry.class);
        registry = injector.getInstance(FeedbackRegistry.class);
    }

    @Test
    public void shouldAddModuleFeedbacks() {
        IModule module = mock(IModule.class);
        Node moduleNode = getModuleNode("<module><feedbacks><feedback/></feedbacks></module>");

        registry.registerFeedbacks(module, moduleNode);
        List<Feedback> moduleFeedbacks = registry.getModuleFeedbacks(module);

        assertThat(moduleFeedbacks, is(not(nullValue())));
        assertThat(moduleFeedbacks.size(), is(equalTo(1)));
    }

    @Test
    public void shouldReturnEmptyListWhen_noFeedbackNode() {
        IModule module = mock(IModule.class);
        Node moduleNode = getModuleNode("<module/>");

        registry.registerFeedbacks(module, moduleNode);
        List<Feedback> moduleFeedbacks = registry.getModuleFeedbacks(module);

        assertThat(moduleFeedbacks, is(not(nullValue())));
        assertThat(moduleFeedbacks.size(), is(equalTo(0)));
    }

    @Test
    public void shouldReturnEmptyListWhen_thereIsNoSuchModule() {
        IModule module = mock(IModule.class);
        IModule module1 = mock(IModule.class);
        Node moduleNode = getModuleNode("<module/>");

        registry.registerFeedbacks(module, moduleNode);
        List<Feedback> moduleFeedbacks = registry.getModuleFeedbacks(module1);

        assertThat(moduleFeedbacks, is(not(nullValue())));
        assertThat(moduleFeedbacks.size(), is(equalTo(0)));
    }

    @Test
    public void testIfModuleIsRegistered() {
        IModule module = mock(IModule.class);
        IModule module1 = mock(IModule.class);
        Node moduleNode = getModuleNode("<module><feedbacks><feedback/></feedbacks></module>");

        registry.registerFeedbacks(module, moduleNode);

        assertThat("module is registered", registry.isModuleRegistered(module), is(equalTo(true)));
        assertThat("module1 is registered", registry.isModuleRegistered(module1), is(equalTo(false)));
    }

    @Test
    public void testIfModuleIsRegisteredWhen_noFeedbackNode() {
        IModule module = mock(IModule.class);
        IModule module1 = mock(IModule.class);
        Node moduleNode = getModuleNode("<module/>");

        registry.registerFeedbacks(module, moduleNode);

        assertThat("module is registered", registry.isModuleRegistered(module), is(equalTo(false)));
        assertThat("module1 is registered", registry.isModuleRegistered(module1), is(equalTo(false)));
    }

    @Test
    public void shouldAppendFeedbacksToRegisteredModule() {
        IModule module = mock(IModule.class);
        Node moduleNode = getModuleNode("<module><feedbacks><feedback/><feedback/></feedbacks></module>");

        registry.registerFeedbacks(module, moduleNode);
        assertThat(registry.getModuleFeedbacks(module).size(), is(equalTo(2)));

        registry.registerFeedbacks(module, moduleNode);
        assertThat(registry.getModuleFeedbacks(module).size(), is(equalTo(4)));
    }

    @Test
    public void testIfRegistryDoesntContainFeedbacks() {
        assertThat(registry.hasFeedbacks(), is(equalTo(false)));
    }

    @Test
    public void testIfRegistryContainsFeedbacks() {
        IModule module = mock(IModule.class);
        Node moduleNode = getModuleNode("<module><feedbacks><feedback/></feedbacks></module>");

        registry.registerFeedbacks(module, moduleNode);
        assertThat(registry.hasFeedbacks(), is(equalTo(true)));
    }

    @Test
    public void shouldNotGetFeedbacksFromChildModules() {
        IModule module = mock(IModule.class);
        Node moduleNode = getModuleNode("<module><childModule><feedbacks><feedback/></feedbacks></childModule></module>");

        registry.registerFeedbacks(module, moduleNode);
        assertThat(registry.getModuleFeedbacks(module), is(not(nullValue())));
        assertThat(registry.getModuleFeedbacks(module).isEmpty(), is(true));
    }

    private Node getModuleNode(String node) {
        return XMLParser.parse(node).getDocumentElement();
    }

}
