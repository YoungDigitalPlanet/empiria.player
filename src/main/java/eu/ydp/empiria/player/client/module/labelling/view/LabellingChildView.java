package eu.ydp.empiria.player.client.module.labelling.view;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;

public interface LabellingChildView {

    IsWidget getView();

    HasWidgets.ForIsWidget getContainer();

    void setPosition(int left, int top);

}
