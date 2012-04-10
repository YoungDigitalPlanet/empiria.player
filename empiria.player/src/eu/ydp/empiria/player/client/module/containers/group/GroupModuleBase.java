package eu.ydp.empiria.player.client.module.containers.group;

import com.google.gwt.dom.client.Document;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.IGroup;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.containers.ContainerModuleBase;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;

public class GroupModuleBase extends ContainerModuleBase implements IGroup {
	
	protected GroupIdentifier groupIdentifier;
	
	public GroupModuleBase(){
	}
	
	@Override
	public void initModule(Element element, ModuleSocket moduleSocket, ModuleInteractionListener mil, BodyGeneratorSocket bodyGeneratorSocket) {
		bodyGeneratorSocket.generateBody(element, getContainer());

		String className = element.getAttribute("class");
		if (className != null  &&  !"".equals(className)  &&  getView() != null){
			getView().addStyleName(className);
		}
		String id = element.getAttribute("id");
		if (id == null  ||  "".equals(id)){
			id = Document.get().createUniqueId();
		}
		if (getView() != null){
			getView().getElement().setId(id);			
		}
		groupIdentifier = new DefaultGroupIdentifier(id);
		super.moduleSocket = moduleSocket;
		
	}
	
	@Override
	public GroupIdentifier getGroupIdentifier() {
		return groupIdentifier;
	}

}
