package eu.ydp.empiria.player.client.controller.data;

import eu.ydp.empiria.player.client.controller.communication.InitialItemData;
import eu.ydp.empiria.player.client.controller.communication.ItemData;
import eu.ydp.empiria.player.client.controller.data.events.ItemDataCollectionLoaderEventListener;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import eu.ydp.gwtutil.client.collections.QueueSet;

public class ItemDataSourceCollectionManager {
	public ItemDataSourceCollectionManager(ItemDataCollectionLoaderEventListener l){
		listener = l;
	}
	
	private ItemDataSource[] items;
	private ItemDataCollectionLoaderEventListener listener;
	private int itemsLoadCounter;
	
	public void initItemDataCollection(int itemsCount){
		items = new ItemDataSource[itemsCount];
		itemsLoadCounter = 0;
	}
	
	public void setItemData(int index, XmlData d){
		items[index] = new ItemDataSource(d);
		itemsLoadCounter++;
		if (itemsLoadCounter == items.length)
			listener.onItemCollectionLoaded();
	}

	public void setItemData(int index, String error){
		items[index] = new ItemDataSource(error);
		itemsLoadCounter++;
		if (itemsLoadCounter == items.length)
			listener.onItemCollectionLoaded();
	}
	
	public void setItemDataCollection(XmlData[] ds){
		items = new ItemDataSource[ds.length];
		for ( int i = 0 ; i < items.length ; i ++){
			items[i] = new ItemDataSource(ds[i]);
		}
		listener.onItemCollectionLoaded();
	}

	public ItemData getItemData(int index){
		if (index >= items.length)
			return new ItemData(0, "There's no item of index " + String.valueOf(index));
		if (!items[index].isError())
			return new ItemData(index, items[index].getItemData());
		else
			return new ItemData(index, items[index].getErrorMessage());
	}
	
	public InitialItemData getItemInitialData(int index){
		if (index < items.length  &&  !items[index].isError())
			return new InitialItemData(items[index].getItemData());
		
		return new InitialItemData(null);
	}
	
	public String[] getTitlesList(){
		String[] titles = new String[items.length];
		for (int i = 0 ; i < items.length ; i ++){
			titles[i] = items[i].getTitle();
		}
		return titles;
	}
	
	public int getItemsCount(){
		if (items != null)
			return items.length;
		else
			return 0;
	}
	
	public QueueSet<String> getStyleLinksForUserAgent(int itemIndex, String userAgent){
		if (items != null && itemIndex<=items.length)
			return items[itemIndex].getStyleLinksForUserAgent(userAgent);
		return new QueueSet<String>();
	}
}
