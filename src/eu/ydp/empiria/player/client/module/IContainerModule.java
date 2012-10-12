package eu.ydp.empiria.player.client.module;

import com.google.gwt.user.client.ui.HasWidgets;

public interface IContainerModule extends ISingleViewWithBodyModule, HasChildren {

	public HasWidgets getContainer();
}
