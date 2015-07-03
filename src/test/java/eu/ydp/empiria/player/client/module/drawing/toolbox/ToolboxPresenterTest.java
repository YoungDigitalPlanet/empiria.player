package eu.ydp.empiria.player.client.module.drawing.toolbox;

import com.google.common.collect.Lists;
import com.google.inject.Key;
import eu.ydp.empiria.player.client.AbstractTestWithMocksBase;
import eu.ydp.empiria.player.client.module.drawing.command.DrawCommand;
import eu.ydp.empiria.player.client.module.drawing.command.DrawCommandFactory;
import eu.ydp.empiria.player.client.module.drawing.model.ColorBean;
import eu.ydp.empiria.player.client.module.drawing.model.DrawingBean;
import eu.ydp.empiria.player.client.module.drawing.model.PaletteBean;
import eu.ydp.empiria.player.client.module.drawing.toolbox.model.ToolboxModel;
import eu.ydp.empiria.player.client.module.drawing.toolbox.model.ToolboxModelImpl;
import eu.ydp.empiria.player.client.module.drawing.toolbox.tool.Tool;
import eu.ydp.empiria.player.client.module.drawing.toolbox.tool.ToolFactory;
import eu.ydp.empiria.player.client.module.drawing.view.CanvasPresenter;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import java.util.List;

import static eu.ydp.empiria.player.client.module.drawing.command.DrawCommandType.CLEAR_ALL;
import static eu.ydp.empiria.player.client.module.model.color.ColorModel.createFromRgbString;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ToolboxPresenterTest extends AbstractTestWithMocksBase {

    private ToolboxPresenter presenter;
    private DrawingBean bean;
    private ToolboxView view;
    private ToolFactory toolFactory;
    private CanvasPresenter canvasPresenter;
    private DrawCommandFactory drawCommandFactory;

    @Override
    public void setUp() {
        super.setUp(ToolboxModel.class, PaletteColorsProvider.class);
        presenter = injector.getInstance(ToolboxPresenter.class);
        bean = injector.getInstance(Key.get(DrawingBean.class, ModuleScoped.class));
        when(bean.getPalette()).thenReturn(createPalette());
        view = injector.getInstance(Key.get(ToolboxView.class, ModuleScoped.class));
        toolFactory = injector.getInstance(ToolFactory.class);
        canvasPresenter = injector.getInstance(Key.get(CanvasPresenter.class, ModuleScoped.class));
        drawCommandFactory = injector.getInstance(DrawCommandFactory.class);
    }

    @Test
    public void init() {
        // given
        Tool tool = mock(Tool.class);
        when(toolFactory.createTool(any(ToolboxModelImpl.class))).thenReturn(tool);

        // when
        presenter.init();

        // then
        ArgumentCaptor<List<ColorModel>> ac = (ArgumentCaptor) ArgumentCaptor.forClass(List.class);
        verify(view).setPalette(ac.capture());
        assertThat(ac.getValue()).containsExactly(createFromRgbString("00FF00"), createFromRgbString("00FFFF"), createFromRgbString("000DAF"));
        verify(view).setPresenterAndBind(presenter);
        verify(view).selectPencil();
        verify(view).setPaletteColor(createFromRgbString("00FF00"));
        verify(canvasPresenter, times(2)).setTool(tool);
    }

    @Test
    public void colorClicked() {
        // given
        Tool tool = mock(Tool.class);
        when(toolFactory.createTool(any(ToolboxModelImpl.class))).thenReturn(tool);
        ColorModel colorModel = ColorModel.createFromRgbString("FFAADD");
        presenter.paletteClicked();

        // when
        presenter.colorClicked(colorModel);

        // then
        verify(canvasPresenter).setTool(eq(tool));
        ArgumentCaptor<ToolboxModelImpl> ac = ArgumentCaptor.forClass(ToolboxModelImpl.class);
        verify(toolFactory).createTool(ac.capture());
        assertThat(ac.getValue().getColorModel()).isEqualTo(colorModel);
        verify(view).hidePalette();
        verify(view).setPaletteColor(eq(colorModel));
    }

    @Test
    public void showAndHideAndShowPalette() {
        // when
        presenter.paletteClicked();
        presenter.paletteClicked();
        presenter.paletteClicked();

        // then
        InOrder order = inOrder(view);
        order.verify(view).showPalette();
        order.verify(view).hidePalette();
        order.verify(view).showPalette();
    }

    @Test
    public void showPaletteAndHideOnColorClicked() {
        // when
        ColorModel colorModel = ColorModel.createFromRgbString("FFAADD");
        presenter.paletteClicked();
        presenter.colorClicked(colorModel);

        // then
        InOrder order = inOrder(view);
        order.verify(view).showPalette();
        order.verify(view).hidePalette();
    }

    @Test
    public void pencilClicked() {
        // given
        Tool tool = mock(Tool.class);
        when(toolFactory.createTool(any(ToolboxModelImpl.class))).thenReturn(tool);

        // when
        presenter.pencilClicked();

        // then
        verify(canvasPresenter).setTool(eq(tool));
        ArgumentCaptor<ToolboxModelImpl> ac = ArgumentCaptor.forClass(ToolboxModelImpl.class);
        verify(toolFactory).createTool(ac.capture());
        assertThat(ac.getValue().getToolType()).isEqualTo(ToolType.PENCIL);
        verify(view).selectPencil();
    }

    @Test
    public void eraserClicked() {
        // given
        Tool tool = mock(Tool.class);
        when(toolFactory.createTool(any(ToolboxModelImpl.class))).thenReturn(tool);

        // when
        presenter.eraserClicked();

        // then
        verify(canvasPresenter).setTool(eq(tool));
        ArgumentCaptor<ToolboxModelImpl> ac = ArgumentCaptor.forClass(ToolboxModelImpl.class);
        verify(toolFactory).createTool(ac.capture());
        assertThat(ac.getValue().getToolType()).isEqualTo(ToolType.ERASER);
        verify(view).selectEraser();
    }

    @Test
    public void clearAllClicked() {
        // given
        DrawCommand command = mock(DrawCommand.class);
        when(drawCommandFactory.createCommand(CLEAR_ALL)).thenReturn(command);

        // when
        presenter.clearAllClicked();

        // then
        verify(command).execute();
    }

    @Test
    public void getView() {
        // when
        ToolboxView result = presenter.getView();

        // then
        assertThat(view).isSameAs(result);
    }

    private static PaletteBean createPalette() {
        List<ColorBean> colors = Lists.newArrayList(createColorBean("00FF00"), createColorBean("00FFFF"), createColorBean("000DAF"));
        PaletteBean palette = new PaletteBean();
        palette.setColors(colors);
        return palette;
    }

    private static ColorBean createColorBean(String rgb) {
        ColorBean color2 = new ColorBean();
        color2.setRgb(rgb);
        return color2;
    }
}
