package eu.ydp.empiria.player.client.controller.body;

import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ISingleViewWithBodyModule;

public interface ParenthoodSocket {

	public void addChild(IModule child);
	
	public void pushParent(ISingleViewWithBodyModule parent);
	
	public void popParent();
	
}
