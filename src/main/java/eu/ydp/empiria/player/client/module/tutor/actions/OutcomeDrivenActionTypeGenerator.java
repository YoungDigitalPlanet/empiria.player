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

package eu.ydp.empiria.player.client.module.tutor.actions;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorConfig;
import eu.ydp.empiria.player.client.module.tutor.ActionType;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.Set;

public class OutcomeDrivenActionTypeGenerator {

    private TutorConfig tutorConfig;

    private OutcomeDrivenActionTypeProvider actionTypeProvider;

    @Inject
    public OutcomeDrivenActionTypeGenerator(@ModuleScoped TutorConfig tutorConfig, @ModuleScoped OutcomeDrivenActionTypeProvider actionTypeProvider) {
        this.tutorConfig = tutorConfig;
        this.actionTypeProvider = actionTypeProvider;
    }

    private final Predicate<OutcomeDrivenAction> actionPredicate = new Predicate<OutcomeDrivenAction>() {

        @Override
        public boolean apply(OutcomeDrivenAction action) {
            ActionType actionType = action.getActionType();
            return tutorConfig.supportsAction(actionType) && action.actionOccured();
        }
    };

    public Optional<ActionType> findActionType() {
        Set<OutcomeDrivenAction> actions = actionTypeProvider.getActions();

        Optional<OutcomeDrivenAction> action = Iterables.tryFind(actions, actionPredicate);
        if (action.isPresent()) {
            ActionType actionType = action.get().getActionType();
            return Optional.of(actionType);
        }

        return Optional.absent();
    }

}
