package eu.ydp.empiria.player.client.module.colorfill.view;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

@RunWith(MockitoJUnitRunner.class)
public class PaletteButtonCreatorTest {

	@InjectMocks
	private PaletteButtonCreator creator;

	@Mock
	private UserInteractionHandlerFactory userInteractionHandlerFactory;

	@Mock
	private Provider<PaletteButton> buttonProvider;

	@Test
	public void createButton() {
		// given
		String description = "buttonDescription";
		ColorModel color = ColorModel.createEraser();
		Command command = mock(Command.class);
		when(buttonProvider.get()).thenReturn(mock(PaletteButton.class));

		// when
		PaletteButton button = creator.createButton(color, command, description);

		// then
		verify(button).setColor(eq(color));
		verify(button).setDescription(description);
		verify(userInteractionHandlerFactory).applyUserClickHandler(eq(command), eq(button));
	}

}
