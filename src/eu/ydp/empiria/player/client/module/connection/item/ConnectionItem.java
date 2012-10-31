package eu.ydp.empiria.player.client.module.connection.item;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;

public interface ConnectionItem extends IsWidget {

	public PairChoiceBean getBean();

	public void reset();

	public void setConnected(boolean connected);

	public int getRelativeX();

	public int getRelativeY();

	public int getOffsetLeft();

	public int getOffsetTop();

	public int getWidth();

	public int getHeight();

	/**
	 * Czy element znajduje sie na pozycji x y
	 * @param xPos
	 * @param yPos
	 * @return
	 */
	public boolean isOnPosition(int xPos, int yPos);

}