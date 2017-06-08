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

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import eu.ydp.gwtutil.client.collections.MapStringToInt;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;

@Singleton
public class TutorService {

    private final Map<String, TutorConfig> tutors = newHashMap();
    private final Map<String, PersonaService> personaServices = newHashMap();
    private final Provider<PersonaService> personaServiceProvider;
    private Optional<MapStringToInt> importedTutorIdToPersonaIndex = Optional.absent();

    @Inject
    public TutorService(Provider<PersonaService> personaServiceProvider) {
        this.personaServiceProvider = personaServiceProvider;
    }

    public TutorConfig getTutorConfig(String tutorId) {
        return tutors.get(tutorId);
    }

    public void registerTutor(String tutorId, TutorConfig tutorConfig) {
        tutors.put(tutorId, tutorConfig);
    }

    public PersonaService getTutorPersonaService(String tutorId) {
        PersonaService personaService = personaServices.get(tutorId);

        if (personaService == null) {
            TutorConfig tutorConfig = getTutorConfig(tutorId);
            int initialPersonaIndex = findInitialPersonaIndex(tutorId);
            personaService = personaServiceProvider.get();
            personaService.init(tutorConfig, initialPersonaIndex);
            personaServices.put(tutorId, personaService);
        }

        return personaService;
    }

    private int findInitialPersonaIndex(String tutorId) {
        boolean containsImportedPersonaIndex = importedTutorIdToPersonaIndex.isPresent() && importedTutorIdToPersonaIndex.get().containsKey(tutorId);
        if (containsImportedPersonaIndex) {
            return importedTutorIdToPersonaIndex.get().get(tutorId);
        } else {
            return 0;
        }
    }

    public Map<String, Integer> buildTutorIdToCurrentPersonaIndexMap() {
        Set<String> tutorsIds = tutors.keySet();
        Map<String, Integer> tutorIdToCurrentPersonaMap = new HashMap<String, Integer>();

        for (String tutorId : tutorsIds) {
            PersonaService personaService = getTutorPersonaService(tutorId);
            int personaIndex = personaService.getCurrentPersonaIndex();
            tutorIdToCurrentPersonaMap.put(tutorId, personaIndex);
        }
        return tutorIdToCurrentPersonaMap;
    }

    public void importCurrentPersonasForTutors(MapStringToInt importedTutorIdToPersonaIndex) {
        this.importedTutorIdToPersonaIndex = Optional.of(importedTutorIdToPersonaIndex);
    }
}
