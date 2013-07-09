package eu.ydp.empiria.player.client.module.draggap;

import com.google.gwt.event.dom.client.DragEndEvent;
import com.google.gwt.event.dom.client.DragEndHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
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
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.dom.drag.NativeDragDataObject;
import eu.ydp.gwtutil.client.StringUtils;

public class DragGapModule extends AbstractInteractionModule<DragGapModule, DragGapModuleModel, DragGapBean> implements SourcelistClient {

	@Inject
	@ModuleScoped
	private DragGapModuleModel dragGapModuleModel;

	@Inject
	private DragGapPresenter dragGapPresenter;

	@Inject
	private DragGapStructure dragGapStructure;

	@Inject @PageScoped
	private SourcelistManager sourcelistManager;

	@Inject
	OverlayTypesParser overlayTypesParser;

	private String itemId = StringUtils.EMPTY_STRING;

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
				String itemID = dragDataObject.getItemId();
				String sourceModuleId = dragDataObject.getSourceId();
				String targetModuleId = getIdentifier();

				sourcelistManager.dragEnd(itemID, sourceModuleId, targetModuleId);
			}
		});

		dragGapPresenter.setDragStartHandler(new DragGapStartDragHandler() {

			@Override
			public void onDragStart(DragStartEvent event) {
				sourcelistManager.dragStart(getIdentifier());

				DragDataObject dataObject = overlayTypesParser.<NativeDragDataObject> get();
				dataObject.setItemId(itemId);
				dataObject.setSourceId(getIdentifier());
				event.setData("json", dataObject.toJSON());
			}
		});

		dragGapPresenter.setDragEndHandler(new DragEndHandler() {

			@Override
			public void onDragEnd(DragEndEvent event) {
				sourcelistManager.dragFinished();
			}
		});

		sourcelistManager.registerModule(this);
	}

	@Override
	public void reset() {
		super.reset();
		itemId = StringUtils.EMPTY_STRING;
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
		return itemId;
	}

	@Override
	public void setDragItem(String itemId) {
		this.itemId = itemId;
		String itemContent = sourcelistManager.getValue(itemId, getIdentifier());
		dragGapPresenter.setContent(itemContent);
	}

	@Override
	public void removeDragItem() {
		dragGapPresenter.removeContent();
		itemId = StringUtils.EMPTY_STRING;
	}

	@Override
	public void lockDropZone() {
		dragGapPresenter.lockDropZone();
	}

	@Override
	public void unlockDropZone() {
		if (!locked)
			dragGapPresenter.unlockDropZone();
	}

	@Override
	public void lock(boolean lock) {
		super.lock(lock);
		if (lock) {
			sourcelistManager.lockGroup(getIdentifier());
		} else {
			sourcelistManager.unlockGroup(getIdentifier());
			dragGapPresenter.unlockDropZone();
		}
	}

}
