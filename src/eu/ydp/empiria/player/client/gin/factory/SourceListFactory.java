package eu.ydp.empiria.player.client.gin.factory;

import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.sourcelist.view.SourceListViewItem;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;

public interface SourceListFactory {
	SourceListViewItem getSourceListViewItem(@Assisted DragDataObject dragDataObject, @Assisted IModule parentModule);
}
