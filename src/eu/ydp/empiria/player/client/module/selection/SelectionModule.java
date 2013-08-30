package eu.ydp.empiria.player.client.module.selection;

import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

import eu.ydp.empiria.player.client.module.AbstractInteractionModule;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.selection.presenter.SelectionModulePresenter;
import eu.ydp.empiria.player.client.module.selection.structure.SelectionInteractionBean;
import eu.ydp.empiria.player.client.module.selection.structure.SelectionModuleStructure;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class SelectionModule extends AbstractInteractionModule<SelectionModule, SelectionModuleModel, SelectionInteractionBean> {

	private final SelectionModulePresenter selectionModulePresenter;
	private final SelectionModuleStructure structure;
	private final SelectionModuleModel model;

	@Inject
	public SelectionModule(
			SelectionModulePresenter selectionModulePresenter,
			SelectionModuleStructure structure, 
			@ModuleScoped SelectionModuleModel model) {
		this.selectionModulePresenter = selectionModulePresenter;
		this.structure = structure;
		this.model = model;
	}

	@Override
	protected ActivityPresenter<SelectionModuleModel, SelectionInteractionBean> getPresenter() {
		return selectionModulePresenter;
	}

	@Override
	protected void initalizeModule() {
		getResponse().setCountMode(getCountMode());
		model.initialize(this);
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
