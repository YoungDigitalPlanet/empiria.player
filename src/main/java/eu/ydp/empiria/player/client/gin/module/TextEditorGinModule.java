package eu.ydp.empiria.player.client.gin.module;

import com.google.inject.TypeLiteral;
import eu.ydp.empiria.player.client.module.texteditor.presenter.TextEditorPresenter;
import eu.ydp.empiria.player.client.module.texteditor.structure.TextEditorBean;
import eu.ydp.empiria.player.client.module.texteditor.structure.TextEditorBeanProvider;
import eu.ydp.empiria.player.client.module.texteditor.view.TextEditorView;
import eu.ydp.empiria.player.client.module.texteditor.view.TextEditorViewImpl;
import eu.ydp.empiria.player.client.module.texteditor.wrapper.TextEditorOptions;
import eu.ydp.empiria.player.client.module.texteditor.wrapper.TextEditorOptionsProvider;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopedProvider;

public class TextEditorGinModule extends EmpiriaModule {
    @Override
    protected void configure() {
        bind(TextEditorView.class).to(TextEditorViewImpl.class);
        bind(TextEditorOptions.class).toProvider(TextEditorOptionsProvider.class);
        bind(TextEditorBean.class).toProvider(TextEditorBeanProvider.class);

        bindModuleScoped(TextEditorBean.class, new TypeLiteral<ModuleScopedProvider<TextEditorBean>>() {
        });
        bindModuleScoped(TextEditorPresenter.class, new TypeLiteral<ModuleScopedProvider<TextEditorPresenter>>() {
        });
        bindModuleScoped(TextEditorView.class, new TypeLiteral<ModuleScopedProvider<TextEditorView>>() {
        });
    }
}
