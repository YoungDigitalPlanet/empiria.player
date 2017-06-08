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

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.module.core.base.HasChildren;
import eu.ydp.empiria.player.client.module.core.base.IModule;

import java.util.*;

public class FeedbackActionCollector {

    private static final Function<FeedbackAction, Class<? extends FeedbackAction>> classExtractor = new Function<FeedbackAction, Class<? extends FeedbackAction>>() {
        @Override
        public Class<? extends FeedbackAction> apply(FeedbackAction currentAction) {
            return currentAction.getClass();
        }
    };

    private IModule source;

    private final Map<IModule, FeedbackProperties> source2properties = Maps.newHashMap();

    private final ListMultimap<IModule, FeedbackAction> source2actions = ArrayListMultimap.create();

    public void setSource(IModule source) {
        this.source = source;
    }

    public void appendPropertiesToSource(FeedbackProperties properties, IModule source) {
        FeedbackProperties moduleProperties = source2properties.get(source);

        if (moduleProperties == null) {
            source2properties.put(source, properties);
        } else {
            moduleProperties.appendProperties(properties);
        }
    }

    public FeedbackProperties getSourceProperties(IModule source) {
        return source2properties.get(source);
    }

    public void appendActionsToSource(List<FeedbackAction> actions, IModule source) {
        if (source2actions.containsKey(source)) {
            source2actions.get(source).addAll(actions);
        } else {
            source2actions.putAll(source, actions);
        }
        clearChildActions(source, actions);
    }

    private void clearChildActions(IModule currentModule, List<FeedbackAction> currentActions) {
        final Collection<Class<? extends FeedbackAction>> classesToRemove = getCurrentActionsClasses(currentActions);
        Collection<IModule> sourcesToClear = getSourcesToClear(currentModule);
        for (IModule sourceToRemove : sourcesToClear) {
            List<FeedbackAction> actionsConsideredToRemove = source2actions.get(sourceToRemove);
            Predicate<FeedbackAction> classPredicate = new Predicate<FeedbackAction>() {
                @Override
                public boolean apply(FeedbackAction actionConsideredToRemove) {
                    Class<? extends FeedbackAction> classConsideredToRemove = actionConsideredToRemove.getClass();
                    return classesToRemove.contains(classConsideredToRemove);
                }
            };
            Iterables.removeIf(actionsConsideredToRemove, classPredicate);
        }
    }

    private Collection<IModule> getSourcesToClear(final IModule currentModule) {
        Set<IModule> modulesCopy = Sets.newHashSet(source2actions.keySet());

        Predicate<IModule> isParentPredicate = new Predicate<IModule>() {
            @Override
            public boolean apply(IModule source) {
                List<HasChildren> parents = getParentHierarchy(source);
                return parents.contains(currentModule);
            }
        };
        return Collections2.filter(modulesCopy, isParentPredicate);
    }

    private Collection<Class<? extends FeedbackAction>> getCurrentActionsClasses(List<FeedbackAction> currentActions) {
        return Collections2.transform(currentActions, classExtractor);
    }

    private List<HasChildren> getParentHierarchy(IModule source) {
        List<HasChildren> parents = new ArrayList<>();
        HasChildren parent = source.getParentModule();
        while (parent != null) {
            parents.add(parent);
            parent = parent.getParentModule();
        }
        return parents;
    }

    public void removeActions(List<FeedbackAction> actionsToRemove) {
        source2actions.values().removeAll(actionsToRemove);
    }

    public IModule getSource() {
        return source;
    }

    public List<FeedbackAction> getActions() {
        return Lists.newArrayList(source2actions.values());
    }

    public List<FeedbackAction> getActionsForSource(IModule source) {
        return Lists.newArrayList(source2actions.get(source));
    }

}
