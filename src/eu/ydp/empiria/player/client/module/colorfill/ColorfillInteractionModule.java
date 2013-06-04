package eu.ydp.empiria.player.client.module.colorfill;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.module.AbstractInteractionModule;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.colorfill.presenter.ColorfillInteractionPresenter;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorfillInteractionBean;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorfillInteractionStructure;

public class ColorfillInteractionModule extends AbstractInteractionModule<ColorfillInteractionModule, ColorfillInteractionModuleModel, ColorfillInteractionBean> implements  Factory<ColorfillInteractionModule> {

	@Inject
	private Provider<ColorfillInteractionModule> moduleProvider;
	@Inject
	private ColorfillInteractionPresenter presenter;
	@Inject @ModuleScoped
	private ColorfillInteractionModuleModel moduleModel;

	@Inject
	private ColorfillInteractionStructure colorfillInteractionStructure;


	@Override
	public ColorfillInteractionModule getNewInstance() {
		return moduleProvider.get();
	}

	@Override
	protected ActivityPresenter<ColorfillInteractionModuleModel, ColorfillInteractionBean> getPresenter() {
		return presenter;
	}

	@Override
	protected void initalizeModule() {
		moduleModel.initialize(this);
	}

	@Override
	protected ColorfillInteractionModuleModel getResponseModel() {
		return moduleModel;
	}

	@Override
	protected AbstractModuleStructure<ColorfillInteractionBean, ? extends JAXBParserFactory<ColorfillInteractionBean>> getStructure() {
		return colorfillInteractionStructure;
	}

}
