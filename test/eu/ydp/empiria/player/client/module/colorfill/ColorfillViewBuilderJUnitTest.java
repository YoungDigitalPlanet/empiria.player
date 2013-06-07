package eu.ydp.empiria.player.client.module.colorfill;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import static org.mockito.Mockito.*;

import eu.ydp.empiria.player.client.module.colorfill.model.ColorModel;
import eu.ydp.empiria.player.client.module.colorfill.presenter.ColorfillInteractionPresenter;
import eu.ydp.empiria.player.client.module.colorfill.presenter.handlers.ColorButtonClickListener;
import eu.ydp.empiria.player.client.module.colorfill.structure.ButtonsContainer;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorButton;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorfillInteractionBean;
import eu.ydp.empiria.player.client.module.colorfill.structure.EraserButton;
import eu.ydp.empiria.player.client.module.colorfill.structure.Image;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillAreaClickListener;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionView;

@RunWith(MockitoJUnitRunner.class)
public class ColorfillViewBuilderJUnitTest {

	private ColorfillViewBuilder colorfillViewBuilder;
	
	@Mock
	private ColorfillInteractionView interactionView;
	@Mock
	private ColorfillInteractionPresenter interactionPresenter;
	
	@Before
	public void setUp() throws Exception {
		colorfillViewBuilder = new ColorfillViewBuilder(interactionView);
	}

	@Test
	public void shouldBuildImageAndButtons() throws Exception {
		//given
		ColorfillInteractionBean bean = new ColorfillInteractionBean();
		ButtonsContainer buttonsContainer = new ButtonsContainer();
		ColorButton colorButton = new ColorButton();
		colorButton.setRgb("00ff00");
		List<ColorButton> buttons = Lists.newArrayList(colorButton);
		buttonsContainer.setButtons(buttons);
		EraserButton eraserButton = new EraserButton();
		buttonsContainer.setEraserButton(eraserButton);
		bean.setButtons(buttonsContainer);
		Image image = new Image();
		bean.setImage(image);
		
		
		//when
		colorfillViewBuilder.buildView(bean, interactionPresenter);
		
		
		//then
		verify(interactionView).createButton(ColorModel.createFromRgbString("00ff00"));
		verify(interactionView).createButton(ColorfillViewBuilder.ERASING_COLOR);
		verify(interactionView).setAreaClickListener(any(ColorfillAreaClickListener.class));
		verify(interactionView).setButtonClickListener(any(ColorButtonClickListener.class));
		verify(interactionView).setImage(image);
	}
	
}
