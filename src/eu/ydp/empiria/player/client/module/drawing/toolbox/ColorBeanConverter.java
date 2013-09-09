package eu.ydp.empiria.player.client.module.drawing.toolbox;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;

import eu.ydp.empiria.player.client.color.ColorModel;
import eu.ydp.empiria.player.client.module.drawing.model.ColorBean;

public class ColorBeanConverter {

	private final Function<ColorBean, ColorModel> colorBeanTransformation = new Function<ColorBean, ColorModel>() {

		@Override
		public ColorModel apply(ColorBean color) {
			return ColorModel.createFromRgbString(color.getRgb());
		}
	};

	public List<ColorModel> convert(List<ColorBean> colorBeans){
		return newArrayList(Iterables.transform(colorBeans, colorBeanTransformation));
	}

}
