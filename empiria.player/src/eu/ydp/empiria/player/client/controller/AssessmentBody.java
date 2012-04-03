package eu.ydp.empiria.player.client.controller;

import java.util.List;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.body.BodyGenerator;
import eu.ydp.empiria.player.client.controller.body.ModuleEventsListener;
import eu.ydp.empiria.player.client.controller.body.ModulesInstalator;
import eu.ydp.empiria.player.client.controller.body.ParenthoodManager;
import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallback;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.IUniqueModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.containers.AssessmentBodyModule;
import eu.ydp.empiria.player.client.module.pageinpage.PageInPageModule;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;

public class AssessmentBody {
	
	protected DisplayContentOptions options;
	protected ModuleSocket moduleSocket;
	protected ModulesRegistrySocket modulesRegistrySocket;
	protected ModuleEventsListener moduleEventsListener;
	protected Panel pageSlot;
	protected ParenthoodManager parenthood;
	
	public AssessmentBody(DisplayContentOptions options, ModuleSocket moduleSocket,
			final InteractionEventsListener interactionEventsListener, ModulesRegistrySocket modulesRegistrySocket) {
		this.options = options;
		this.moduleSocket = moduleSocket;
		this.modulesRegistrySocket = modulesRegistrySocket;
		
		this.moduleEventsListener = new ModuleEventsListener() {
			
			@Override
			public void onMediaSoundPlay(String url,
					MediaInteractionSoundEventCallback callback) {				
			}
			
			@Override
			public void onFeedbackSoundPlay(String url) {				
			}
			
			@Override
			public void onStateChanged(boolean userInteract, IUniqueModule sender) {
			}
		};
		
		parenthood = new ParenthoodManager();
	}
	
	public Widget init(Element assessmentBodyElement){
		
		ModulesInstalator instalator = new ModulesInstalator(parenthood, modulesRegistrySocket, moduleSocket, moduleEventsListener);
		BodyGenerator generator = new BodyGenerator(instalator, options);
		
		AssessmentBodyModule bodyModule = new AssessmentBodyModule();
		instalator.setInitialParent(bodyModule);
		bodyModule.initModule(assessmentBodyElement, generator);
		
		pageSlot = findPageInPage(instalator);
		
		return bodyModule.getView();
	}
	
	public Panel getPageSlot(){
		return pageSlot;
	}
	
	private Panel findPageInPage(ModulesInstalator instalator){
		Panel pagePanel = null;
		
		for(IModule module: instalator.getInstalledSingleViewModules()){
			if(module instanceof PageInPageModule){
				pagePanel = (Panel)((PageInPageModule) module).getView();
				break;
			}
		}
		
		return pagePanel;
	}
	
	public IModule getModuleParent(IModule module) {
		return parenthood.getParent(module);
	}

	public List<IModule> getModuleChildren(IModule parent) {
		return parenthood.getChildren(parent);
	}
	
}
