package eu.ydp.empiria.player.client.controller.communication;

import java.util.ArrayList;

public class InitialData {

	ArrayList<InitialItemData> itemInitialDatas;

	public InitialData(int itemsCount) {
		itemInitialDatas = new ArrayList<InitialItemData>(itemsCount);
	}

	public void addItemInitialData(InitialItemData data) {
		itemInitialDatas.add(data);
	}

	public InitialItemData getItemInitialData(int index) {
		return itemInitialDatas.get(index);
	}
}
