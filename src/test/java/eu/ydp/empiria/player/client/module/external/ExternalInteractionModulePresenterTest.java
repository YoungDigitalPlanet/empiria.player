package eu.ydp.empiria.player.client.module.external;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.external.object.ExternalInteractionEmpiriaApi;
import eu.ydp.empiria.player.client.module.external.object.ExternalInteractionObject;
import eu.ydp.empiria.player.client.module.external.structure.ExternalInteractionModuleBean;
import eu.ydp.empiria.player.client.module.external.view.ExternalInteractionView;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
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
	private ExternalInteractionModuleBean externalInteractionModuleBean;
	@Mock
	private ExternalInteractionView view;
	@Mock
	private EmpiriaPaths empiriaPaths;
	@Mock
	private ExternalInteractionEmpiriaApi empiriaApi;
	@Mock
	private ExternalInteractionObject externalObject;

	@Before
	public void init() {
		testObj.setBean(externalInteractionModuleBean);
		testObj.onExternalModuleLoaded(externalObject);
	}

	@Test
	public void shouldInitializeView() {
		// given
		final String filename = "external.html";
		final String expectedURL = "media/external.html";
		when(externalInteractionModuleBean.getSrc()).thenReturn(filename);
		when(empiriaPaths.getMediaFilePath(filename)).thenReturn(expectedURL);

		testObj.setBean(externalInteractionModuleBean);

		// when
		testObj.bindView();

		// then
		verify(view).init(empiriaApi, testObj);
		verify(view).setUrl(expectedURL);
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
	public void shouldSetState() {
		// given
		JavaScriptObject jsObj = mock(JavaScriptObject.class);
		JSONArray array = mock(JSONArray.class);
		when(array.getJavaScriptObject()).thenReturn(jsObj);

		// when
		testObj.setState(array);

		// then
		verify(externalObject).setStateFromEmpiriaOnExternal(jsObj);
	}

	@Test
	public void shouldReturnState() {
		// given
		JavaScriptObject jsObj = mock(JavaScriptObject.class);
		when(externalObject.getStateFromExternal()).thenReturn(jsObj);

		// when
		JSONArray array = testObj.getState();

		// then
		assertThat(array.getJavaScriptObject()).isEqualTo(jsObj);
	}
}
