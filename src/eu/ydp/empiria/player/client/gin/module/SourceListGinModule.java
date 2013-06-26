package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.sourcelist.presenter.SourceListPresenter;
import eu.ydp.empiria.player.client.module.sourcelist.presenter.SourceListPresenterImpl;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListJAXBParser;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListModuleStructure;
import eu.ydp.empiria.player.client.module.sourcelist.view.SourceListView;
import eu.ydp.empiria.player.client.module.sourcelist.view.SourceListViewImpl;
public class SourceListGinModule extends AbstractGinModule {
	public static class SourceListJAXBParserProvider implements Provider<SourceListJAXBParser>{
		@Override
		public SourceListJAXBParser get() {
			return GWT.create(SourceListJAXBParser.class);
		};
	}


	@Override
	protected void configure() {
		bind(SourceListView.class).to(SourceListViewImpl.class);
		bind(SourceListPresenter.class).to(SourceListPresenterImpl.class);
		bind(SourceListJAXBParser.class).toProvider(SourceListJAXBParserProvider.class);
		bind(SourceListModuleStructure.class);
	}

}
