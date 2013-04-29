package eu.ydp.empiria.player.client.module;

import com.google.gwt.user.client.ui.HasWidgets;

import eu.ydp.empiria.player.client.module.containers.SimpleContainerModuleBase;

public interface IContainerModule extends ISingleViewWithBodyModule, HasChildren {

	/**
	 * @deprecated used only in {@link SimpleContainerModuleBase} - should be removed
	 */
	@Deprecated
	public HasWidgets getContainer();
}
