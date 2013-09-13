package eu.ydp.empiria.player.client.controller.extensions.internal.bonus;

import static eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusResourceType.IMAGE;
import eu.ydp.gwtutil.client.util.geom.Size;


public class BonusResource {

	public String getAsset(){
		return "mock asset";
	}
	
	public BonusResourceType getType(){
		return IMAGE;
	}
	
	public Size getSize(){
		return new Size(666, 666);
	}
}
