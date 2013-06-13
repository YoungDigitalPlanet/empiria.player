package eu.ydp.empiria.player.client.module.colorfill.structure;

import java.util.List;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;

public class ColorfillBeanProxy {

	@Inject @ModuleScoped
	private ColorfillInteractionStructure structure;
	
	public ColorfillInteractionBean getColofillBean(){
		return structure.getBean();
	}
	
	public List<Area> getAreas(){
		return structure.getBean().getAreas().getAreas();
	}
}
