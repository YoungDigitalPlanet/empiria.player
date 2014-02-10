package eu.ydp.empiria.player.client.view.page;

import eu.ydp.empiria.player.client.view.item.ItemViewSocket;

public interface PageViewSocket {

	public void initItemViewSockets(int count);

	public ItemViewSocket getItemViewSocket(int index);

	public void setPageViewCarrier(PageViewCarrier pvc);
}
