package eu.ydp.empiria.player.client.module.texteditor.presenter;

import eu.ydp.empiria.player.client.module.texteditor.view.TextEditorView;
import eu.ydp.empiria.player.client.module.texteditor.wrapper.TextEditorJSWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TextEditorPresenterTest {

	@InjectMocks
	private TextEditorPresenter testObj;

	@Mock
	private TextEditorView textEditorView;
	@Mock
	private TextEditorJSWrapper textEditorJSWrapper;

	private static final String MODULE_ID = "MODULE_ID";
	private static final String CONTENT = "CONTENT";

	@Before
	public void setUp() {
		testObj.init(MODULE_ID);
	}

	@Test
	public void shouldInitView() {
		// then
		verify(textEditorView).init();
	}

	@Test
	public void shouldConvertEditor() {
		// when
		testObj.convertEditor();

		// then
		verify(textEditorJSWrapper).convert(MODULE_ID);
	}

	@Test
	public void shouldSetContentToEditor() {
		// when
		testObj.setContent(CONTENT);

		// then
		verify(textEditorJSWrapper).setContent(MODULE_ID, CONTENT);
	}

	@Test
	public void shouldGetContentFromEditor() {
		// given
		String expected = "expected";
		when(textEditorJSWrapper.getContent(MODULE_ID)).thenReturn(expected);

		// when
		String actual = testObj.getContent();

		// then
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void shouldLockView() {
		// when
		testObj.lock();

		// then
		verify(textEditorView).lock();
	}
}
