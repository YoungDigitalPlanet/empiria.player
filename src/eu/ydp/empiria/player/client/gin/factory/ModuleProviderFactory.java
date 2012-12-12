package eu.ydp.empiria.player.client.gin.factory;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.ImageActionProcessor;
import eu.ydp.empiria.player.client.module.TextActionProcessor;
import eu.ydp.empiria.player.client.module.connection.ConnectionModule;
import eu.ydp.empiria.player.client.module.img.ImgModule;
import eu.ydp.empiria.player.client.module.object.ObjectModule;
import eu.ydp.empiria.player.client.module.pageinpage.PageInPageModule;
import eu.ydp.empiria.player.client.module.selection.SelectionModule;
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

	@Inject
	protected Provider<ImageActionProcessor> imageActionProcessor;

	@Inject
	protected Provider<ImgModule> imgModule;
	
	@Inject
	protected Provider<SelectionModule> selectionModule;

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

	public Provider<ImageActionProcessor> getImageActionProcessor() {
		return imageActionProcessor;
	}

	public Provider<ImgModule> getImgModule() {
		return imgModule;
	}

	public Provider<SelectionModule> getSelectionModule() {
		return selectionModule;
	}

}
