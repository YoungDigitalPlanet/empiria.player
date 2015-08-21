package eu.ydp.empiria.player.client.gin.module;

import com.google.inject.TypeLiteral;
import eu.ydp.empiria.player.client.module.test.reset.TestResetButtonPresenter;
import eu.ydp.empiria.player.client.module.test.reset.view.TestResetButtonView;
import eu.ydp.empiria.player.client.module.test.reset.view.TestResetButtonViewImpl;
import eu.ydp.empiria.player.client.module.test.submit.view.TestPageSubmitButtonView;
import eu.ydp.empiria.player.client.module.test.submit.view.TestPageSubmitButtonViewImpl;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopedProvider;

public class TestGinModule extends BaseGinModule {

    @Override
    protected void configure() {
        bind(TestPageSubmitButtonView.class).to(TestPageSubmitButtonViewImpl.class);
        bind(TestResetButtonView.class).to(TestResetButtonViewImpl.class);

        bindModuleScoped(TestResetButtonPresenter.class, new TypeLiteral<ModuleScopedProvider<TestResetButtonPresenter>>() {
        });
        bindModuleScoped(TestResetButtonView.class, new TypeLiteral<ModuleScopedProvider<TestResetButtonView>>() {
        });
    }
}
