package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.components.multiplepair.structure.MultiplePairBean;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionView;
import eu.ydp.empiria.player.client.module.connection.structure.SimpleAssociableChoiceBean;

public class ConnectionColumnsBuilder {
	private final MultiplePairBean<SimpleAssociableChoiceBean> modelInterface;
	private final ConnectionItems connectionItems;
	private final ConnectionView view;

	@Inject
	public ConnectionColumnsBuilder(@Assisted MultiplePairBean<SimpleAssociableChoiceBean> modelInterface, @Assisted ConnectionItems connectionItems,
			@Assisted ConnectionView view) {
		this.modelInterface = modelInterface;
		this.connectionItems = connectionItems;
		this.view = view;
	}

	public void initRightColumn() {
		for (PairChoiceBean choice : modelInterface.getTargetChoicesSet()) {
			ConnectionItem item = connectionItems.addItemToRightColumn(choice);
			view.addSecondColumnItem(item);
		}
	}

	public void initLeftColumn() {
		for (PairChoiceBean choice : modelInterface.getSourceChoicesSet()) {
			ConnectionItem item = connectionItems.addItemToLeftColumn(choice);
			view.addFirstColumnItem(item);
		}
	}
}
