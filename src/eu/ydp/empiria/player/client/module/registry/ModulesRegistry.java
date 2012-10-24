package eu.ydp.empiria.player.client.module.registry;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleCreator;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;

public class ModulesRegistry implements ModulesRegistrySocket {

	protected Map<String, ModuleCreator> moduleCreators;
	
	public ModulesRegistry(){
		moduleCreators = new HashMap<String, ModuleCreator>();
	}
	
	public void registerModuleCreator(String nodeName, ModuleCreator creator){
		moduleCreators.put(nodeName, creator);
	}
	
	public boolean isModuleSupported(String nodeName){
		if(EmpiriaTagConstants.NAME_GAP.equals(nodeName)){
			return true;
		}else{
			return moduleCreators.keySet().contains(nodeName);
		}
	}

	@Override
	public boolean isMultiViewModule(String nodeName){
		if(EmpiriaTagConstants.NAME_GAP.equals(nodeName)){
			return true;
		}
		ModuleCreator currCreator = moduleCreators.get(nodeName);
		if (currCreator != null){
			return currCreator.isMultiViewModule();
		}
		return false;
	}

	@Override
	public boolean isInlineModule(String nodeName) {
		ModuleCreator currCreator = moduleCreators.get(nodeName);
		if (currCreator != null){
			return currCreator.isInlineModule();
		}
		return false;
	}
	
	public IModule createModule(Element node){
		String nodeName = node.getNodeName();
		
		if (node.hasAttribute(EmpiriaTagConstants.ATTR_TYPE)) {
			nodeName = ModuleTagName.getTagNameWithType(nodeName, node.getAttribute(EmpiriaTagConstants.ATTR_TYPE));
		}
		
		ModuleCreator currCreator = moduleCreators.get(nodeName);
		
		IModule module = null;
		if (currCreator != null) {
			module = currCreator.createModule();
		}
		
		return module;
	}
}
