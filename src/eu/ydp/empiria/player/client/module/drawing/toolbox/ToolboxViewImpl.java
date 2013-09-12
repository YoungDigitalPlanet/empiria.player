package eu.ydp.empiria.player.client.module.drawing.toolbox;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.color.ColorModel;
import eu.ydp.empiria.player.client.module.drawing.toolbox.view.ToolboxButton;
import eu.ydp.empiria.player.client.module.drawing.toolbox.view.ToolboxPalette;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

public class ToolboxViewImpl extends Composite implements ToolboxView {

	@Inject
	private UserInteractionHandlerFactory userInteractionHandlerFactory;
	private ToolboxPresenter presenter;

	private static ToolboxViewImplUiBinder uiBinder = GWT.create(ToolboxViewImplUiBinder.class);

	@UiTemplate("ToolboxView.ui.xml")
	interface ToolboxViewImplUiBinder extends UiBinder<Widget, ToolboxViewImpl> {
	}

	@UiField(provided = true)
	ToolboxButton pencilButton;
	@UiField(provided = true)
	ToolboxButton paletteButton;
	@UiField(provided = true)
	ToolboxButton eraserButton;
	@UiField(provided = true)
	ToolboxButton clearAllButton;
	@UiField(provided = true)
	ToolboxPalette palette;

	@Inject
	public ToolboxViewImpl(ToolboxButton pencilButton, ToolboxButton paletteButton, ToolboxButton eraserButton, ToolboxButton clearAllButton,
			ToolboxPalette palette) {
		this.pencilButton = pencilButton;
		this.paletteButton = paletteButton;
		this.eraserButton = eraserButton;
		this.clearAllButton = clearAllButton;
		this.palette = palette;
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void showPalette() {
		palette.show();
		paletteButton.select();
	}

	@Override
	public void hidePalette() {
		palette.hide();
		paletteButton.unselect();
	}

	@Override
	public void selectPencil() {
		unselectTools();
		pencilButton.select();
	}

	@Override
	public void selectEraser() {
		unselectTools();
		eraserButton.select();
	}

	private void unselectTools() {
		pencilButton.unselect();
		eraserButton.unselect();
	}

	@Override
	public void setPaletteColor(ColorModel colorModel) {
		paletteButton.setColor(colorModel);
	}

	@Override
	public void setPalette(List<ColorModel> colorModel) {
		palette.init(colorModel);
	}

	@Override
	public void setPresenterAndBind(ToolboxPresenter toolboxPresenter) {
		this.presenter = toolboxPresenter;
		bindEvents();
	}

	private void bindEvents() {
		userInteractionHandlerFactory.applyUserClickHandler(new Command() {

			@Override
			public void execute(NativeEvent event) {
				presenter.pencilClicked();
			}
		}, pencilButton);
		userInteractionHandlerFactory.applyUserClickHandler(new Command() {

			@Override
			public void execute(NativeEvent event) {
				presenter.paletteClicked();
			}
		}, paletteButton);
		userInteractionHandlerFactory.applyUserClickHandler(new Command() {

			@Override
			public void execute(NativeEvent event) {
				presenter.eraserClicked();
			}
		}, eraserButton);
		userInteractionHandlerFactory.applyUserClickHandler(new Command() {

			@Override
			public void execute(NativeEvent event) {
				presenter.clearAllClicked();
			}
		}, clearAllButton);
	}
}
