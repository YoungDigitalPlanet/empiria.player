package eu.ydp.empiria.player.client.gin.factory;

import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.identification.SelectableChoice;

public interface IdentificationModuleFactory {

	SelectableChoice createSelectableChoice(String identifier, Widget contentWidget);
	
}
