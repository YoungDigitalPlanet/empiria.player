package eu.ydp.empiria.player.client.module.texteditor;

import com.google.gwt.json.client.JSONArray;
import eu.ydp.empiria.player.client.module.texteditor.model.TextEditorModel;
import eu.ydp.empiria.player.client.module.texteditor.model.TextEditorModelEncoder;
import eu.ydp.empiria.player.client.module.texteditor.presenter.TextEditorPresenter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TextEditorModuleTest {

	@InjectMocks
	private TextEditorModule testObj;

	@Mock
	private TextEditorPresenter presenter;
	@Mock
	private TextEditorModelEncoder textEditorModelEncoder;

	@Test
	public void shouldConvertEditorOnBodyLoad() {
		// when
		testObj.onBodyLoad();

		// then
		verify(presenter).convertEditor();
	}

	@Test
	public void shouldConvertEditorOnBodyUnload() {
		// when
		testObj.onBodyUnload();

		// then
		verify(presenter).convertEditor();
	}

	@Test
	public void shouldLockPresenter_onLock() {
		// when
		testObj.lock(true);

		// then
		verify(presenter).lock();
	}

	@Test
	public void shouldLockPresenter_onShowAnswer() {
		// when
		testObj.showCorrectAnswers(true);

		// then
		verify(presenter).lock();
	}

	@Test
	public void shouldLockPresenter_onmarkAnswers() {
		// when
		testObj.markAnswers(true);

		// then
		verify(presenter).lock();
	}

	@Test
	public void shouldUnlockPresenter_onLock() {
		// when
		testObj.lock(false);

		// then
		verify(presenter).unlock();
	}

	@Test
	public void shouldUnlockPresenter_onShowAnswer() {
		// when
		testObj.showCorrectAnswers(false);

		// then
		verify(presenter).unlock();
	}

	@Test
	public void shouldUnlockPresenter_onmarkAnswers() {
		// when
		testObj.markAnswers(false);

		// then
		verify(presenter).unlock();
	}

	@Test
	public void shouldSetStateOnPresenter() {
		// given
		String content = "any string";
		TextEditorModel expectedTextEditorModel = new TextEditorModel(content);
		JSONArray state = mock(JSONArray.class);

		when(textEditorModelEncoder.decodeModel(state)).thenReturn(expectedTextEditorModel);

		// when
		testObj.setState(state);

		// then
		verify(presenter).setTextEditorModel(expectedTextEditorModel);
	}

	@Test
	public void shouldGetStateFromPresenter() {
		// given
		String content = "any string";
		TextEditorModel textEditorModel = new TextEditorModel(content);
		when(presenter.getTextEditorModel()).thenReturn(textEditorModel);
		JSONArray expectedState = mock(JSONArray.class);
		when(textEditorModelEncoder.encodeModel(textEditorModel)).thenReturn(expectedState);

		// when
		JSONArray actual = testObj.getState();

		// then
		assertThat(actual).isEqualTo(expectedState);
	}

	@Test
	public void shouldSetEmptyModelOnReset() {
		// given
		TextEditorModel emptyTextEditorModel = TextEditorModel.createEmpty();

		// when
		testObj.reset();

		// then
		verify(presenter).setTextEditorModel(emptyTextEditorModel);
	}
}
