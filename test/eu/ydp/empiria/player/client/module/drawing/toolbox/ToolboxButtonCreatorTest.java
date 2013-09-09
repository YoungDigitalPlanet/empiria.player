package eu.ydp.empiria.player.client.module.drawing.toolbox;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.color.ColorModel;
import eu.ydp.empiria.player.client.module.drawing.toolbox.view.ToolboxButton;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

@RunWith(MockitoJUnitRunner.class)
public class ToolboxButtonCreatorTest {

	@InjectMocks
	ToolboxButtonCreator buttonCreator;
	@Mock
	private Provider<ToolboxButton> buttonProvider;
	@Mock
	private UserInteractionHandlerFactory userInteractionHandlerFactory;
	@Mock
	private ToolboxPresenter presenter;

	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}
	
	@AfterClass
	public static void arm() {
		GWTMockUtilities.restore();
	}

	@Test
	public void shouldCreateButton() {
		// given
		ColorModel colorModel = ColorModel.createFromRgba(100, 100, 100, 100);
		when(buttonProvider.get()).thenReturn(mock(ToolboxButton.class));

		ArgumentCaptor<Command> commandArg = ArgumentCaptor.forClass(Command.class);

		// when
		ToolboxButton paletteButton = buttonCreator.createPaletteButton(colorModel);

		// then
		verify(userInteractionHandlerFactory).applyUserClickHandler(commandArg.capture(), eq(paletteButton));
		commandArg.getValue().execute(mock(NativeEvent.class));
		verify(presenter).colorClicked(colorModel);
	}
}
