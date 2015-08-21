package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import eu.ydp.empiria.player.client.gin.factory.SimpleChoicePresenterFactory;
import eu.ydp.empiria.player.client.gin.factory.SimpleChoiceViewFactory;
import eu.ydp.empiria.player.client.module.choice.ChoiceModuleModel;
import eu.ydp.empiria.player.client.module.choice.presenter.ChoiceModulePresenter;
import eu.ydp.empiria.player.client.module.choice.presenter.ChoiceModulePresenterImpl;
import eu.ydp.empiria.player.client.module.choice.presenter.SimpleChoicePresenter;
import eu.ydp.empiria.player.client.module.choice.presenter.SimpleChoicePresenterImpl;
import eu.ydp.empiria.player.client.module.choice.providers.MultiChoiceStyleProvider;
import eu.ydp.empiria.player.client.module.choice.providers.SimpleChoiceStyleProvider;
import eu.ydp.empiria.player.client.module.choice.providers.SingleChoiceStyleProvider;
import eu.ydp.empiria.player.client.module.choice.view.ChoiceModuleView;
import eu.ydp.empiria.player.client.module.choice.view.ChoiceModuleViewImpl;
import eu.ydp.empiria.player.client.module.choice.view.SimpleChoiceView;
import eu.ydp.empiria.player.client.module.choice.view.SimpleChoiceViewImpl;
import eu.ydp.empiria.player.client.module.components.choicebutton.ChoiceButtonBase;
import eu.ydp.empiria.player.client.module.components.choicebutton.MultiChoiceButton;
import eu.ydp.empiria.player.client.module.components.choicebutton.SingleChoiceButton;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopedProvider;

public class ChoiceGinModule extends BaseGinModule {

    @Override
    protected void configure() {
        bind(ChoiceModulePresenter.class).to(ChoiceModulePresenterImpl.class);
        bind(ChoiceModuleView.class).to(ChoiceModuleViewImpl.class);

        bindModuleScoped(ChoiceModuleView.class, new TypeLiteral<ModuleScopedProvider<ChoiceModuleView>>() {
        });
        bindModuleScoped(ChoiceModulePresenter.class, new TypeLiteral<ModuleScopedProvider<ChoiceModulePresenter>>() {
        });
        bindModuleScoped(ChoiceModuleModel.class, new TypeLiteral<ModuleScopedProvider<ChoiceModuleModel>>() {
        });
        bindModuleScoped(MultiChoiceStyleProvider.class, new TypeLiteral<ModuleScopedProvider<MultiChoiceStyleProvider>>() {
        });

        install(new GinFactoryModuleBuilder().implement(SimpleChoicePresenter.class, SimpleChoicePresenterImpl.class).build(SimpleChoicePresenterFactory.class));
        install(new GinFactoryModuleBuilder().implement(SimpleChoiceView.class, SimpleChoiceViewImpl.class)
                .implement(ChoiceButtonBase.class, Names.named("single"), SingleChoiceButton.class)
                .implement(ChoiceButtonBase.class, Names.named("multi"), MultiChoiceButton.class)
                .implement(SimpleChoiceStyleProvider.class, Names.named("single"), SingleChoiceStyleProvider.class)
                .implement(SimpleChoiceStyleProvider.class, Names.named("multi"), MultiChoiceStyleProvider.class).build(SimpleChoiceViewFactory.class));
    }
}
