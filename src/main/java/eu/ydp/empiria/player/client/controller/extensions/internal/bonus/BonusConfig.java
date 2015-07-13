package eu.ydp.empiria.player.client.controller.extensions.internal.bonus;

import com.google.common.collect.Lists;
import com.google.gwt.core.client.JsArray;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.js.BonusActionJs;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.js.BonusConfigJs;

import java.util.List;

public class BonusConfig {

    private final List<BonusAction> actions;

    public BonusConfig(List<BonusAction> actions) {
        this.actions = actions;
    }

    public List<BonusAction> getActions() {
        return this.actions;
    }

    public static BonusConfig fromJs(BonusConfigJs bonusConfigJs) {
        List<BonusAction> actions = getActions(bonusConfigJs);
        return new BonusConfig(actions);
    }

    private static List<BonusAction> getActions(BonusConfigJs bonusConfigJs) {
        List<BonusAction> actions = Lists.newArrayList();
        JsArray<BonusActionJs> jsActions = bonusConfigJs.getActions();

        for (int i = 0; i < jsActions.length(); i++) {
            BonusActionJs jsAction = jsActions.get(i);
            BonusAction action = BonusAction.fromJs(jsAction);
            actions.add(action);
        }

        return actions;
    }
}
