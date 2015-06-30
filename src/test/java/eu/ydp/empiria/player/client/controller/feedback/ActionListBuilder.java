package eu.ydp.empiria.player.client.controller.feedback;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ActionType;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowTextAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowUrlAction;

import java.util.List;

public final class ActionListBuilder {

    private final List<FeedbackAction> actions;

    private ActionListBuilder() {
        actions = Lists.newArrayList();
    }

    public static ActionListBuilder create() {
        return new ActionListBuilder();
    }

    public ActionListBuilder addUrlAction(ActionType type, String href) {
        ShowUrlAction action = new ShowUrlAction();

        action.setType(type.getName());
        action.setHref(href);
        actions.add(action);

        return this;
    }

    public ActionListBuilder addTextAction(XMLContent xmlContent) {
        ShowTextAction action = new ShowTextAction();

        action.setContent(xmlContent);
        actions.add(action);

        return this;
    }

    public List<FeedbackAction> getList() {
        return ImmutableList.copyOf(actions);
    }

}
