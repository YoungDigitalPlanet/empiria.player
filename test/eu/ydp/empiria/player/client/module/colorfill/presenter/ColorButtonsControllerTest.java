package eu.ydp.empiria.player.client.module.colorfill.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.fest.assertions.api.Assertions.*;

import eu.ydp.empiria.player.client.module.colorfill.model.ColorModel;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionView;


public class ColorButtonsControllerTest {

	private ColorButtonsController colorButtonsController;
	private ColorfillInteractionView interactionView;
	
	@Before
	public void setUp() throws Exception {
		interactionView = Mockito.mock(ColorfillInteractionView.class);
		colorButtonsController = new ColorButtonsController(interactionView);
	}

	@Test
	public void shouldSelectClickedColor() throws Exception {
		ColorModel color = ColorModel.createFromRgbString("00ff00");
		
		colorButtonsController.colorButtonClicked(color);
		
		assertThat(colorButtonsController.getCurrentSelectedButtonColor()).isEqualTo(color);
	}
	
	@Test
	public void shouldSelectAndDeselectButtonWhenDoubleClicked() throws Exception {
		ColorModel color = ColorModel.createFromRgbString("00ff00");
		
		colorButtonsController.colorButtonClicked(color);
		colorButtonsController.colorButtonClicked(color);
		
		assertThat(colorButtonsController.getCurrentSelectedButtonColor()).isEqualTo(null);
	}
	
	@Test
	public void shouldSelectColorAndReplaceItWithNewClicked() throws Exception {
		ColorModel color = ColorModel.createFromRgbString("00ff00");
		ColorModel newColor = ColorModel.createFromRgbString("ff00ff");
		
		colorButtonsController.colorButtonClicked(color);
		colorButtonsController.colorButtonClicked(newColor);
		
		assertThat(colorButtonsController.getCurrentSelectedButtonColor()).isEqualTo(newColor);
	}
	
}
