package eu.ydp.empiria.player.client.module.external.interaction;

import com.google.common.base.Optional;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.external.common.ExternalInteractionPaths;
import eu.ydp.empiria.player.client.module.external.common.state.ExternalInteractionStateSaver;
import eu.ydp.empiria.player.client.module.external.common.state.ExternalStateEncoder;
import eu.ydp.empiria.player.client.module.external.interaction.view.ExternalInteractionView;
import eu.ydp.empiria.player.client.module.external.interaction.api.ExternalInteractionEmpiriaApi;
import eu.ydp.empiria.player.client.module.external.interaction.api.ExternalInteractionApi;
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
	private ExternalInteractionApi externalApi;
	@Mock
	private ExternalStateEncoder stateUtil;
	@Mock
	private ExternalInteractionStateSaver stateSaver;

	@Before
	public void init() {
		Optional<JavaScriptObject> jsoOptional = Optional.absent();
		when(stateSaver.getExternalState()).thenReturn(jsoOptional);
		testObj.onExternalModuleLoaded(externalApi);
	}

	@Test
	public void shouldNotSetState_whenIsEmpty() {
		// given
		Optional<JavaScriptObject> jsoOptional = Optional.absent();
		when(stateSaver.getExternalState()).thenReturn(jsoOptional);

		// when
		testObj.onExternalModuleLoaded(externalApi);

		// then
		verify(externalApi, never()).setStateOnExternal(any(JavaScriptObject.class));
	}

	@Test
	public void shouldSetStateOnExternal_whenIsPresent() {
		// given
		JavaScriptObject jso = mock(JavaScriptObject.class);
		Optional<JavaScriptObject> jsoOptional = Optional.of(jso);
		when(stateSaver.getExternalState()).thenReturn(jsoOptional);

		// when
		testObj.onExternalModuleLoaded(externalApi);

		// then
		verify(externalApi).setStateOnExternal(jso);
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
		verify(externalApi).reset();
	}

	@Test
	public void shouldLock() {
		// given
		boolean locked = true;

		// when
		testObj.setLocked(locked);

		// then
		verify(externalApi).lock();
	}

	@Test
	public void shouldUnlock() {
		// given
		boolean locked = false;

		// when
		testObj.setLocked(locked);

		// then
		verify(externalApi).unlock();
	}

	@Test
	public void shouldMarkCorrectAnswers() {
		// given
		MarkAnswersMode mode = MarkAnswersMode.MARK;
		MarkAnswersType type = MarkAnswersType.CORRECT;

		// when
		testObj.markAnswers(type, mode);

		// then
		verify(externalApi).markCorrectAnswers();
	}

	@Test
	public void shouldUnmarkCorrectAnswers() {
		// given
		MarkAnswersMode mode = MarkAnswersMode.UNMARK;
		MarkAnswersType type = MarkAnswersType.CORRECT;

		// when
		testObj.markAnswers(type, mode);

		// then
		verify(externalApi).unmarkCorrectAnswers();
	}

	@Test
	public void shouldMarkWrongAnswers() {
		// given
		MarkAnswersMode mode = MarkAnswersMode.MARK;
		MarkAnswersType type = MarkAnswersType.WRONG;

		// when
		testObj.markAnswers(type, mode);

		// then
		verify(externalApi).markWrongAnswers();
	}

	@Test
	public void shouldUnmarkWrongAnswers() {
		// given
		MarkAnswersMode mode = MarkAnswersMode.UNMARK;
		MarkAnswersType type = MarkAnswersType.WRONG;

		// when
		testObj.markAnswers(type, mode);

		// then
		verify(externalApi).unmarkWrongAnswers();
	}

	@Test
	public void shouldShowCorrectAnswers() {
		// given
		ShowAnswersType type = ShowAnswersType.CORRECT;

		// when
		testObj.showAnswers(type);

		// then
		verify(externalApi).showCorrectAnswers();
	}

	@Test
	public void shouldHideCorrectAnswers() {
		// given
		ShowAnswersType type = ShowAnswersType.USER;

		// when
		testObj.showAnswers(type);

		// then
		verify(externalApi).hideCorrectAnswers();
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
		when(externalApi.getStateFromExternal()).thenReturn(jsObj);
		when(stateUtil.encodeState(jsObj)).thenReturn(jsonArray);

		// when
		JSONArray array = testObj.getState();

		// then
		assertThat(array).isEqualTo(jsonArray);
		verify(stateSaver).setExternalState(jsObj);
	}
}
