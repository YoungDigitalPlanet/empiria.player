package eu.ydp.empiria.player.client.module.texteditor.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.texteditor.model.Model;
import eu.ydp.empiria.player.client.module.texteditor.view.TextEditorView;
import eu.ydp.empiria.player.client.module.texteditor.wrapper.TextEditorJSWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class TextEditorPresenterTest {

	@InjectMocks
	private TextEditorPresenter testObj;

	@Mock
	private TextEditorJSWrapper textEditorJSWrapper;
	@Mock
	private TextEditorView view;

	private static final String MODULE_ID = "MODULE_ID";
	private static final String CONTENT = "CONTENT";

	@Before
	public void setUp() {
		testObj.init(MODULE_ID);
	}

	@Test
	public void shouldInitView() {
		// then
		verify(view).init();
	}

	@Test
	public void shouldConvertEditor() {
		// when
		testObj.convertEditor();

		// then
		verify(textEditorJSWrapper).convert(MODULE_ID);
	}

	@Test
	public void shouldSetContentFromModel() {
		// given
		Model model = new Model(CONTENT);

		// when
		testObj.setModel(model);

		// then
		verify(textEditorJSWrapper).setContent(MODULE_ID, CONTENT);
	}

	@Test
	public void shouldGetModelWithContent() {
		// given
		Model expectedModel = new Model(CONTENT);

		when(textEditorJSWrapper.getContent(MODULE_ID)).thenReturn(CONTENT);

		// when
		Model actual = testObj.getModel();

		// then
		assertThat(actual).isEqualTo(expectedModel);
	}

	@Test
	public void shouldLockViewAndEditor() {
		// when
		testObj.lock();

		// then
		verify(textEditorJSWrapper).lock(MODULE_ID);
		verify(view).lock();
	}

	@Test
	public void shouldUnlockViewAndEditor() {
		// when
		testObj.unlock();

		// then
		verify(textEditorJSWrapper).unlock(MODULE_ID);
		verify(view).unlock();
	}

	@Test
	public void shouldReturnViewWidget() {
		// given
		Widget widget = mock(Widget.class);
		when(view.asWidget()).thenReturn(widget);

		// when
		Widget actual = testObj.getView();

		// then
		assertThat(actual).isEqualTo(widget);
	}
}
