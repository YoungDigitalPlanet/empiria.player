package eu.ydp.empiria.player.client.module.ordering.presenter;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionBean;
import eu.ydp.empiria.player.client.module.ordering.structure.SimpleOrderChoiceBean;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionView;

public class OrderingViewBuilder {

	private final InlineBodyGeneratorSocket bodyGeneratorSocket;
	private final OrderInteractionBean bean;
	private final OrderInteractionView interactionView;
	private final OrderingItemsDao orderingItemsDao;

	@Inject
	public OrderingViewBuilder(
			@Assisted InlineBodyGeneratorSocket bodyGeneratorSocket,
			@Assisted OrderInteractionBean bean,
			@Assisted OrderInteractionView interactionView,
			@Assisted OrderingItemsDao orderingItemsDao) {
		this.bodyGeneratorSocket = bodyGeneratorSocket;
		this.bean = bean;
		this.interactionView = interactionView;
		this.orderingItemsDao = orderingItemsDao;
	}

	public void buildView(){
		interactionView.setOrientation(bean.getOrientation());
		List<SimpleOrderChoiceBean> itemBeans = bean.getChoiceBeans();

		for (int i=0; i<itemBeans.size(); i++) {
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
