package eu.ydp.empiria.player.client.gin.factory;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.TextActionProcessor;
import eu.ydp.empiria.player.client.module.connection.ConnectionModule;
import eu.ydp.empiria.player.client.module.object.ObjectModule;
import eu.ydp.empiria.player.client.module.pageinpage.PageInPageModule;
import eu.ydp.empiria.player.client.module.sourcelist.SourceListModule;

public class ModuleProviderFactory {
	@Inject
	protected Provider<ConnectionModule> connectionModule;

	@Inject
	protected Provider<SourceListModule> sourceListModule;

	@Inject
	protected Provider<ObjectModule> objectModule;

	@Inject
	protected Provider<PageInPageModule> pageInPageModule;
	
	@Inject
	protected Provider<TextActionProcessor> textActionProcessor;

	public Provider<ConnectionModule> getConnectionModule() {
		return connectionModule;
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
	
	public Provider<TextActionProcessor> getTextActionProcessor() {
		return textActionProcessor;
	}
}
