package eu.ydp.empiria.player.client.module.draggap;

import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

import eu.ydp.empiria.player.client.module.AbstractInteractionModule;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.draggap.presenter.DragGapPresenter;
import eu.ydp.empiria.player.client.module.draggap.structure.DragGapBean;
import eu.ydp.empiria.player.client.module.draggap.structure.DragGapStructure;

public class DragGapModule extends AbstractInteractionModule<DragGapModule, DragGapModuleModel, DragGapBean> {

	@Inject
	private DragGapModuleModel dragGapModuleModel;
	
	@Inject
	private DragGapPresenter dragGapPresenter;
	
	@Inject
	private DragGapStructure dragGapStructure; 
	
	@Override
	protected ActivityPresenter<DragGapModuleModel, DragGapBean> getPresenter() {
		return dragGapPresenter;
	}

	@Override
	protected void initalizeModule() {
		dragGapModuleModel.initialize(this);
	}

	@Override
	protected DragGapModuleModel getResponseModel() {
		return dragGapModuleModel;
	}

	@Override
	protected AbstractModuleStructure<DragGapBean, ? extends JAXBParserFactory<DragGapBean>> getStructure() {
		return dragGapStructure;
	}
}
