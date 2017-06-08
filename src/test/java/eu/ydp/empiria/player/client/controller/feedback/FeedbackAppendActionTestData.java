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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ActionType;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowTextAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowUrlAction;
import eu.ydp.empiria.player.client.module.core.base.IModule;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

class FeedbackAppendActionTestData {

    private final Map<Integer, FeedbackActionData> actions = Maps.newHashMap();

    void addShowUrlAction(int position, IModule module, String url, ActionType type) {
        actions.put(position, new FeedbackActionData(createUrlAction(url, type), module, ShowUrlAction.class));
    }

    void addShowTextAction(int position, IModule module, XMLContent text) {
        actions.put(position, new FeedbackActionData(createTextAction(text), module, ShowTextAction.class));
    }

    List<FeedbackAction> getModuleActions(IModule module) {
        List<FeedbackAction> moduleActions = Lists.newArrayList();

        for (FeedbackActionData actionData : actions.values()) {
            if (actionData.getModule() == module) {
                moduleActions.add(actionData.getAction());
            }
        }

        return moduleActions;
    }

    int getActionsSize() {
        return actions.values().size();
    }

    FeedbackAction getActionAtIndex(int index) {
        FeedbackAction action = null;

        try {
            action = getActionDataAtIndex(index).getAction();
        } catch (Exception exception) {
            Logger.getAnonymousLogger().info(exception.getMessage());
        }

        return action;
    }

    FeedbackActionData getActionDataAtIndex(int index) {
        FeedbackActionData actionData = null;

        try {
            actionData = actions.get(index);
        } catch (Exception exception) {
            Logger.getAnonymousLogger().info(exception.getMessage());
        }

        return actionData;
    }

    private ShowUrlAction createUrlAction(String url, ActionType type) {
        ShowUrlAction action = new ShowUrlAction();

        action.setHref(url);
        action.setType(type.getName());
        return action;
    }

    private ShowTextAction createTextAction(XMLContent text) {
        ShowTextAction action = new ShowTextAction();

        action.setContent(text);
        return action;
    }

    static class FeedbackActionData {

        private final IModule module;

        private final FeedbackAction action;

        private final Class<? extends FeedbackAction> clazz;

        public FeedbackActionData(FeedbackAction action, IModule module, Class<? extends FeedbackAction> clazz) {
            this.action = action;
            this.module = module;
            this.clazz = clazz;
        }

        public IModule getModule() {
            return module;
        }

        public FeedbackAction getAction() {
            return action;
        }

        public Class<? extends FeedbackAction> getActionClass() {
            return clazz;
        }
    }
}
