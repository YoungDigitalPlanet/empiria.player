package eu.ydp.empiria.player.client.module.registry;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleCreator;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleCreationContext;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopeStack;

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
	public boolean isMultiViewModule(String nodeName) {
		if (EmpiriaTagConstants.NAME_GAP.equals(nodeName)) {
			return true;
		}
		ModuleCreator currCreator = moduleCreators.get(nodeName);
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
	public IModule createModule(Element node) {
		String nodeName = node.getNodeName();
		ModuleCreator currCreator = moduleCreators.get(nodeName);

		if ((currCreator == null) && (node.hasAttribute(EmpiriaTagConstants.ATTR_TYPE))) {
			nodeName = ModuleTagName.getTagNameWithType(nodeName, node.getAttribute(EmpiriaTagConstants.ATTR_TYPE));
			currCreator = moduleCreators.get(nodeName);
		}

		ModuleCreationContext context = new ModuleCreationContext(node);
		moduleScopeStack.pushContext(context);
		IModule module = currCreator.createModule();
		moduleScopeStack.pop();

		return module;
	}
}
