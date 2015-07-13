package eu.ydp.empiria.player.client.controller.extensions.internal.modules;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.controller.extensions.types.DeliveryEventsListenerExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.ModuleConnectorExtension;
import eu.ydp.empiria.player.client.module.AbstractModuleCreator;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleCreator;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.info.InfoModule;
import eu.ydp.empiria.player.client.module.info.InfoModuleUnloadListener;

import java.util.List;

public class InfoModuleConnectorExtension extends ModuleExtension implements ModuleConnectorExtension, DeliveryEventsListenerExtension {

    @Inject
    private Provider<InfoModule> infoModuleProvider;
    protected List<InfoModule> modules = Lists.newArrayList();

    @Override
    public ModuleCreator getModuleCreator() {
        return new AbstractModuleCreator(false, true) {
            @Override
            public IModule createModule() {
                final InfoModule infoModule = infoModuleProvider.get();
                infoModule.setModuleUnloadListener(new InfoModuleUnloadListener() {

                    @Override
                    public void moduleUnloaded() {
                        modules.remove(infoModule);
                    }
                });
                modules.add(infoModule);
                return infoModule;
            }
        };
    }

    @Override
    public String getModuleNodeName() {
        return ModuleTagName.INFO.tagName();
    }

    @Override
    public void onDeliveryEvent(DeliveryEvent event) {
        if (event.getType() == DeliveryEventType.TEST_PAGE_LOADED) {
            for (InfoModule im : modules) {
                im.update();
            }
        }
    }

}
