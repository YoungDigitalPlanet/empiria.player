package eu.ydp.empiria.player.client.controller.extensions.internal;

import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorService;
import eu.ydp.gwtutil.client.collections.JsMapStringToInt;

import java.util.HashMap;
import java.util.Map;

public class TutorApiExtensionTest extends AbstractEmpiriaPlayerGWTTestCase {

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
