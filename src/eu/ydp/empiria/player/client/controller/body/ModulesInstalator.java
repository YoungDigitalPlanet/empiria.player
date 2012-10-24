package eu.ydp.empiria.player.client.controller.body;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.components.ModulePlaceholder;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IInlineModule;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.IMultiViewModule;
import eu.ydp.empiria.player.client.module.ISingleViewModule;
import eu.ydp.empiria.player.client.module.ISingleViewSimpleModule;
import eu.ydp.empiria.player.client.module.ISingleViewWithBodyModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import eu.ydp.gwtutil.client.collections.StackMap;

public class ModulesInstalator implements ModulesInstalatorSocket {

	protected ModulesRegistrySocket registry;
	protected ModuleSocket moduleSocket;
	protected InteractionEventsListener interactionListener;
	protected ParenthoodGeneratorSocket parenthood;
	protected List<IModule> singleViewModules;

	protected StackMap<String, StackMap<Element, HasWidgets>> uniqueModulesMap = new StackMap<String, StackMap<Element,HasWidgets>>();
	protected StackMap<Element, StackMap<IModule, HasWidgets>> nonuniqueModulesMap = new StackMap<Element, StackMap<IModule,HasWidgets>>();
	protected StackMap<String, IModule> multiViewModulesMap = new StackMap<String, IModule>();

	public ModulesInstalator(ParenthoodGeneratorSocket pts, ModulesRegistrySocket reg, ModuleSocket ms, InteractionEventsListener mil){
		this.registry = reg;
		this.moduleSocket = ms;
		this.interactionListener = mil;
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

	@Override
	public void registerModuleView(Element element, HasWidgets parent){
		String responseIdentifier = element.getAttribute("responseIdentifier");

		ModulePlaceholder placeholder = new ModulePlaceholder();
		parent.add(placeholder);

		if (responseIdentifier != null){
			if (!uniqueModulesMap.containsKey(responseIdentifier)){
				uniqueModulesMap.put(responseIdentifier, new StackMap<Element, HasWidgets>());
			}
			if (!multiViewModulesMap.containsKey(responseIdentifier)){
				IModule currModule = registry.createModule(element);
				multiViewModulesMap.put(responseIdentifier, currModule);
				parenthood.addChild(currModule);
			}
			uniqueModulesMap.get(responseIdentifier).put(element, placeholder);
		} else {
			if(!nonuniqueModulesMap.containsKey(element)){
				nonuniqueModulesMap.put(element, new StackMap<IModule, HasWidgets>());
			}
			
			IModule currModule = registry.createModule(element);
			nonuniqueModulesMap.get(element).put(currModule, placeholder);		
			parenthood.addChild(currModule);
		}
	}

	@Override
	public void createSingleViewModule(Element element, HasWidgets parent,BodyGeneratorSocket bodyGeneratorSocket) {
		IModule module = registry.createModule(element);

		if (module != null) {
			parenthood.addChild(module);
	
			if (module instanceof ISingleViewWithBodyModule){
				parenthood.pushParent((ISingleViewWithBodyModule) module);
				((ISingleViewWithBodyModule) module).initModule(element, moduleSocket, interactionListener, bodyGeneratorSocket);
				parenthood.popParent();
			} else if (module instanceof ISingleViewSimpleModule){
				((ISingleViewSimpleModule)module).initModule(element, moduleSocket, interactionListener);
			}else if(module instanceof IInlineModule){
				((IInlineModule) module).initModule(element, moduleSocket);
			}
			if (((ISingleViewModule)module).getView() instanceof Widget ){
				parent.add( ((ISingleViewModule)module).getView() );
			}
	
			singleViewModules.add(module);
		}
	}

	public void installMultiViewNonuniuqeModules(){
		for (Element currElement : nonuniqueModulesMap.getKeys()){
			StackMap<IModule, HasWidgets> moduleMap = nonuniqueModulesMap.get(currElement);
			IModule module = moduleMap.getKeys().get(0);
			
			if(module instanceof IMultiViewModule){
				IMultiViewModule multiViewModule = (IMultiViewModule) module;
				List<HasWidgets> placeholders = moduleMap.getValues();
				
				multiViewModule.initModule(moduleSocket, interactionListener);
				multiViewModule.addElement(currElement);
				multiViewModule.installViews(placeholders);
			}
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
						((IMultiViewModule)currModule).initModule(moduleSocket, interactionListener);
					}
					//registerModule(currModule);
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

	public void setInitialParent(HasChildren parent){
		parenthood.pushParent(parent);
	}

}
