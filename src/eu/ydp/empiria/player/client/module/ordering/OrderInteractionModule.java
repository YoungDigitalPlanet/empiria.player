package eu.ydp.empiria.player.client.module.ordering;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

import eu.ydp.empiria.player.client.gin.factory.OrderInteractionModuleFactory;
import eu.ydp.empiria.player.client.module.AbstractInteractionModule;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.ordering.presenter.OrderInteractionPresenter;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionBean;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionStructure;

public class OrderInteractionModule extends AbstractInteractionModule<OrderInteractionModule, OrderInteractionModuleModel, OrderInteractionBean> implements
		Factory<OrderInteractionModule> {

	@Inject
	private Provider<OrderInteractionModule> moduleProvider;

	@Inject
	private OrderInteractionPresenter presenter;

	@Inject
	private OrderInteractionStructure orderInteractionStructure;

	@Inject
	private OrderInteractionModuleFactory moduleFactory;

	private OrderInteractionModuleModel moduleModel;

	@Override
	public Widget getView() {
		return presenter.asWidget();
	}

	@Override
	public OrderInteractionModule getNewInstance() {
		return moduleProvider.get();
	}

	@Override
	protected void initalizeModule() {
		moduleModel = moduleFactory.getOrderInteractionModuleModel(getResponse(), this);
	}

	@Override
	protected OrderInteractionModuleModel getResponseModel() {
		return moduleModel;
	}

	@Override
	protected ActivityPresenter<OrderInteractionModuleModel, OrderInteractionBean> getPresenter() {
		return presenter;
	}

	@Override
	protected AbstractModuleStructure<OrderInteractionBean, ? extends JAXBParserFactory<OrderInteractionBean>> getStructure() {
		return orderInteractionStructure;
	}

}
