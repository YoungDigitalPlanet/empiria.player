package eu.ydp.empiria.player.client.view;

import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.view.player.PlayerContentView;
import eu.ydp.empiria.player.client.view.player.PlayerViewSocket;

public class ViewEngine {

	@Inject
	public ViewEngine(PlayerContentView pcv){
		playerView = pcv;
	}

	public void mountView(ComplexPanel container){
		container.add(playerView);
	}

	private final PlayerContentView playerView;

	public PlayerViewSocket getPlayerViewSocket(){
		return playerView;
	}



}
