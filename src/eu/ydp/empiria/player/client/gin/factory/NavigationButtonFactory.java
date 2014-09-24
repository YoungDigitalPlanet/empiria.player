package eu.ydp.empiria.player.client.gin.factory;

import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.button.NavigationButtonDirection;
import eu.ydp.empiria.player.client.module.button.NavigationButtonModule;

public interface NavigationButtonFactory {

	public NavigationButtonModule getNavigationButtonModule(@Assisted NavigationButtonDirection dir);
}
