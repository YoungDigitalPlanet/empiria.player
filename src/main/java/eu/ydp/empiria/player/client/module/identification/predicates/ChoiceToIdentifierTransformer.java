package eu.ydp.empiria.player.client.module.identification.predicates;

import com.google.common.base.Function;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.identification.presenter.SelectableChoicePresenter;

@Singleton
public class ChoiceToIdentifierTransformer implements Function<SelectableChoicePresenter, String> {

    @Override
    public String apply(SelectableChoicePresenter input) {
        return input.getIdentifier();
    }
}
