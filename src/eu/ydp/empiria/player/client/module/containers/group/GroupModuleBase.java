package eu.ydp.empiria.player.client.module.containers.group;

import com.google.gwt.dom.client.Document;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.module.IGroup;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.binding.BindingProxy;
import eu.ydp.empiria.player.client.module.binding.BindingType;
import eu.ydp.empiria.player.client.module.binding.gapwidth.GapWidthBindingManager;
import eu.ydp.empiria.player.client.module.containers.SimpleContainerModuleBase;

public abstract class GroupModuleBase<T> extends SimpleContainerModuleBase<T> implements IGroup, BindingProxy {

	protected GroupIdentifier groupIdentifier;
	private String moduleId;
	private GapWidthBindingManager gapWidthBindingManager;

	public GroupModuleBase(){
	}

	@Override
	protected String getModuleId(){
		if (super.getModuleId() == null  ||  "".equals(super.getModuleId().trim()) ){
			if (moduleId == null)
				moduleId = Document.get().createUniqueId();
			return moduleId;
		}
		return super.getModuleId();
	}
	
	@Override
	public void initModule(Element element, ModuleSocket moduleSocket, InteractionEventsListener mil, BodyGeneratorSocket bodyGeneratorSocket) {
		super.initModule(element, moduleSocket, mil, bodyGeneratorSocket);

		groupIdentifier = new DefaultGroupIdentifier(getModuleId());

	}

	@Override
	public GroupIdentifier getGroupIdentifier() {
		return groupIdentifier;
	}

	@Override
	public GapWidthBindingManager getBindingManager(BindingType bindingType) {
		if (bindingType == BindingType.GAP_WIDTHS){
			if (gapWidthBindingManager == null)
				gapWidthBindingManager = createGapWidthBindingManager();
			return gapWidthBindingManager;
		}
		return null;
	}
	
	protected GapWidthBindingManager createGapWidthBindingManager(){
		return new GapWidthBindingManager(false);
	}

}
