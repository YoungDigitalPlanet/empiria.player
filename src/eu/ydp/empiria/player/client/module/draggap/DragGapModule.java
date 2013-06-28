package eu.ydp.empiria.player.client.module.draggap;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

import eu.ydp.empiria.player.client.module.AbstractInteractionModule;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistClient;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.draggap.presenter.DragGapPresenter;
import eu.ydp.empiria.player.client.module.draggap.structure.DragGapBean;
import eu.ydp.empiria.player.client.module.draggap.structure.DragGapStructure;
import eu.ydp.empiria.player.client.module.draggap.view.DragGapDropHandler;
import eu.ydp.empiria.player.client.module.draggap.view.DragGapStartDragHandler;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.gwtutil.client.StringUtils;

public class DragGapModule extends AbstractInteractionModule<DragGapModule, DragGapModuleModel, DragGapBean> implements SourcelistClient {

	@Inject
	private DragGapModuleModel dragGapModuleModel;

	@Inject
	private DragGapPresenter dragGapPresenter;

	@Inject
	private DragGapStructure dragGapStructure;

	private SourcelistManager sourcelistManager;

	private Optional<DragDataObject> dragDataObjectOptional;

	@Override
	protected ActivityPresenter<DragGapModuleModel, DragGapBean> getPresenter() {
		return dragGapPresenter;
	}

	@Override
	protected void initalizeModule() {
		dragGapModuleModel.initialize(this);

		dragGapPresenter.setDropHandler(new DragGapDropHandler() {

			@Override
			public void onDrop(DragDataObject dragDataObject) {
				setDragDataObject(dragDataObject);
				String itemID = dragDataObject.getItemId();
				String sourceModuleId = dragDataObject.getSourceId();
				String targetModuleId = getIdentifier();

				sourcelistManager.dragEnd(itemID, sourceModuleId,
						targetModuleId);
			}
		});

		dragGapPresenter.setDragStartHandler(new DragGapStartDragHandler() {

			@Override
			public void onDragStart() {
				sourcelistManager.dragStart(getIdentifier());
			}
		});

		sourcelistManager.registerModule(this);
	}
	
	@Override
	public void reset() {
		super.reset();
		sourcelistManager.onUserValueChanged();
	}

	private void setDragDataObject(DragDataObject dragDataObject) {
		this.dragDataObjectOptional = Optional.fromNullable(dragDataObject);
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
		if(dragDataObjectOptional.isPresent()){
			return dragDataObjectOptional.get().getItemId();
		} else {
			//TODO zamiast empty stringa powinien byc zwracany optional
			return StringUtils.EMPTY_STRING;
		}
	}

	@Override
	public void setDragItem(String itemId) {
		String itemContent = sourcelistManager.getValue(itemId, getIdentifier());
		dragGapPresenter.setContent(itemContent);
	}

	@Override
	public void removeDragItem() {
		dragGapPresenter.removeContent();
	}

	@Override
	public void lockDropZone() {
		dragGapPresenter.lockDropZone();
	}

	@Override
	public void unlockDropZone() {
		dragGapPresenter.unlockDropZone();
	}
}
