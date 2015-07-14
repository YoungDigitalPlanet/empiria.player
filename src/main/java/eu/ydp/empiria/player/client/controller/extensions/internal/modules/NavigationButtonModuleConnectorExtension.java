package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.factory.ModuleFactory;
import eu.ydp.empiria.player.client.module.AbstractModuleCreator;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleCreator;
import eu.ydp.empiria.player.client.module.button.NavigationButtonDirection;
import eu.ydp.empiria.player.client.module.button.NavigationButtonModule;

import java.util.Map;

import static eu.ydp.empiria.player.client.module.ModuleTagName.NEXT_ITEM_NAVIGATION;
import static eu.ydp.empiria.player.client.module.ModuleTagName.PREV_ITEM_NAVIGATION;

public abstract class NavigationButtonModuleConnectorExtension extends ControlModuleConnectorExtension {

    private static final Map<String, NavigationButtonDirection> NODE2DIRECTION = ImmutableMap
            .<String, NavigationButtonDirection>builder()
            .put(NEXT_ITEM_NAVIGATION.tagName(), NavigationButtonDirection.NEXT)
            .put(PREV_ITEM_NAVIGATION.tagName(), NavigationButtonDirection.PREVIOUS)
            .build();

    @Inject
    private ModuleFactory moduleFactory;

    @Override
    public ModuleCreator getModuleCreator() {
        return new AbstractModuleCreator() {
            @Override
            public IModule createModule() {
                NavigationButtonDirection direction = getDirection(getModuleNodeName());
                NavigationButtonModule button = moduleFactory.createNavigationButtonModule(direction);
                initializeModule(button);
                return button;
            }
        };
    }

    private NavigationButtonDirection getDirection(String name) {
        return NODE2DIRECTION.get(name);
    }

}
