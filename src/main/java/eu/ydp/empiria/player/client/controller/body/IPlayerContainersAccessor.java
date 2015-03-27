package eu.ydp.empiria.player.client.controller.body;

import com.google.gwt.user.client.ui.HasWidgets;

public interface IPlayerContainersAccessor {

	void registerItemBodyContainer(int itemIndex, HasWidgets container);

	HasWidgets getItemBodyContainer(int itemIndex);

	void setPlayerContainer(HasWidgets playerContainer);

	HasWidgets getPlayerContainer();
}
