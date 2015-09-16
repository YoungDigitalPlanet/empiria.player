package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.extensions.types.ModuleConnectorExtension;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.module.core.creator.ModuleCreator;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.core.creator.SimpleModuleCreator;

/**
 * Klasa bedaca podstawowowa implementacja ModuleConnectorExtension.<br/>
 */
public class SimpleConnectorExtension extends ModuleExtension implements ModuleConnectorExtension {

    private final Provider<? extends IModule> clazzProvider;
    private final String tagName;
    private boolean isMultiViewModule = false;
    private boolean isInlineModule = false;

    /**
     * domyslnie isMultiViewModule=false,isInlineModule=false
     *
     * @param clazz   klasa modulu
     * @param tagName nazwa taga kt�ry ma by obslugiwany
     */
    public SimpleConnectorExtension(Provider<? extends IModule> clazz, ModuleTagName tagName) {
        this(clazz, tagName, false);
    }

    public SimpleConnectorExtension(Provider<? extends IModule> clazz, ModuleTagName tagName, boolean isMultiViewModule) {
        this(clazz, tagName, isMultiViewModule, false);
    }

    public SimpleConnectorExtension(Provider<? extends IModule> clazzProvider, ModuleTagName tagName, boolean isMultiViewModule, boolean isInlineModule) {
        this.tagName = tagName.tagName();
        this.isMultiViewModule = isMultiViewModule;
        this.isInlineModule = isInlineModule;
        this.clazzProvider = clazzProvider;
    }

    @Override
    public ModuleCreator getModuleCreator() {
        return new SimpleModuleCreator<>(clazzProvider, isMultiViewModule, isInlineModule);
    }

    @Override
    public String getModuleNodeName() {
        return tagName;
    }

}
