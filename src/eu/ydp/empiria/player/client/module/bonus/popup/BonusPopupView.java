package eu.ydp.empiria.player.client.module.bonus.popup;

import com.google.gwt.user.client.ui.IsWidget;
import com.mathplayer.player.geom.Size;

public interface BonusPopupView {
	void showImage(String url, Size size);
	void setAnimationWidget(IsWidget widget, Size size);
	void attachToRoot();
	void reset();
}
