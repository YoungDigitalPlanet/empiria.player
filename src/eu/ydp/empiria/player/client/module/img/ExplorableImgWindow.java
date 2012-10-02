package eu.ydp.empiria.player.client.module.img;

import com.google.gwt.user.client.ui.IsWidget;

public interface ExplorableImgWindow extends IsWidget {

	public void init(int windowWidth, int windowHeight, String imageUrl, double initialScale, double scaleStep, double zoomMax, String title);
	public void zoomIn();
	public void zoomOut();
}
