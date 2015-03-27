package eu.ydp.empiria.player.client.module.connection.structure;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gwt.json.client.JSONArray;

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.module.connection.InteractionModuleVersionConverter;
import eu.ydp.empiria.player.client.module.conversion.StateToStateAndStructureConverter;
import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.client.json.YJsonObject;
import eu.ydp.gwtutil.client.json.YJsonValue;
import eu.ydp.gwtutil.client.json.js.YJsJsonConverter;
import eu.ydp.gwtutil.client.service.json.IJSONService;
import eu.ydp.gwtutil.client.service.json.NativeJSONService;
import eu.ydp.gwtutil.client.state.StateVersion;
import eu.ydp.gwtutil.json.YNativeJsonArray;
import eu.ydp.gwtutil.json.YNativeJsonFactory;

public class StateControllerTest extends AbstractTestBase {

	private StateController testObj;
	@Mock
	private YJsJsonConverter jsJsonConverter;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		setUp(InteractionModuleVersionConverter.class, StateToStateAndStructureConverter.class, IJSONService.class);
		InteractionModuleVersionConverter interactionModuleVersionConverter = injector.getInstance(InteractionModuleVersionConverter.class);
		testObj = new StateController(new NativeJSONService(), jsJsonConverter, interactionModuleVersionConverter);

	}

	@After
	public void after() {
		verifyNoMoreInteractions(jsJsonConverter);
	}

	@Test
	public void updateStateAndStructureVersionTest_version0() {
		// given
		JSONArray stateAndStructure = mock(JSONArray.class);
		when(jsJsonConverter.toYJson(stateAndStructure)).thenReturn(YNativeJsonFactory.createArray());

		// when
		YJsonValue result = testObj.updateStateAndStructureVersion(stateAndStructure);

		// then
		verify(jsJsonConverter).toYJson(stateAndStructure);
		assertEquals(1, result.isArray().size());
		assertNotNull(result.isArray().get(0).isObject().get(StateController.STATE).isArray());
		assertNotNull(result.isArray().get(0).isObject().get(StateController.STRUCTURE).isArray());
	}

	@Test
	public void updateStateAndStructureVersionTest_version1() {
		// given
		JSONArray stateAndStructure = mock(JSONArray.class);

		YJsonArray yStateAndStructure = YNativeJsonFactory.createArray();
		YJsonObject yStateAndStructureObject = YNativeJsonFactory.createObject();
		yStateAndStructureObject.put(StateVersion.VERSION_FIELD, YNativeJsonFactory.createNumber(1));
		yStateAndStructureObject.put(StateController.STRUCTURE, YNativeJsonFactory.createArray());
		yStateAndStructureObject.put(StateController.STATE, YNativeJsonFactory.createArray());
		yStateAndStructure.set(0, yStateAndStructureObject);

		when(jsJsonConverter.toYJson(stateAndStructure)).thenReturn(yStateAndStructure);

		// when
		YJsonValue result = testObj.updateStateAndStructureVersion(stateAndStructure);

		// then
		verify(jsJsonConverter).toYJson(stateAndStructure);
		assertSame(result, yStateAndStructure);
	}

	@Test
	public void getStructureTest() {
		// given
		YJsonArray structureAndState = YNativeJsonFactory.createArray();
		YJsonObject structureObject = YNativeJsonFactory.createObject();

		YJsonArray structureArray = YNativeJsonFactory.createArray();
		String structureString = "structure";
		structureArray.set(0, YNativeJsonFactory.createString(structureString));
		structureObject.put(StateController.STRUCTURE, structureArray);
		structureAndState.set(0, structureObject);

		// when
		YJsonArray result = testObj.getStructure(structureAndState);

		// then
		assertEquals(structureString, result.get(0).isString().stringValue());
	}

	@Test
	public void getStateTest() {
		// given
		JSONArray jsonArray = mock(JSONArray.class);
		YNativeJsonArray resonseArray = YNativeJsonFactory.createArray();
		YJsonArray stateWithResponse = createResonseYJsonArray(resonseArray);
		when(jsJsonConverter.toYJson(jsonArray)).thenReturn(stateWithResponse);

		JSONArray expectedResult = mock(JSONArray.class);
		when(jsJsonConverter.toJson(any(YJsonArray.class))).thenReturn(expectedResult);

		// when
		JSONArray result = testObj.getResponse(jsonArray);
		// then

		assertSame(expectedResult, result);
		InOrder inOrder = inOrder(jsJsonConverter);
		inOrder.verify(jsJsonConverter).toYJson(jsonArray);

		ArgumentCaptor<YNativeJsonArray> argumentCaptor = ArgumentCaptor.forClass(YNativeJsonArray.class);
		inOrder.verify(jsJsonConverter).toJson(argumentCaptor.capture());
		assertEquals(resonseArray.get(0).isString().stringValue(), argumentCaptor.getValue().get(0).isString().stringValue());
	}

	private YJsonArray createResonseYJsonArray(YNativeJsonArray resonseArray) {
		resonseArray.set(0, YNativeJsonFactory.createString("response"));
		YJsonArray result = YNativeJsonFactory.createArray();
		YJsonObject object = YNativeJsonFactory.createObject();
		object.put(StateController.STATE, resonseArray);
		result.set(0, object);

		return result;
	}

	@Test
	public void saveTest() {
		// given

		List<SimpleMatchSetBean> simpleMatchSetBeans = new ArrayList<SimpleMatchSetBean>();
		simpleMatchSetBeans.add(createSimpleMatchSetBean("a0", "a1", "a2", "a3", "a4", "a5"));
		simpleMatchSetBeans.add(createSimpleMatchSetBean("b0", "b1", "b2", "b3"));

		// when
		YJsonArray result = testObj.saveStructure(simpleMatchSetBeans);

		// then
		assertEquals(2, result.size());

		YJsonArray subArray0 = result.get(0).isArray();
		assertEquals(6, subArray0.size());
		assertSubArray(subArray0, "a");

		YJsonArray subArray1 = result.get(1).isArray();
		assertEquals(4, subArray1.size());
		assertSubArray(subArray1, "b");

	}

	@Test
	public void loadStructureTest() {
		// given
		List<SimpleMatchSetBean> simpleMatchSetBeans = new ArrayList<SimpleMatchSetBean>();
		simpleMatchSetBeans.add(createSimpleMatchSetBean("a1", "a0", "a3", "a2", "a5", "a4"));
		simpleMatchSetBeans.add(createSimpleMatchSetBean("b1", "b0", "b3", "b2"));

		YJsonArray structureYJsonArray = YNativeJsonFactory.createArray();

		structureYJsonArray.set(0, createArrayIs("a0", "a1", "a2", "a3", "a4", "a5"));
		structureYJsonArray.set(1, createArrayIs("b0", "b1", "b2", "b3"));

		// when
		List<SimpleMatchSetBean> result = testObj.loadStructure(structureYJsonArray, simpleMatchSetBeans);

		// then
		assertSimpleMatchSetBean(result.get(0), "a");
		assertSimpleMatchSetBean(result.get(1), "b");
	}

	@Test(expected = IllegalArgumentException.class)
	public void loadStructureTest_notFindId() {
		// given
		List<SimpleMatchSetBean> simpleMatchSetBeans = new ArrayList<SimpleMatchSetBean>();
		simpleMatchSetBeans.add(createSimpleMatchSetBean("a1", "a0", "a3", "a2", "a5", "a4"));
		simpleMatchSetBeans.add(createSimpleMatchSetBean("b1", "b0", "b3", "b2"));

		YJsonArray structureYJsonArray = YNativeJsonFactory.createArray();

		structureYJsonArray.set(0, createArrayIs("a0", "a1", "a2", "a3", "a4", "a5"));
		structureYJsonArray.set(1, createArrayIs("c0", "b1", "b2", "b3"));
		// when
		testObj.loadStructure(structureYJsonArray, simpleMatchSetBeans);
	}

	@Test
	public void isStructureDataExistTest_false() {

		assertFalse(testObj.isStructureExist(null));

		assertFalse(testObj.isStructureExist(YNativeJsonFactory.createArray()));
	}

	@Test
	public void isStructureDataExistTest_true() {

		// given
		YJsonArray jsonArray = YNativeJsonFactory.createArray();
		jsonArray.set(0, YNativeJsonFactory.createObject());

		// when then
		assertTrue(testObj.isStructureExist(jsonArray));
	}

	@Test
	public void getResponseWithStructureTest() {
		// given
		String stateString = "state";
		String structureString = "structure";
		// create state
		JSONArray state = mock(JSONArray.class);
		YJsonArray yState = YNativeJsonFactory.createArray();
		yState.set(0, YNativeJsonFactory.createString(stateString));
		when(jsJsonConverter.toYJson(state)).thenReturn(yState);

		// create structure
		YJsonArray savedStructure = YNativeJsonFactory.createArray();
		savedStructure.set(0, YNativeJsonFactory.createString(structureString));

		JSONArray expectedResult = mock(JSONArray.class);
		when(jsJsonConverter.toJson(any(YJsonArray.class))).thenReturn(expectedResult);

		// when
		JSONArray result = testObj.getResponseWithStructure(state, savedStructure);

		// then
		assertEquals(expectedResult, result);

		InOrder inOrder = inOrder(jsJsonConverter);
		inOrder.verify(jsJsonConverter).toYJson(state);

		ArgumentCaptor<YJsonArray> argumentCaptor = ArgumentCaptor.forClass(YJsonArray.class);
		inOrder.verify(jsJsonConverter).toJson(argumentCaptor.capture());
		YJsonArray resultBeforeConvert = argumentCaptor.getValue();
		assertEquals(1, resultBeforeConvert.size());
		assertEquals(stateString, resultBeforeConvert.get(0).isObject().get(StateController.STATE).isArray().get(0).isString().stringValue());
		assertEquals(structureString, resultBeforeConvert.get(0).isObject().get(StateController.STRUCTURE).isArray().get(0).isString().stringValue());
	}

	private void assertSimpleMatchSetBean(SimpleMatchSetBean simpleMatchSetBean, String prefixId) {
		List<SimpleAssociableChoiceBean> simpleAssociableChoices = simpleMatchSetBean.getSimpleAssociableChoices();
		for (int i = 0; i < simpleAssociableChoices.size(); i++) {
			SimpleAssociableChoiceBean simpleAssociableChoiceBean = simpleAssociableChoices.get(i);
			assertEquals(simpleAssociableChoiceBean.getIdentifier(), prefixId + i);
		}

	}

	private YJsonArray createArrayIs(String... ids) {
		YJsonArray result = YNativeJsonFactory.createArray();
		for (int i = 0; i < ids.length; i++) {
			result.set(i, YNativeJsonFactory.createString(ids[i]));
		}
		return result;

	}

	private void assertSubArray(YJsonArray subArray, String prefix) {
		for (int i = 0; i < subArray.size(); i++) {
			assertEquals(prefix + i, subArray.get(i).isString().stringValue());
		}
	}

	private SimpleMatchSetBean createSimpleMatchSetBean(String... ids) {
		SimpleMatchSetBean simpleMatchSetBean = new SimpleMatchSetBean();

		List<SimpleAssociableChoiceBean> list = new ArrayList<SimpleAssociableChoiceBean>();

		for (String id : ids) {
			SimpleAssociableChoiceBean associableChoiceBean = new SimpleAssociableChoiceBean();
			associableChoiceBean.setIdentifier(id);
			list.add(associableChoiceBean);
		}

		simpleMatchSetBean.setSimpleAssociableChoices(list);

		return simpleMatchSetBean;
	}
}
