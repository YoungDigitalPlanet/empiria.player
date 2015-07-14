package eu.ydp.empiria.player.client.module.drawing.toolbox;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;

import java.util.List;

public interface ToolboxView extends IsWidget {
    void showPalette();

    void hidePalette();

    void selectPencil();

    void selectEraser();

    void setPaletteColor(ColorModel colorModel);

    void setPalette(List<ColorModel> colorModel);

    void setPresenterAndBind(ToolboxPresenter toolboxPresenter);
}
