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

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.feedback.structure.Feedback;
import eu.ydp.empiria.player.client.module.core.base.IModule;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Optional.fromNullable;
import static eu.ydp.empiria.player.client.resources.EmpiriaTagConstants.NAME_FEEDBACK;
import static eu.ydp.empiria.player.client.resources.EmpiriaTagConstants.NAME_FEEDBACKS;

@Singleton
public class FeedbackRegistry {

    @Inject
    private FeedbackParserFactory feedbackParserFactory;

    private final Map<IModule, List<Feedback>> modules2feedbacks = Maps.newHashMap();

    public void registerFeedbacks(IModule module, Node moduleNode) {
        Element feedbackElement = getFeedbacksElement(moduleNode);

        if (fromNullable(feedbackElement).isPresent()) {
            addModuleFeedbacks(module, feedbackElement.getElementsByTagName(NAME_FEEDBACK));
        }
    }

    private Element getFeedbacksElement(Node moduleNode) {
        Optional<Element> feedbackElement = Optional.absent();
        NodeList moduleChildNodes = moduleNode.getChildNodes();

        for (int i = 0; i < moduleChildNodes.getLength(); i++) {
            Node child = moduleChildNodes.item(i);
            if (NAME_FEEDBACKS.equals(child.getNodeName())) {
                feedbackElement = Optional.of((Element) child);
                break;
            }
        }

        return feedbackElement.orNull();
    }

    private void addModuleFeedbacks(IModule module, NodeList feedbackNodeList) {
        List<Feedback> feedbackList = getModuleFeedbacks(module);
        feedbackList.addAll(createFeedbackList(feedbackNodeList));
        modules2feedbacks.put(module, feedbackList);
    }

    public boolean isModuleRegistered(IModule module) {
        return fromNullable(modules2feedbacks.get(module)).isPresent();
    }

    public List<Feedback> getModuleFeedbacks(IModule module) {
        List<Feedback> feedbackList = modules2feedbacks.get(module);

        if (!fromNullable(feedbackList).isPresent()) {
            feedbackList = Lists.newArrayList();
        }

        return feedbackList;
    }

    private List<Feedback> createFeedbackList(NodeList feedbackNodeList) {
        List<Feedback> feedbackList = Lists.newArrayList();

        for (int i = 0; i < feedbackNodeList.getLength(); i++) {
            Node feedbackNode = feedbackNodeList.item(i);
            feedbackList.add(createFeedback(feedbackNode));
        }

        return feedbackList;
    }

    private Feedback createFeedback(Node feedbackNode) {
        return getFeedbackParserFactory().create().parse(feedbackNode.toString());
    }

    FeedbackParserFactory getFeedbackParserFactory() {
        return feedbackParserFactory;
    }

    public Boolean hasFeedbacks() {
        return !modules2feedbacks.values().isEmpty();
    }

}
