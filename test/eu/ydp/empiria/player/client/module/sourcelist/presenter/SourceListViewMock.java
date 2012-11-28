package eu.ydp.empiria.player.client.module.sourcelist.presenter;

import static org.mockito.Mockito.mock;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListBean;
import eu.ydp.empiria.player.client.module.sourcelist.view.SourceListView;

public class SourceListViewMock implements SourceListView {

	FlowPanel panel = mock(FlowPanel.class);

	@Override
	public Widget asWidget() {
		return panel;
	}

	@Override
	public void setBean(SourceListBean bean) {

	}

	@Override
	public void createAndBindUi() {

	}

	@Override
	public void setIModule(IModule module) {

	}

	@Override
	public boolean containsValue(String value) {
		return false;
	}
}
