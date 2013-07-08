package eu.ydp.empiria.player.client.module.draggap;

import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.AbstractInteractionModule;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistClient;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.draggap.dragging.DragDropController;
import eu.ydp.empiria.player.client.module.draggap.presenter.DragGapPresenter;
import eu.ydp.empiria.player.client.module.draggap.structure.DragGapBean;
import eu.ydp.empiria.player.client.module.draggap.structure.DragGapStructure;
import eu.ydp.empiria.player.client.module.view.HasDimensions;
import eu.ydp.gwtutil.client.StringUtils;
import eu.ydp.gwtutil.client.Wrapper;

public class DragGapModule extends AbstractInteractionModule<DragGapModule, DragGapModuleModel, DragGapBean> implements SourcelistClient {

	@Inject
	private DragGapPresenter dragGapPresenter;
	@Inject
	private DragGapStructure dragGapStructure;
	@Inject
	@ModuleScoped
	private DragGapModuleModel dragGapModuleModel;
	@Inject @PageScoped
	private SourcelistManager sourcelistManager;
	@Inject 
	private DragDropController dragDropController;
	
	
	private Wrapper<String> itemIdWrapper = Wrapper.of(StringUtils.EMPTY_STRING);

	@Override
	protected ActivityPresenter<DragGapModuleModel, DragGapBean> getPresenter() {
		return dragGapPresenter;
	}

	@Override
	protected void initalizeModule() {
		dragGapModuleModel.initialize(this);
		dragDropController.initializeDrop(getIdentifier());
		dragDropController.initializeDrag(getIdentifier(), itemIdWrapper);
		sourcelistManager.registerModule(this);
	}

	@Override
	public void reset() {
		super.reset();
		itemIdWrapper = Wrapper.of(StringUtils.EMPTY_STRING);
		sourcelistManager.onUserValueChanged();
	}

	@Override
	protected DragGapModuleModel getResponseModel() {
		return dragGapModuleModel;
	}

	@Override
	protected AbstractModuleStructure<DragGapBean, ? extends JAXBParserFactory<DragGapBean>> getStructure() {
		return dragGapStructure;
	}

	@Override
	public String getDragItemId() {
		return itemIdWrapper.getInstance();
	}

	@Override
	public void setDragItem(String itemId) {
		this.itemIdWrapper = Wrapper.of(itemId);
		SourcelistItemValue itemContent = sourcelistManager.getValue(itemId, getIdentifier());
		dragGapPresenter.setContent(itemContent.getContent());
	}

	@Override
	public void removeDragItem() {
		dragGapPresenter.removeContent();
		itemIdWrapper = Wrapper.of(StringUtils.EMPTY_STRING);
	}

	@Override
	public void lockDropZone() {
		dragDropController.lockDropZone();
	}

	@Override
	public void unlockDropZone() {
		dragDropController.unlockDropZone();
	}

	@Override
	public void setSize(HasDimensions size) {
		dragGapPresenter.setGapDimensions(size);
	}
}
