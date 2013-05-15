package eu.ydp.empiria.player.client.module.ordering;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.ordering.presenter.OrderInteractionPresenter;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionBean;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionStructure;
import eu.ydp.gwtutil.client.service.json.IJSONService;

public class OrderInteractionModule extends SimpleModuleBase implements Factory<OrderInteractionModule> {

	@Inject
	private Provider<OrderInteractionModule> moduleProvider;

	@Inject
	private OrderInteractionPresenter presenter;

	@Inject
	private OrderInteractionStructure orderInteractionStructure;

	@Inject
	private IJSONService ijsonService;
	@Override
	public Widget getView() {
		return presenter.asWidget();
	}

	@Override
	public OrderInteractionModule getNewInstance() {
		return moduleProvider.get();
	}

	@Override
	protected void initModule(Element element) {
		orderInteractionStructure.createFromXml(element.toString(),ijsonService.createArray());
		OrderInteractionBean bean = orderInteractionStructure.getBean();
	}

}
