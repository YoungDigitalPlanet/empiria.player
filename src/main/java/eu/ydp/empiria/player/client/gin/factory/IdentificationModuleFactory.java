package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.module.identification.presenter.SelectableChoicePresenter;

import eu.ydp.empiria.player.client.module.identification.view.SelectableChoiceView;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.module.identification.*;

public interface IdentificationModuleFactory {

	SelectableChoicePresenter createSelectableChoice(Widget contentWidget, String text);

	SelectableChoiceView createSelectableChoiceView(Widget contentWidget);
}
