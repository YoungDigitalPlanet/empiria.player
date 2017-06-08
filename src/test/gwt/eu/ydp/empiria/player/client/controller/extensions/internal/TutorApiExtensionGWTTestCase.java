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

package eu.ydp.empiria.player.client.controller.extensions.internal;

import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorService;
import eu.ydp.gwtutil.client.collections.JsMapStringToInt;

import java.util.HashMap;
import java.util.Map;

public class TutorApiExtensionGWTTestCase extends EmpiriaPlayerGWTTestCase {

    private TutorApiExtension apiExtension;

    public void testShouldExportCurrentPersonasIndexesForTutors() throws Exception {
        // given
        apiExtension = new TutorApiExtension(new TutorServiceMock());

        // when
        JsMapStringToInt personasId = apiExtension.exportTutorPersonasId();

        // then
        assertNotNull(personasId);
        assertEquals(personasId.keySet().size(), 2);
        assertEquals((int) personasId.get("tutor1"), 1);
        assertEquals((int) personasId.get("tutor2"), 2);
    }

    private class TutorServiceMock extends TutorService {

        public TutorServiceMock() {
            super(null);
        }

        @Override
        public Map<String, Integer> buildTutorIdToCurrentPersonaIndexMap() {
            HashMap<String, Integer> tutorIdToPersonIndex = new HashMap<String, Integer>();
            tutorIdToPersonIndex.put("tutor1", 1);
            tutorIdToPersonIndex.put("tutor2", 2);
            return tutorIdToPersonIndex;
        }
    }
}
