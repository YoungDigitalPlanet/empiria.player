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

package eu.ydp.empiria.player.client.gin.factory;

import com.google.gwt.user.client.ui.Panel;
import eu.ydp.empiria.player.client.controller.Assessment;
import eu.ydp.empiria.player.client.controller.AssessmentBody;
import eu.ydp.empiria.player.client.controller.AssessmentController;
import eu.ydp.empiria.player.client.controller.communication.AssessmentData;
import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.controller.events.widgets.WidgetWorkflowListener;
import eu.ydp.empiria.player.client.controller.flow.IFlowSocket;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import eu.ydp.empiria.player.client.view.assessment.AssessmentBodyView;
import eu.ydp.empiria.player.client.view.assessment.AssessmentContentView;
import eu.ydp.empiria.player.client.view.assessment.AssessmentViewSocket;

public interface AssessmentFactory {
    AssessmentContentView geAssessmentContentView(Panel panel);

    AssessmentController createAssessmentController(AssessmentViewSocket avs, IFlowSocket fsocket);

    Assessment createAssessment(AssessmentData data, DisplayContentOptions options, ModulesRegistrySocket modulesRegistrySocket);

    AssessmentBody createAssessmentBody(DisplayContentOptions options, ModuleSocket moduleSocket, ModulesRegistrySocket modulesRegistrySocket);

    AssessmentBodyView createAssessmentBodyView(WidgetWorkflowListener wwl);
}
