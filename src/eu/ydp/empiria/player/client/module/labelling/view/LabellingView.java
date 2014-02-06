package eu.ydp.empiria.player.client.module.labelling.view;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.module.labelling.structure.ImgBean;

public interface LabellingView {

	void setBackground(ImgBean imgBean);

	void addChild(IsWidget widget, int left, int top);

	IsWidget getView();

	HasWidgets.ForIsWidget getContainer();

	void setViewId(String id);
}
