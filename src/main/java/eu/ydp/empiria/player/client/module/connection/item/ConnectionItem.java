package eu.ydp.empiria.player.client.module.connection.item;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;

public interface ConnectionItem extends IsWidget {
	public static enum Column {
		LEFT, RIGHT
	}

	public PairChoiceBean getBean();

	public void reset();

	public void setConnected(boolean connected, MultiplePairModuleConnectType connectType);

	public int getRelativeX();

	public int getRelativeY();

	public int getOffsetLeft();

	public int getOffsetTop();

	public int getWidth();

	public int getHeight();
}