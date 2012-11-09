package eu.ydp.empiria.player.client.module.sourcelist.view;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.sourcelist.structure.SimpleSourceListItemBean;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListBean;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.ui.GWTPanelFactory;

public class SourceListViewImpl extends Composite implements SourceListView {

	private static SourceListViewImplUiBinder uiBinder = GWT.create(SourceListViewImplUiBinder.class);

	interface SourceListViewImplUiBinder extends UiBinder<Widget, SourceListViewImpl> {
	}

	@Inject
	StyleNameConstants styleNames;

	@Inject
	GWTPanelFactory panelFactory;
	@UiField
	FlowPanel items;

	private SourceListBean bean;

	@Override
	public void setBean(SourceListBean bean) {
		this.bean = bean;
	}

	@Override
	public void createAndBindUi() {
		initWidget(uiBinder.createAndBindUi(this));
		List<SimpleSourceListItemBean> simpleSourceListItemBeans = bean.getSimpleSourceListItemBeans();
		for (SimpleSourceListItemBean simpleSourceListItemBean : simpleSourceListItemBeans) {
			FlowPanel panel = panelFactory.getFlowPanel();
			panel.setStyleName(styleNames.QP_SOURCELIST_ITEM());
			panel.setTitle(simpleSourceListItemBean.getValue());
			panel.getElement().setInnerText(simpleSourceListItemBean.getValue());
			//panel.getElement().setDraggable(Element.DRAGGABLE_TRUE);
			items.add(panel);
		}
	}
}
