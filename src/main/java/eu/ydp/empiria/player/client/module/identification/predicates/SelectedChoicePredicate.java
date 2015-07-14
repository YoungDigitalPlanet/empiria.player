package eu.ydp.empiria.player.client.module.identification.predicates;

import com.google.common.base.Predicate;
import eu.ydp.empiria.player.client.module.identification.presenter.SelectableChoicePresenter;

public class SelectedChoicePredicate implements Predicate<SelectableChoicePresenter> {

    @Override
    public boolean apply(SelectableChoicePresenter choice) {
        return choice.isSelected();
    }
}
