package eu.ydp.empiria.player.client.module.drawing.toolbox;

import static eu.ydp.empiria.player.client.module.model.color.ColorModel.createFromRgbString;
import static org.fest.assertions.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.module.drawing.model.ColorBean;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;

public class ColorBeanConverterTest {

	private final ColorBeanConverter converter = new ColorBeanConverter();

	@Test
	public void convert() {
		// given
		List<ColorBean> colorBeans = Lists.newArrayList(createColorBean("00FF00"), createColorBean("00FFFF"), createColorBean("000DAF"));

		// when
		List<ColorModel> models = converter.convert(colorBeans);

		// then
		assertThat(models).containsExactly(createFromRgbString("00FF00"), createFromRgbString("00FFFF"), createFromRgbString("000DAF"));
	}

	private static ColorBean createColorBean(String rgb) {
		ColorBean color2 = new ColorBean();
		color2.setRgb(rgb);
		return color2;
	}

}
