package eu.ydp.empiria.player.client.controller.body;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.components.ModulePlaceholder;
import eu.ydp.empiria.player.client.module.IContainerModule;
import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.IMultiViewModule;
import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.module.ISingleViewModule;
import eu.ydp.empiria.player.client.module.ISingleViewWithBodyModule;
import eu.ydp.empiria.player.client.module.ISingleViewSimpleModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import eu.ydp.empiria.player.client.util.StackMap;

public class ModulesInstalator implements ModulesInstalatorSocket {
	
	protected ModulesRegistrySocket registry;
	protected ModuleSocket moduleSocket;
	protected ModuleInteractionListener moduleInteractionListener;
	protected List<IModule> singleViewModules;
	
	public ModulesInstalator(ModulesRegistrySocket reg, ModuleSocket ms, ModuleInteractionListener mil){
		this.registry = reg;
		this.moduleSocket = ms;
		this.moduleInteractionListener = mil;
		singleViewModules = new ArrayList<IModule>();
	}
	
	
	protected StackMap<String, StackMap<Element, HasWidgets>> uniqueModulesMap = new StackMap<String, StackMap<Element,HasWidgets>>();
	protected StackMap<Element, HasWidgets> nonuniqueModulesMap = new StackMap<Element, HasWidgets>();

	@Override
	public boolean isModuleSupported(String nodeName){
		return registry.isModuleSupported(nodeName);
	}
	
	@Override
	public boolean isMultiViewModule(String nodeName) {
		return registry.isMultiViewModule(nodeName);
	}
	
	public void registerModuleView(Element element, HasWidgets parent){
		String responseIdentifier = element.getAttribute("responseIdentifier");
		
		ModulePlaceholder placeholder = new ModulePlaceholder();
		parent.add(placeholder);
		
		if (responseIdentifier != null){
			if (!uniqueModulesMap.containsKey(responseIdentifier)){
				uniqueModulesMap.put(responseIdentifier, new StackMap<Element, HasWidgets>());
			}
			uniqueModulesMap.get(responseIdentifier).put(element, placeholder);
		} else {
			nonuniqueModulesMap.put(element, placeholder);
		}
	}

	@Override
	public void createSingleViewModule(Element element, HasWidgets parent,BodyGeneratorSocket bodyGeneratorSocket) {
		IModule module = registry.createModule(element.getNodeName());
		if (module instanceof ISingleViewWithBodyModule){
			((ISingleViewWithBodyModule) module).initModule(element, moduleSocket, moduleInteractionListener, bodyGeneratorSocket);
		} else if (module instanceof ISingleViewSimpleModule){
			((ISingleViewSimpleModule)module).initModule(element, moduleSocket, moduleInteractionListener);			
		}

		if (((ISingleViewModule)module).getView() instanceof Widget ){
			parent.add( ((ISingleViewModule)module).getView() );
		}
		
		singleViewModules.add(module);
	}
	
	public void installMultiViewNonuniuqeModules(){
		for (Element currElement : nonuniqueModulesMap.getKeys()){
			// TODO
		}
	}
	
	public List<IModule> installMultiViewUniqueModules(){
		
		List<IModule> modules = new ArrayList<IModule>();
		
		for (String responseIdentifier : uniqueModulesMap.getKeys()){
			StackMap<Element, HasWidgets> currModuleMap = uniqueModulesMap.get(responseIdentifier);
						
			IModule currModule = null;
			
			for (Element currElement : currModuleMap.getKeys()){
				if (currModule == null){
					currModule = registry.createModule(currElement.getNodeName());
					if (currModule instanceof IMultiViewModule){
						((IMultiViewModule)currModule).initModule(moduleSocket, moduleInteractionListener);
					}
				}
				if (currModule instanceof IMultiViewModule){
					((IMultiViewModule)currModule).addElement(currElement);
				}
			}
			
			if (currModule instanceof IMultiViewModule){
				((IMultiViewModule)currModule).installViews(currModuleMap.getValues());
			}
			
			if (currModule != null)
				modules.add(currModule);
			
		}
		return modules;
	}

	public List<IModule> getInstalledSingleViewModules(){
		return singleViewModules;
	}
	
}
