package eu.ydp.empiria.player.client.module.ordering.presenter;

import java.util.List;

import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionBean;
import eu.ydp.empiria.player.client.module.ordering.structure.SimpleOrderChoiceBean;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class OrderingViewBuilder {

	private final OrderInteractionView interactionView;
	private final OrderingItemsDao orderingItemsDao;

	@Inject
	public OrderingViewBuilder(@ModuleScoped OrderInteractionView interactionView, @ModuleScoped OrderingItemsDao orderingItemsDao) {
		this.interactionView = interactionView;
		this.orderingItemsDao = orderingItemsDao;
	}

	public void buildView(OrderInteractionBean bean, InlineBodyGeneratorSocket bodyGeneratorSocket) {
		interactionView.setOrientation(bean.getOrientation());
		List<SimpleOrderChoiceBean> itemBeans = bean.getChoiceBeans();

		for (int i = 0; i < itemBeans.size(); i++) {
			SimpleOrderChoiceBean simpleOrderChoiceBean = itemBeans.get(i);
			XMLContent content = getXMLContent(simpleOrderChoiceBean);
			OrderingItem orderingItem = createOrderingItem(String.valueOf(i), simpleOrderChoiceBean);
			interactionView.createItem(orderingItem, content, bodyGeneratorSocket);
			orderingItemsDao.addItem(orderingItem);
		}

		orderingItemsDao.createInitialItemsOrder();
	}

	private OrderingItem createOrderingItem(String itemId, SimpleOrderChoiceBean simpleOrderChoiceBean) {
		String answerValue = simpleOrderChoiceBean.getIdentifier();
		return new OrderingItem(itemId, answerValue);
	}

	private XMLContent getXMLContent(SimpleOrderChoiceBean simpleOrderChoiceBean) {
		return simpleOrderChoiceBean.getContent();
	}
}
