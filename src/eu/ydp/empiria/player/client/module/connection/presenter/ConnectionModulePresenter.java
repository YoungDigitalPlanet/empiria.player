package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.connection.ConnectionModuleModel;
import eu.ydp.empiria.player.client.module.connection.structure.MatchInteractionBean;

public interface ConnectionModulePresenter extends ActivityPresenter<ConnectionModuleModel, MatchInteractionBean> {
	
	IsWidget getFeedbackPlaceholderByIdentifier(String identifier);

	void setModuleSocket(ModuleSocket moduleSocket);
	
}
