package eu.ydp.empiria.player.client.module;

import com.google.inject.Provider;

/**
 * Prosta implementacja ModuleCreator<br/>
 */
public class SimpleModuleCreator<T extends IModule> implements ModuleCreator {

    private final boolean inlineModule;
    private final boolean multiViewModule;
    private final Provider<T> provider;

    protected SimpleModuleCreator(boolean isMultiViewModule, boolean isInlineModule) {
        this.inlineModule = isInlineModule;
        this.multiViewModule = isMultiViewModule;
        this.provider = null;
    }

    public SimpleModuleCreator(Provider<T> provider, boolean isMultiViewModule, boolean isInlineModule) {
        this.inlineModule = isInlineModule;
        this.multiViewModule = isMultiViewModule;
        this.provider = provider;
    }

    @Override
    public boolean isMultiViewModule() {
        return multiViewModule;
    }

    @Override
    public boolean isInlineModule() {
        return inlineModule;
    }

    @Override
    public T createModule() {
        return provider.get();
    }

}
