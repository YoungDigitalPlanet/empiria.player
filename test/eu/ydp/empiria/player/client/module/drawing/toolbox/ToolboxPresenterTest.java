package eu.ydp.empiria.player.client.module.drawing.toolbox;

import static eu.ydp.empiria.player.client.color.ColorModel.createFromRgbString;
import static eu.ydp.empiria.player.client.module.drawing.command.DrawCommandType.CLEAR_ALL;
import static eu.ydp.gwtutil.test.MappedInjectorFactory.createInjector;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import eu.ydp.empiria.player.client.color.ColorModel;
import eu.ydp.empiria.player.client.module.drawing.command.DrawCommand;
import eu.ydp.empiria.player.client.module.drawing.command.DrawCommandFactory;
import eu.ydp.empiria.player.client.module.drawing.model.ColorBean;
import eu.ydp.empiria.player.client.module.drawing.model.DrawingBean;
import eu.ydp.empiria.player.client.module.drawing.model.ImageBean;
import eu.ydp.empiria.player.client.module.drawing.model.PaletteBean;
import eu.ydp.empiria.player.client.module.drawing.toolbox.model.ToolboxModel;
import eu.ydp.empiria.player.client.module.drawing.toolbox.model.ToolboxModelImpl;
import eu.ydp.empiria.player.client.module.drawing.toolbox.tool.Tool;
import eu.ydp.empiria.player.client.module.drawing.toolbox.tool.ToolFactory;
import eu.ydp.empiria.player.client.module.drawing.view.CanvasPresenter;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Ignore
public class ToolboxPresenterTest {

	private final ToolboxView view = mock(ToolboxView.class);
	private final ToolFactory toolFactory = mock(ToolFactory.class);
	private final CanvasPresenter canvasPresenter = mock(CanvasPresenter.class);
	private final ToolboxModel model = new ToolboxModelImpl();
	private final DrawingBean bean = prepareBean();
	private final DrawCommandFactory drawCommandFactory = mock(DrawCommandFactory.class);

	private ToolboxPresenter presenter;

	@Before
	public void setUp() {
		Map<Class, Object> map = Maps.newHashMap();
		map.put(ToolboxView.class, view);
		map.put(ToolFactory.class, toolFactory);
		map.put(CanvasPresenter.class, canvasPresenter);
		map.put(ToolboxModelImpl.class, model);
		map.put(DrawingBean.class, bean);
		map.put(DrawCommandFactory.class, drawCommandFactory);
		presenter = createInjector(map).getInstance(ToolboxPresenter.class);
	}

	@Test
	public void init() {
		// when
		presenter.init();

		// then
		ArgumentCaptor<List<ColorModel>> ac = (ArgumentCaptor)ArgumentCaptor.forClass(List.class);
		verify(view).setPalette(ac.capture());
		assertThat(ac.getValue()).containsExactly(createFromRgbString("00FF00"), createFromRgbString("00FFFF"), createFromRgbString("000DAF"));
	}

	@Test
	public void colorClicked() {
		// given
		Tool tool = mock(Tool.class);
		when(toolFactory.createTool(any(ToolboxModelImpl.class))).thenReturn(tool);
		ColorModel colorModel = ColorModel.createFromRgbString("FFAADD");

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

	private static DrawingBean prepareBean() {
		DrawingBean bean = new DrawingBean();
		bean.setId("id1");

		ImageBean img = new ImageBean();
		img.setSrc("image.jpg");
		img.setWidth(640);
		img.setHeight(480);
		bean.setImage(img);

		List<ColorBean> colors = Lists.newArrayList(createColorBean("00FF00"), createColorBean("00FFFF"), createColorBean("000DAF"));
		PaletteBean palette = new PaletteBean();
		palette.setColors(colors);
		bean.setPalette(palette);

		return bean;
	}

	private static ColorBean createColorBean(String rgb) {
		ColorBean color2 = new ColorBean();
		color2.setRgb(rgb);
		return color2;
	}
}
