package eu.ydp.empiria.player.client.module.containers;

import eu.ydp.empiria.player.client.module.core.flow.Activity;
import eu.ydp.empiria.player.client.module.core.flow.Lockable;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.module.core.flow.Resetable;

import java.util.List;

public class ModulesActivitiesController {

    public void lock(List<IModule> modules, boolean state) {
        for (IModule child : modules) {
            if (child instanceof Lockable) {
                ((Lockable) child).lock(state);
            }
        }
    }

    public void reset(List<IModule> modules) {
        for (IModule child : modules) {
            if (child instanceof Resetable) {
                ((Resetable) child).reset();
            }
        }
    }

    public void markAnswers(List<IModule> modules, boolean mark) {
        for (IModule child : modules) {
            if (child instanceof Activity) {
                ((Activity) child).markAnswers(mark);
            }
        }
    }

    public void showCorrectAnswers(List<IModule> modules, boolean show) {
        for (IModule child : modules) {
            if (child instanceof Activity) {
                ((Activity) child).showCorrectAnswers(show);
            }
        }

    }
}
