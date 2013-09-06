package eu.ydp.empiria.player.client.module.drawing.command;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.module.drawing.view.CanvasView;

@RunWith(MockitoJUnitRunner.class)
public class ClearAllDrawCommandTest {
	@Mock private CanvasView view;
	@InjectMocks private ClearAllDrawCommand instance;

	@Test
	public void execute() throws Exception {
		instance.execute();
		verify(view).clear();
		verifyNoMoreInteractions(view);
	}
}
