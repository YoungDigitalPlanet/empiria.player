package eu.ydp.empiria.player.client.gin.factory;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.multiview.MultiPageController;
import eu.ydp.empiria.player.client.module.connection.ConnectionModule;
import eu.ydp.empiria.player.client.module.dragdrop.DragDropManager;
import eu.ydp.empiria.player.client.module.object.ObjectModule;
import eu.ydp.empiria.player.client.module.pageinpage.PageInPageModule;
import eu.ydp.empiria.player.client.module.sourcelist.SourceListModule;

public class ModuleProviderFactory {
	@Inject
	protected Provider<ConnectionModule> connectionModule;

	@Inject
	protected Provider<DragDropManager> dragDropManager;

	@Inject
	protected Provider<SourceListModule> sourceListModule;

	@Inject
	protected Provider<ObjectModule> objectModule;

	@Inject
	protected Provider<PageInPageModule> pageInPageModule;

	@Inject
	protected Provider<MultiPageController> multiPageController;

	public Provider<ConnectionModule> getConnectionModule() {
		return connectionModule;
	}

	public Provider<DragDropManager> getDragDropManager() {
		return dragDropManager;
	}

	public Provider<SourceListModule> getSourceListModule() {
		return sourceListModule;
	}

	public Provider<ObjectModule> getObjectModule() {
		return objectModule;
	}

	public Provider<PageInPageModule> getPageInPageModule() {
		return pageInPageModule;
	}

	public Provider<MultiPageController> getMultiPageController() {
		return multiPageController;
	}
}
