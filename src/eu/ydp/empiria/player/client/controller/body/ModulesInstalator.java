package eu.ydp.empiria.player.client.controller.body;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.components.ModulePlaceholder;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.IMultiViewModule;
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
	protected ParenthoodSocket parenthood;
	protected List<IModule> singleViewModules;
	
	protected StackMap<String, StackMap<Element, HasWidgets>> uniqueModulesMap = new StackMap<String, StackMap<Element,HasWidgets>>();
	protected StackMap<Element, HasWidgets> nonuniqueModulesMap = new StackMap<Element, HasWidgets>();
	protected StackMap<String, IModule> multiViewModulesMap = new StackMap<String, IModule>();
	
	public ModulesInstalator(ParenthoodSocket pts, ModulesRegistrySocket reg, ModuleSocket ms, ModuleInteractionListener mil){
		this.registry = reg;
		this.moduleSocket = ms;
		this.moduleInteractionListener = mil;
		this.parenthood = pts;
		singleViewModules = new ArrayList<IModule>();
	}
	

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
			if (!multiViewModulesMap.containsKey(responseIdentifier)){
				IModule currModule = registry.createModule(element.getNodeName());
				multiViewModulesMap.put(responseIdentifier, currModule);
				parenthood.addChild(currModule);							
			}
			uniqueModulesMap.get(responseIdentifier).put(element, placeholder);
		} else {
			nonuniqueModulesMap.put(element, placeholder);
		}
	}

	@Override
	public void createSingleViewModule(Element element, HasWidgets parent,BodyGeneratorSocket bodyGeneratorSocket) {
		IModule module = registry.createModule(element.getNodeName());
		
		parenthood.addChild(module);
		
		if (module instanceof ISingleViewWithBodyModule){
			parenthood.pushParent((ISingleViewWithBodyModule) module);
			((ISingleViewWithBodyModule) module).initModule(element, moduleSocket, moduleInteractionListener, bodyGeneratorSocket);
			parenthood.popParent();
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
					currModule = multiViewModulesMap.get(responseIdentifier);
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
			
			if (currModule != null){
				modules.add(currModule);
			}
			
		}
		return modules;
	}

	public List<IModule> getInstalledSingleViewModules(){
		return singleViewModules;
	}
	
	public void setInitialParent(ISingleViewWithBodyModule parent){
		parenthood.pushParent(parent);
	}
		
}
