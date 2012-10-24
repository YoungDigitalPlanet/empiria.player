package eu.ydp.empiria.player.client.module.connection.presenter;

import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleView;
import eu.ydp.empiria.player.client.module.connection.ConnectionModuleModel;
import eu.ydp.empiria.player.client.module.connection.structure.MatchInteractionBean;

public interface ConnectionModulePresenter extends ActivityPresenter<ConnectionModuleModel, MatchInteractionBean> {
		
	void setModuleView(MultiplePairModuleView moduleView);
	
}
