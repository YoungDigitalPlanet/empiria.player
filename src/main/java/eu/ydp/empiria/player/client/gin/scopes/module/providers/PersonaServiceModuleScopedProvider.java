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

package eu.ydp.empiria.player.client.gin.scopes.module.providers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.PersonaService;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorService;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleCreationContext;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopeStack;

public class PersonaServiceModuleScopedProvider implements Provider<PersonaService> {

    private static final String TUTOR_ID_ATTR = "tutorId";

    @Inject
    private ModuleScopeStack moduleScopeStack;

    @Inject
    private TutorService tutorService;

    @Override
    public PersonaService get() {
        ModuleCreationContext context = moduleScopeStack.getCurrentTopContext();
        String tutorId = context.getXmlElement().getAttribute(TUTOR_ID_ATTR);
        return tutorService.getTutorPersonaService(tutorId);
    }

}
