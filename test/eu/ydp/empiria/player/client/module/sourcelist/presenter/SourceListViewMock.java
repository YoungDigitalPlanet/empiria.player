package eu.ydp.empiria.player.client.module.sourcelist.presenter;

import static org.mockito.Mockito.mock;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

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
		// TODO Auto-generated method stub

	}

	@Override
	public void createAndBindUi() {
		// TODO Auto-generated method stub

	}

}
