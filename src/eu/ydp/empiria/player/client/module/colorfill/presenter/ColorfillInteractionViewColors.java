package eu.ydp.empiria.player.client.module.colorfill.presenter;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.colorfill.model.ColorModel;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ColorfillInteractionViewColors {

	@Inject @ModuleScoped
	private ColorfillInteractionView interactionView;
	
	public Map<Area, ColorModel> getColors(List<Area> areas){
		Map<Area, ColorModel> colors = Maps.newHashMap();
		for (Area area : areas){
			ColorModel color = interactionView.getColor(area);
			colors.put(area, color);
		}
		return colors;
	}
}
