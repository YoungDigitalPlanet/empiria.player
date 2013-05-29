package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;

import eu.ydp.empiria.player.client.gin.factory.OrderInteractionModuleFactory;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionView;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionViewImpl;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionViewWidget;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionViewWidgetImpl;
import eu.ydp.empiria.player.client.module.ordering.presenter.OrderInteractionPresenter;
import eu.ydp.empiria.player.client.module.ordering.presenter.OrderInteractionPresenterImpl;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionView;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionViewImpl;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionViewWidget;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionViewWidgetImpl;
import eu.ydp.empiria.player.client.module.ordering.view.items.OrderInteractionViewItem;
import eu.ydp.empiria.player.client.module.ordering.view.items.OrderInteractionViewItemImpl;
import eu.ydp.empiria.player.client.module.ordering.view.items.OrderInteractionViewItemStyles;
import eu.ydp.empiria.player.client.module.ordering.view.items.OrderInteractionViewItemStylesImpl;
import eu.ydp.empiria.player.client.module.ordering.view.items.OrderInteractionViewItems;
import eu.ydp.empiria.player.client.module.ordering.view.items.OrderInteractionViewItemsImpl;

public class ColorfillGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		//bind(ColorfillInteractionPresenter.class).to(ColorfillInteractionPresenterImpl.class);
		bind(ColorfillInteractionView.class).to(ColorfillInteractionViewImpl.class);
		bind(ColorfillInteractionViewWidget.class).to(ColorfillInteractionViewWidgetImpl.class);
	}

}
