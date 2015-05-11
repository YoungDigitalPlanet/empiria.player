package eu.ydp.empiria.player.client.module.external;

import com.google.common.base.Optional;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.external.object.ExternalInteractionEmpiriaApi;
import eu.ydp.empiria.player.client.module.external.object.ExternalInteractionObject;
import eu.ydp.empiria.player.client.module.external.state.ExternalInteractionStateSaver;
import eu.ydp.empiria.player.client.module.external.state.ExternalStateEncoder;
import eu.ydp.empiria.player.client.module.external.view.ExternalInteractionView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExternalInteractionModulePresenterTest {

	@InjectMocks
	private ExternalInteractionModulePresenter testObj;
	@Mock
	private ExternalInteractionView view;
	@Mock
	private ExternalInteractionPaths externalPaths;
	@Mock
	private ExternalInteractionEmpiriaApi empiriaApi;
	@Mock
	private ExternalInteractionObject externalObject;
	@Mock
	private ExternalStateEncoder stateUtil;
	@Mock
	private ExternalInteractionStateSaver stateSaver;

	@Before
	public void init() {
		Optional<JavaScriptObject> jsoOptional = Optional.absent();
		when(stateSaver.getExternalState()).thenReturn(jsoOptional);
		testObj.onExternalModuleLoaded(externalObject);
	}

	@Test
	public void shouldNotSetState_whenIsEmpty() {
		// given
		Optional<JavaScriptObject> jsoOptional = Optional.absent();
		when(stateSaver.getExternalState()).thenReturn(jsoOptional);

		// when
		testObj.onExternalModuleLoaded(externalObject);

		// then
		verify(externalObject, never()).setStateOnExternal(any(JavaScriptObject.class));
	}

	@Test
	public void shouldSetStateOnExternal_whenIsPresent() {
		// given
		JavaScriptObject jso = mock(JavaScriptObject.class);
		Optional<JavaScriptObject> jsoOptional = Optional.of(jso);
		when(stateSaver.getExternalState()).thenReturn(jsoOptional);

		// when
		testObj.onExternalModuleLoaded(externalObject);

		// then
		verify(externalObject).setStateOnExternal(jso);
	}

	@Test
	public void shouldInitializeView() {
		// given
		final String expectedURL = "media/external/index.html";
		when(externalPaths.getExternalEntryPointPath()).thenReturn(expectedURL);

		// when
		testObj.bindView();

		// then
		verify(view).init(empiriaApi, testObj, expectedURL);
	}

	@Test
	public void shouldResetExternalObject() {
		// given

		// when
		testObj.reset();

		// then
		verify(externalObject).reset();
	}

	@Test
	public void shouldLock() {
		// given
		boolean locked = true;

		// when
		testObj.setLocked(locked);

		// then
		verify(externalObject).lock();
	}

	@Test
	public void shouldUnlock() {
		// given
		boolean locked = false;

		// when
		testObj.setLocked(locked);

		// then
		verify(externalObject).unlock();
	}

	@Test
	public void shouldMarkCorrectAnswers() {
		// given
		MarkAnswersMode mode = MarkAnswersMode.MARK;
		MarkAnswersType type = MarkAnswersType.CORRECT;

		// when
		testObj.markAnswers(type, mode);

		// then
		verify(externalObject).markCorrectAnswers();
	}

	@Test
	public void shouldUnmarkCorrectAnswers() {
		// given
		MarkAnswersMode mode = MarkAnswersMode.UNMARK;
		MarkAnswersType type = MarkAnswersType.CORRECT;

		// when
		testObj.markAnswers(type, mode);

		// then
		verify(externalObject).unmarkCorrectAnswers();
	}

	@Test
	public void shouldMarkWrongAnswers() {
		// given
		MarkAnswersMode mode = MarkAnswersMode.MARK;
		MarkAnswersType type = MarkAnswersType.WRONG;

		// when
		testObj.markAnswers(type, mode);

		// then
		verify(externalObject).markWrongAnswers();
	}

	@Test
	public void shouldUnmarkWrongAnswers() {
		// given
		MarkAnswersMode mode = MarkAnswersMode.UNMARK;
		MarkAnswersType type = MarkAnswersType.WRONG;

		// when
		testObj.markAnswers(type, mode);

		// then
		verify(externalObject).unmarkWrongAnswers();
	}

	@Test
	public void shouldShowCorrectAnswers() {
		// given
		ShowAnswersType type = ShowAnswersType.CORRECT;

		// when
		testObj.showAnswers(type);

		// then
		verify(externalObject).showCorrectAnswers();
	}

	@Test
	public void shouldHideCorrectAnswers() {
		// given
		ShowAnswersType type = ShowAnswersType.USER;

		// when
		testObj.showAnswers(type);

		// then
		verify(externalObject).hideCorrectAnswers();
	}

	@Test
	public void shouldSetState() {
		// given
		JavaScriptObject jsObj = mock(JavaScriptObject.class);
		JSONArray array = mock(JSONArray.class);
		when(stateUtil.decodeState(array)).thenReturn(jsObj);

		// when
		testObj.setState(array);

		// then
		verify(stateSaver).setExternalState(jsObj);
	}

	@Test
	public void shouldReturnState() {
		// given
		JavaScriptObject jsObj = mock(JavaScriptObject.class);
		JSONArray jsonArray = mock(JSONArray.class);
		when(externalObject.getStateFromExternal()).thenReturn(jsObj);
		when(stateUtil.encodeState(jsObj)).thenReturn(jsonArray);

		// when
		JSONArray array = testObj.getState();

		// then
		assertThat(array).isEqualTo(jsonArray);
		verify(stateSaver).setExternalState(jsObj);
	}
}
