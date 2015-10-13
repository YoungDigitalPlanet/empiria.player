package eu.ydp.empiria.player.client.module.registry;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.module.core.creator.ModuleCreator;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleCreationContext;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopeStack;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class ModulesRegistry implements ModulesRegistrySocket {

    protected final Map<String, ModuleCreator> moduleCreators;
    private final ModuleScopeStack moduleScopeStack;

    @Inject
    public ModulesRegistry(ModuleScopeStack moduleScopeStack) {
        this.moduleScopeStack = moduleScopeStack;
        moduleCreators = new HashMap<String, ModuleCreator>();
    }

    public void registerModuleCreator(String nodeName, ModuleCreator creator) {
        moduleCreators.put(nodeName, creator);
    }

    @Override
    public boolean isModuleSupported(String nodeName) {
        if (EmpiriaTagConstants.NAME_GAP.equals(nodeName)) {
            return true;
        } else {
            return moduleCreators.keySet().contains(nodeName);
        }
    }

    @Override
    public boolean isMultiViewModule(Element element) {
        if (EmpiriaTagConstants.NAME_GAP.equals(element.getTagName())) {
            String tagNameWithType = ModuleTagName.getTagNameWithType(element);
            return isMultiViewModule(tagNameWithType);
        }

        return isMultiViewModule(element.getTagName());
    }

    private boolean isMultiViewModule(String tagName){
        ModuleCreator currCreator = moduleCreators.get(tagName);
        if (currCreator != null) {
            return currCreator.isMultiViewModule();
        }
        return false;
    }

    @Override
    public boolean isInlineModule(String nodeName) {
        ModuleCreator currCreator = moduleCreators.get(nodeName);
        if (currCreator != null) {
            return currCreator.isInlineModule();
        }
        return false;
    }

    @Override
    public IModule createModule(Element element) {
        String nodeName = element.getNodeName();
        ModuleCreator currCreator = moduleCreators.get(nodeName);

        if ((currCreator == null) && (element.hasAttribute(EmpiriaTagConstants.ATTR_TYPE))) {
            nodeName = ModuleTagName.getTagNameWithType(element);
            currCreator = moduleCreators.get(nodeName);
        }

        ModuleCreationContext context = new ModuleCreationContext(element);
        moduleScopeStack.pushContext(context);
        IModule module = currCreator.createModule();
        moduleScopeStack.pop();

        return module;
    }
}
