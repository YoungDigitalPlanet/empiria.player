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

package eu.ydp.empiria.player.client.controller.extensions.internal.tutor;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.tutor.TutorEvent;
import eu.ydp.empiria.player.client.module.tutor.TutorEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;

public class PersonaService {

    @Inject
    private EventsBus eventsBus;

    private int currentPersonaIndex;
    private TutorConfig tutorConfig;

    public void init(TutorConfig tutorConfig, Integer initialPersonaIndex) {
        this.tutorConfig = tutorConfig;
        this.currentPersonaIndex = initialPersonaIndex;
    }

    public TutorPersonaProperties getPersonaProperties() {
        return tutorConfig.getTutorPersonaProperties(currentPersonaIndex);
    }

    public int getCurrentPersonaIndex() {
        return currentPersonaIndex;
    }

    public void setCurrentPersonaIndex(int personaIndex) {
        if (personaIndex != currentPersonaIndex) {
            this.currentPersonaIndex = personaIndex;
            eventsBus.fireAsyncEvent(new TutorEvent(TutorEventTypes.TUTOR_CHANGED, this, personaIndex));
        }
    }
}
