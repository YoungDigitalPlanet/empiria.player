package eu.ydp.empiria.player.client.module.binding;

import eu.ydp.empiria.player.client.module.IModule;

public abstract class BindingUtil {

    private BindingUtil() {
    }

    public static BindingContext register(BindingType bindingType, Bindable bindable, IModule module) {
        IModule currModule = module;
        while (currModule != null) {
            if (currModule instanceof BindingProxy) {
                BindingProxy proxy = (BindingProxy) currModule;
                BindingManager manager = proxy.getBindingManager(bindingType);
                if (manager != null) {
                    BindingContext context = manager.getBindingContext(bindable.getBindingGroupIdentifier(bindingType));
                    if (context != null) {
                        boolean result = context.add(bindable);
                        if (result)
                            return context;
                    }
                }
            }
            currModule = currModule.getParentModule();
        }
        return null;
    }
}
