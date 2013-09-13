package eu.ydp.empiria.player.client.module.drawing.toolbox;

import java.util.List;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.drawing.model.ColorBean;
import eu.ydp.empiria.player.client.module.drawing.model.DrawingBean;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class PaletteColorsProvider {

	@Inject @ModuleScoped private DrawingBean bean;
	@Inject private ColorBeanConverter converter;
	
	public List<ColorModel> getColors(){
		List<ColorBean> colorBeans = bean.getPalette().getColors();
		return converter.convert(colorBeans);
	}
	
}
