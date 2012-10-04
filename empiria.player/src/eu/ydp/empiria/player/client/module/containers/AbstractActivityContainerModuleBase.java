package eu.ydp.empiria.player.client.module.containers;

import java.util.List;

import eu.ydp.empiria.player.client.module.ContainerModuleBase;
import eu.ydp.empiria.player.client.module.IActivity;
import eu.ydp.empiria.player.client.module.IContainerModule;
import eu.ydp.empiria.player.client.module.IModule;

public abstract class AbstractActivityContainerModuleBase extends ContainerModuleBase implements IContainerModule, IActivity {

	private boolean markingAnswers = false;
	private boolean showingAnswers = false;

	@Override
	public void lock(boolean state) {
		List<IModule> children = getModuleSocket().getChildren(this);
		for (IModule child : children){
			if (child instanceof IActivity){
				((IActivity)child).lock(state);
			}
		}
	}

	@Override
	public void reset() {
		showCorrectAnswers(false);		
		markAnswers(false);
		List<IModule> children = getModuleSocket().getChildren(this);
		for (IModule child : children){
			if (child instanceof IActivity){
				((IActivity)child).reset();
			}
		}
	}

	@Override
	public void markAnswers(boolean mark) {
		if (!mark && markingAnswers || mark && !markingAnswers) {
			showCorrectAnswers(false);
			doMarkAnswers(mark);
		}
	}

	void doMarkAnswers(boolean mark){
		List<IModule> children = getModuleSocket().getChildren(this);
		for (IModule child : children){
			if (child instanceof IActivity){
				((IActivity)child).markAnswers(mark);
			}
		}
		markingAnswers = mark;
	}

	@Override
	public void showCorrectAnswers(boolean show) {		
		if (!show && showingAnswers || show && !showingAnswers) {
			markAnswers(false);
			doShowCorrectAnswers(show);
		}
	}

	void doShowCorrectAnswers(boolean show){
		List<IModule> children = getModuleSocket().getChildren(this);
		for (IModule child : children){
			if (child instanceof IActivity){
				((IActivity)child).showCorrectAnswers(show);
			}
		}

		showingAnswers = show;
	}
}
