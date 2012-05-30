package eu.ydp.empiria.player.client.module.containers;

import java.util.List;

import eu.ydp.empiria.player.client.module.ContainerModuleBase;
import eu.ydp.empiria.player.client.module.IActivity;
import eu.ydp.empiria.player.client.module.IContainerModule;
import eu.ydp.empiria.player.client.module.IModule;

public abstract class ActivityContainerModuleBase extends ContainerModuleBase implements IContainerModule, IActivity {


	private boolean markingAnswers = false;
	private boolean showingAnswers = false;

	@Override
	public void lock(boolean lo) {
		List<IModule> children = getModuleSocket().getChildren(this);
		for (IModule child : children){
			if (child instanceof IActivity){
				((IActivity)child).lock(lo);
			}
		}
	}

	@Override
	public void reset() {
		List<IModule> children = getModuleSocket().getChildren(this);
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
		if (markingAnswers)
			markAnswers(false);
		doShowCorrectAnswers(show);
	}

	private void doShowCorrectAnswers(boolean show){
		List<IModule> children = getModuleSocket().getChildren(this);
		for (IModule child : children){
			if (child instanceof IActivity){
				((IActivity)child).showCorrectAnswers(show);
			}
		}

		showingAnswers = show;
	}
}
