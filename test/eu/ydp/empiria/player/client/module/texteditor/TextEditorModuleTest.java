package eu.ydp.empiria.player.client.module.texteditor;

import eu.ydp.empiria.player.client.module.texteditor.presenter.TextEditorPresenter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TextEditorModuleTest {

	@InjectMocks
	private TextEditorModule testObj;

	@Mock
	private TextEditorPresenter presenter;

	@Test
	public void chouldConvertEditorOnBodyLoad() {
		// when
		testObj.onBodyLoad();

		// then
		verify(presenter).convertEditor();
	}
}
