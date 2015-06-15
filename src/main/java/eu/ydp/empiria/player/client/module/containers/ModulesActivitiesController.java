package eu.ydp.empiria.player.client.module.containers;

import eu.ydp.empiria.player.client.module.IActivity;
import eu.ydp.empiria.player.client.module.ILockable;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.IResetable;

import java.util.List;

public class ModulesActivitiesController {

	public void lock(List<IModule> modules, boolean state) {
		for (IModule child : modules) {
			if (child instanceof IActivity) {
				((ILockable) child).lock(state);
			}
		}
	}

	public void reset(List<IModule> modules) {
		for (IModule child : modules) {
			if (child instanceof IResetable) {
				((IResetable) child).reset();
			}
		}
	}

	public void markAnswers(List<IModule> modules, boolean mark) {
		for (IModule child : modules) {
			if (child instanceof IActivity) {
				((IActivity) child).markAnswers(mark);
			}
		}
	}

	public void showCorrectAnswers(List<IModule> modules, boolean show) {
		for (IModule child : modules) {
			if (child instanceof IActivity) {
				((IActivity) child).showCorrectAnswers(show);
			}
		}

	}
}
