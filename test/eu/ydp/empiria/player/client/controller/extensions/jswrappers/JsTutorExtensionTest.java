package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorService;
import eu.ydp.gwtutil.client.collections.JsMapStringToInt;

public class JsTutorExtensionTest extends AbstractEmpiriaPlayerGWTTestCase {

	private JsTutorExtension tutorExtension;
	private JavaScriptObject playerJsObject;
	private TutorService tutorService;
	
	protected void setUpTests() throws Exception {
		tutorExtension = new JsTutorExtension();
		
		tutorService = new TutorServiceMock();
		playerJsObject = JavaScriptObject.createObject();
		tutorExtension.initJsApi(playerJsObject, tutorService);
	}
	

	public void testShouldExportCurrentPersonasIndexesForTutors() throws Exception {
		setUpTests();
		
		//when
		JsMapStringToInt personasId = tutorExtension.exportTutorPersonasId();
		
		//then
		assertNotNull(personasId);
		assertEquals(personasId.keys().size(), 2);
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
