package eu.ydp.empiria.player.client.controller.extensions.internal.stickies;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;

public interface IStickieView extends HasText, IsWidget {

	void setPresenter(IPresenter presenter);
	
	void setViewParent(HasWidgets parent);
	
	void setMinimized(boolean minimized);
	
	void setColorIndex(int colorIndex);
	
	void remove();
	
	int getX();
	
	int getY();
	
	void setX(int x);
	
	void setY(int y);
	
	void setPositionRaw(double x, double y);
	
	void centerView();
	
	public static interface IPresenter {
		
		void stickieMinimize();
		
		void stickieDelete();
		
		void stickieChange();
	}
}