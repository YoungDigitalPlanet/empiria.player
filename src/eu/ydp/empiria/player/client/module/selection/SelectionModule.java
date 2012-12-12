package eu.ydp.empiria.player.client.module.selection;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

import eu.ydp.empiria.player.client.gin.factory.SelectionModuleFactory;
import eu.ydp.empiria.player.client.module.AbstractInteractionModule;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.selection.presenter.SelectionModulePresenter;
import eu.ydp.empiria.player.client.module.selection.structure.SelectionInteractionBean;
import eu.ydp.empiria.player.client.module.selection.structure.SelectionModuleStructure;

public class SelectionModule extends AbstractInteractionModule<SelectionModule, SelectionModuleModel, SelectionInteractionBean>{

	private Provider<SelectionModule> selectionModuleProvider;
	private SelectionModulePresenter selectionModulePresenter;
	private SelectionModuleStructure structure;
	private SelectionModuleFactory selectionModuleFactory;
	
	@Inject
	public SelectionModule(
			Provider<SelectionModule> selectionModuleProvider, 
			SelectionModulePresenter selectionModulePresenter,
			SelectionModuleStructure structure, 
			SelectionModuleFactory selectionModuleFactory) {
		this.selectionModuleProvider = selectionModuleProvider;
		this.selectionModulePresenter = selectionModulePresenter;
		this.structure = structure;
		this.selectionModuleFactory = selectionModuleFactory;
	}

	private SelectionModuleModel model;
	
	@Override
	public SelectionModule getNewInstance() {
		return selectionModuleProvider.get();
	}

	@Override
	protected ActivityPresenter<SelectionModuleModel, SelectionInteractionBean> getPresenter() {
		return selectionModulePresenter;
	}

	@Override
	protected void initalizeModule() {
		model = selectionModuleFactory.createSelectionModuleModel(getResponse(), this);
	}

	@Override
	protected SelectionModuleModel getResponseModel() {
		return model;
	}

	@Override
	protected AbstractModuleStructure<SelectionInteractionBean, ? extends JAXBParserFactory<SelectionInteractionBean>> getStructure() {
		return structure;
	}

}
