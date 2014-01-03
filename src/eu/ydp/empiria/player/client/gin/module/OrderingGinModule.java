package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Singleton;

import eu.ydp.empiria.player.client.gin.factory.OrderInteractionModuleFactory;
import eu.ydp.empiria.player.client.module.ordering.drag.SortCallback;
import eu.ydp.empiria.player.client.module.ordering.drag.SortCallbackImpl;
import eu.ydp.empiria.player.client.module.ordering.drag.Sortable;
import eu.ydp.empiria.player.client.module.ordering.drag.SortableNative;
import eu.ydp.empiria.player.client.module.ordering.presenter.OrderInteractionPresenter;
import eu.ydp.empiria.player.client.module.ordering.presenter.OrderInteractionPresenterImpl;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionView;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionViewImpl;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionViewUniqueCssProvider;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionViewWidget;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionViewWidgetImpl;
import eu.ydp.empiria.player.client.module.ordering.view.items.OrderInteractionViewItem;
import eu.ydp.empiria.player.client.module.ordering.view.items.OrderInteractionViewItemImpl;
import eu.ydp.empiria.player.client.module.ordering.view.items.OrderInteractionViewItemStyles;
import eu.ydp.empiria.player.client.module.ordering.view.items.OrderInteractionViewItemStylesImpl;
import eu.ydp.empiria.player.client.module.ordering.view.items.OrderInteractionViewItems;
import eu.ydp.empiria.player.client.module.ordering.view.items.OrderInteractionViewItemsImpl;

public class OrderingGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(Sortable.class).in(Singleton.class);
		bind(SortableNative.class).in(Singleton.class);
		bind(OrderInteractionPresenter.class).to(OrderInteractionPresenterImpl.class);
		bind(OrderInteractionView.class).to(OrderInteractionViewImpl.class);
		bind(OrderInteractionViewWidget.class).to(OrderInteractionViewWidgetImpl.class);
		bind(OrderInteractionViewItems.class).to(OrderInteractionViewItemsImpl.class);
		bind(OrderInteractionViewItemStyles.class).to(OrderInteractionViewItemStylesImpl.class);
		bind(SortCallback.class).to(SortCallbackImpl.class);
		bind(OrderInteractionViewUniqueCssProvider.class).in(Singleton.class);
		install(new GinFactoryModuleBuilder().implement(OrderInteractionViewItem.class, OrderInteractionViewItemImpl.class).build(
				OrderInteractionModuleFactory.class));
	}

}
