package eu.ydp.empiria.player.client.module.containers;

import java.util.List;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.IActivity;
import eu.ydp.empiria.player.client.module.IContainerModule;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;

public abstract class ActivityContainerModuleBase implements IContainerModule, IActivity {

	protected ModuleSocket moduleSocket;
	
	private boolean markingAnswers = false;
	private boolean showingAnswers = false;

	@Override
	public void initModule(Element element, ModuleSocket ms, ModuleInteractionListener mil, BodyGeneratorSocket bgs) {
		this.moduleSocket = ms;
	}

	@Override
	public void lock(boolean lo) {
		List<IModule> children = moduleSocket.getChildren(this);
		for (IModule child : children){
			if (child instanceof IActivity){
				((IActivity)child).lock(lo);
			}
		}		
	}

	@Override
	public void reset() {
		List<IModule> children = moduleSocket.getChildren(this);
		for (IModule child : children){
			if (child instanceof IActivity){
				((IActivity)child).reset();
			}
		}
	}

	@Override
	public void markAnswers(boolean mark) {
		if (showingAnswers)
			showCorrectAnswers(false);
		doMarkAnswers(mark);
	}
	
	private void doMarkAnswers(boolean mark){
		List<IModule> children = moduleSocket.getChildren(this);
		for (IModule child : children){
			if (child instanceof IActivity){
				((IActivity)child).markAnswers(mark);
			}
		}
		markingAnswers = mark;
	}

	@Override
	public void showCorrectAnswers(boolean show) {
		if (markingAnswers)
			markAnswers(false);
		doShowCorrectAnswers(show);
	}
	
	private void doShowCorrectAnswers(boolean show){
		List<IModule> children = moduleSocket.getChildren(this);
		for (IModule child : children){
			if (child instanceof IActivity){
				((IActivity)child).showCorrectAnswers(show);
			}
		}
		
		showingAnswers = show;
	}
}
