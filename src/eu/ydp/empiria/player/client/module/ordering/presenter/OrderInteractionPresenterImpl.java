package eu.ydp.empiria.player.client.module.ordering.presenter;

import java.util.List;
import java.util.Map;

import com.google.gwt.dev.util.collect.HashMap;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionBean;
import eu.ydp.empiria.player.client.module.ordering.structure.SimpleOrderChoiceBean;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionView;

public class OrderInteractionPresenterImpl implements OrderInteractionPresenter {

	@Inject
	private OrderInteractionView interactionView;
	
	private ModuleSocket socket;
	private OrderInteractionModuleModel model;
	private OrderInteractionBean bean;

	private Map<String, OrderingItem> orderingItemsMap = new HashMap<String, OrderingItem>();
	
	@Override
	public Widget asWidget() {
		return interactionView.asWidget();
	}

	@Override
	public void bindView() {
		List<SimpleOrderChoiceBean> itemBeans = bean.getChoiceBeans();
		InlineBodyGeneratorSocket bodyGeneratorSocket = socket.getInlineBodyGeneratorSocket();
		
		for (int i=0; i<itemBeans.size(); i++) {
			SimpleOrderChoiceBean simpleOrderChoiceBean = itemBeans.get(i);
			XMLContent content = simpleOrderChoiceBean.getContent();
			String answerValue = simpleOrderChoiceBean.getIdentifier();
			String itemId = String.valueOf(i);
			OrderingItem orderingItem = new OrderingItem(itemId, answerValue);
			
			interactionView.createItem(orderingItem, content, bodyGeneratorSocket);
			
			orderingItemsMap.put(itemId, orderingItem);
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		//sprawdzic aktualn¹ kolejnoœæ
		//mieszac poki jest taka sama
	}

	@Override
	public void setModel(OrderInteractionModuleModel model) {
		this.model = model;
	}

	@Override
	public void setModuleSocket(ModuleSocket socket) {
		this.socket = socket;
	}

	@Override
	public void setBean(OrderInteractionBean bean) {
		this.bean = bean;
	}

	@Override
	public void setLocked(boolean locked) {
		// TODO Auto-generated method stub

	}

	@Override
	public void markAnswers(MarkAnswersType type, MarkAnswersMode mode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showAnswers(ShowAnswersType mode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void itemClicked(String itemId) {
		// TODO Auto-generated method stub
		
	}
}
