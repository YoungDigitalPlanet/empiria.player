package eu.ydp.empiria.player.client.module.connection.item;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;
import eu.ydp.empiria.player.client.module.connection.presenter.view.AbstractConnectionView;

public interface ConnectionItem extends IsWidget {

	public PairChoiceBean getBean();

	public void setConnectionView(AbstractConnectionView connectionView);

	public void reset();

	public void setConnected(boolean connected);

	public int getRelativeX();

	public int getRelativeY();

	public int getOffsetLeft();

	public int getOffsetTop();

	public int getWidth();

	public int getHeight();

}