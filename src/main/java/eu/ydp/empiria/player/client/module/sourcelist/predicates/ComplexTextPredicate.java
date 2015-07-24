package eu.ydp.empiria.player.client.module.sourcelist.predicates;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

public class ComplexTextPredicate implements Predicate<Element> {

    private final Predicate<Element> complexTextPredicate;

    @Inject
    public ComplexTextPredicate(SpecialCharacterPredicate specialCharacterPredicate, FormattedTextPredicate formattedTextPredicate) {
        complexTextPredicate = Predicates.or(specialCharacterPredicate, formattedTextPredicate);
    }

    @Override
    public boolean apply(Element element) {
        return complexTextPredicate.apply(element);
    }
}
