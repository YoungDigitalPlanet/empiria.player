package eu.ydp.empiria.player.client.module.colorfill;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

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
		
	//@Inject
	//private ColorfillInteractionModuleFactory moduleFactory;
	
	private ColorfillInteractionStructure colorfillInteractionStructure;
	private ColorfillInteractionPresenter presenter;
	private ColorfillInteractionModuleModel moduleModel;

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
		 //TODO: moduleModel = moduleFactory.getColorfillInteractionModuleModel(getResponse(), this);		
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
