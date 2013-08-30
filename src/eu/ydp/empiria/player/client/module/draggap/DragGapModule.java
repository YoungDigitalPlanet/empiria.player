package eu.ydp.empiria.player.client.module.draggap;

import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

import eu.ydp.empiria.player.client.module.AbstractInteractionModule;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistClient;
import eu.ydp.empiria.player.client.module.draggap.dragging.DragDropController;
import eu.ydp.empiria.player.client.module.draggap.presenter.DragGapPresenter;
import eu.ydp.empiria.player.client.module.draggap.structure.DragGapBean;
import eu.ydp.empiria.player.client.module.draggap.structure.DragGapStructure;
import eu.ydp.gwtutil.client.StringUtils;
import eu.ydp.gwtutil.client.Wrapper;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;

public class DragGapModule extends AbstractInteractionModule<DragGapModule, DragGapModuleModel, DragGapBean> implements SourcelistClient {

	@Inject
	private DragGapPresenter dragGapPresenter;
	@Inject
	private DragGapStructure dragGapStructure;
	@Inject
	@ModuleScoped
	private DragGapModuleModel dragGapModuleModel;
	@Inject @ModuleScoped
	private SourceListManagerAdapter sourceListManagerAdapter;
	@Inject
	private DragDropController dragDropController;


	private final Wrapper<String> itemIdWrapper = Wrapper.of(StringUtils.EMPTY_STRING);

	@Override
	protected ActivityPresenter<DragGapModuleModel, DragGapBean> getPresenter() {
		return dragGapPresenter;
	}

	@Override
	protected void initalizeModule() {
		sourceListManagerAdapter.initialize(getIdentifier());
		dragGapModuleModel.initialize(this);
		dragDropController.initializeDrop(getIdentifier());
		dragDropController.initializeDrag(getIdentifier(), itemIdWrapper);
		sourceListManagerAdapter.registerModule(this);
	}

	@Override
	public void reset() {
		super.reset();
		itemIdWrapper.setInstance(StringUtils.EMPTY_STRING);
		sourceListManagerAdapter.onUserValueChanged();
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
		itemIdWrapper.setInstance(itemId);
		dragGapPresenter.setContent(itemId);
	}

	@Override
	public void removeDragItem() {
		dragGapPresenter.removeContent();
		itemIdWrapper.setInstance(StringUtils.EMPTY_STRING);
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

	@Override
	public void lock(boolean lock) {
		super.lock(lock);
		if (lock) {
			sourceListManagerAdapter.lockGroup();
		} else {
			sourceListManagerAdapter.unlockGroup();
			dragDropController.unlockDropZone();
		}
	}

	@Override
	public String getSourcelistId() {
		DragGapBean dragGapBean = getStructure().getBean();
		return dragGapBean.getSourcelistId();
	}

}
