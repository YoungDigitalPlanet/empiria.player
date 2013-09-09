package eu.ydp.empiria.player.client.module.drawing.toolbox;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.color.ColorModel;
import eu.ydp.empiria.player.client.module.drawing.toolbox.view.ToolboxButton;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

public class ToolboxButtonCreator {

	@Inject
	private Provider<ToolboxButton> buttonProvider;
	@Inject
	private UserInteractionHandlerFactory userInteractionHandlerFactory;
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

	private void addClickHandler(ColorModel colorModel, IsWidget toolboxButton) {
		Command command = createPaletteButtonClickCommand(colorModel);
		userInteractionHandlerFactory.applyUserClickHandler(command, toolboxButton);
	}

	private Command createPaletteButtonClickCommand(final ColorModel color) {
		return new Command() {

			@Override
			public void execute(NativeEvent event) {
				if (presenter != null) {
					presenter.colorClicked(color);
				}
			}
		};
	}

}
