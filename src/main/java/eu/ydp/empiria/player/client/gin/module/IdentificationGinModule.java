package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.identification.predicates.ChoiceToIdentifierTransformer;
import eu.ydp.empiria.player.client.module.identification.predicates.SelectedChoicePredicate;

public class IdentificationGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(SelectedChoicePredicate.class).in(Singleton.class);
        bind(ChoiceToIdentifierTransformer.class).in(Singleton.class);
    }
}
