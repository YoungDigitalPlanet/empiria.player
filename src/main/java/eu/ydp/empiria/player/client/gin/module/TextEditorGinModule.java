package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import eu.ydp.empiria.player.client.module.texteditor.structure.TextEditorBean;
import eu.ydp.empiria.player.client.module.texteditor.structure.TextEditorBeanProvider;
import eu.ydp.empiria.player.client.module.texteditor.view.TextEditorView;
import eu.ydp.empiria.player.client.module.texteditor.view.TextEditorViewImpl;
import eu.ydp.empiria.player.client.module.texteditor.wrapper.TextEditorOptions;
import eu.ydp.empiria.player.client.module.texteditor.wrapper.TextEditorOptionsProvider;

public class TextEditorGinModule extends AbstractGinModule {
    @Override
    protected void configure() {
        bind(TextEditorView.class).to(TextEditorViewImpl.class);
        bind(TextEditorOptions.class).toProvider(TextEditorOptionsProvider.class);
        bind(TextEditorBean.class).toProvider(TextEditorBeanProvider.class);
    }
}
