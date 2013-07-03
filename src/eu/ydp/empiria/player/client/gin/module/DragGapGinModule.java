package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;

import eu.ydp.empiria.player.client.module.draggap.presenter.DragGapPresenter;
import eu.ydp.empiria.player.client.module.draggap.presenter.DragGapPresenterImpl;
import eu.ydp.empiria.player.client.module.draggap.view.DragGapView;
import eu.ydp.empiria.player.client.module.draggap.view.DragGapViewImpl;

public class DragGapGinModule extends AbstractGinModule{

	@Override
	protected void configure() {
		bind(DragGapPresenter.class).to(DragGapPresenterImpl.class);
		bind(DragGapView.class).to(DragGapViewImpl.class);
	}
}
