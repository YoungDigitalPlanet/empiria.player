package eu.ydp.empiria.player.client.module.colorfill;

import java.util.List;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.module.colorfill.model.ColorModel;
import eu.ydp.empiria.player.client.module.colorfill.presenter.ColorfillInteractionPresenter;
import eu.ydp.empiria.player.client.module.colorfill.presenter.handlers.AreaClickListener;
import eu.ydp.empiria.player.client.module.colorfill.presenter.handlers.ColorButtonClickListener;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorButton;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorfillInteractionBean;
import eu.ydp.empiria.player.client.module.colorfill.structure.Image;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionView;

public class ColorfillViewBuilder {

	private final ColorfillInteractionView interactionView;
	private ColorfillInteractionPresenter interactionPresenter;

	@Inject
	public ColorfillViewBuilder(@ModuleScoped ColorfillInteractionView interactionView) {
		this.interactionView = interactionView;
	}

	public void buildView(ColorfillInteractionBean bean,ColorfillInteractionPresenter interactionPresenter) {
			this.interactionPresenter = interactionPresenter;
			List<ColorButton> buttons = bean.getButtons().getButtons();
			createButtons(buttons);

			setListenersOnView();

			Image image = bean.getImage();
			interactionView.setImage(image);
	}

	private void createButtons(List<ColorButton> buttons) {
		for (ColorButton colorButton : buttons) {
			ColorModel colorModel = ColorModel.createFromRgbString(colorButton.getRgb());
			interactionView.createButton(colorModel);
		}
	}

	private void setListenersOnView() {
		setButtonClickedListener();
		setImageColorChangedListener();
	}

	private void setImageColorChangedListener() {
		AreaClickListener areaClickListener = new AreaClickListener(interactionPresenter);
		interactionView.setAreaClickListener(areaClickListener);
	}

	private void setButtonClickedListener() {
		ColorButtonClickListener buttonClickListener = new ColorButtonClickListener(interactionPresenter);
		interactionView.setButtonClickListener(buttonClickListener);
	}
}
