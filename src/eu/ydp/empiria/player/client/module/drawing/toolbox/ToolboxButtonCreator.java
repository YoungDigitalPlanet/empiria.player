package eu.ydp.empiria.player.client.module.drawing.toolbox;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.drawing.toolbox.view.ToolboxButton;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;

public class ToolboxButtonCreator {

	@Inject
	private Provider<ToolboxButton> buttonProvider;
	private ToolboxPresenter presenter;

	public void setPresenter(ToolboxPresenter presenter) {
		this.presenter = presenter;
	}

	public ToolboxButton createPaletteButton(ColorModel colorModel) {
		ToolboxButton toolboxButton = buttonProvider.get();
		toolboxButton.setColor(colorModel);
		addClickHandler(colorModel, toolboxButton);
		return toolboxButton;
	}

	private void addClickHandler(ColorModel colorModel, ToolboxButton toolboxButton) {
		ClickHandler handler = createPaletteButtonClickHandler(colorModel);
		toolboxButton.addClickHandler(handler);
	}

	private ClickHandler createPaletteButtonClickHandler(final ColorModel color) {
		return new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (presenter != null) {
					presenter.colorClicked(color);
				}
			}
		};
	}

}
