package eu.ydp.empiria.player.client.module.drawing.toolbox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.drawing.toolbox.view.ToolboxButton;
import eu.ydp.empiria.player.client.module.drawing.toolbox.view.ToolboxPalette;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;

import java.util.List;

public class ToolboxViewImpl extends Composite implements ToolboxView {

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
    }

    @UiHandler("pencilButton")
    void pencilButtonClicked(ClickEvent e) {
        presenter.pencilClicked();
    }

    @UiHandler("paletteButton")
    void paletteButtonClicked(ClickEvent e) {
        presenter.paletteClicked();
    }

    @UiHandler("eraserButton")
    void eraserButtonClicked(ClickEvent e) {
        presenter.eraserClicked();
    }

    @UiHandler("clearAllButton")
    void clearAllButtonClicked(ClickEvent e) {
        presenter.clearAllClicked();
    }
}
