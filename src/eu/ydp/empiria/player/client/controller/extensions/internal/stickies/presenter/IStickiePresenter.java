package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.presenter;

import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.IStickieView;
import eu.ydp.gwtutil.client.geom.Point;

public interface IStickiePresenter {

		void negateStickieMinimize();
		
		void deleteStickie();
		
		void updateStickieView();

		void centerPositionToView();
		
		void setView(IStickieView stickieView);

		void correctStickiePosition();

		void changeContentText(String textToAppend);

		void moveStickieToPosition(Point<Integer> newPosition);
}
