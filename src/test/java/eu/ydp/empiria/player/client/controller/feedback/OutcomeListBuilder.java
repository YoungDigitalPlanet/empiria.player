package eu.ydp.empiria.player.client.controller.feedback;

import com.google.common.collect.Maps;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;

import java.util.Map;

public class OutcomeListBuilder {

    private final Map<String, Outcome> map;

    public OutcomeListBuilder() {
        this.map = Maps.newHashMap();
    }

    public static OutcomeListBuilder init() {
        return new OutcomeListBuilder();
    }

    public OutcomeListBuilder put(Outcome outcome) {
        map.put(outcome.identifier, outcome);
        return this;
    }

    public Map<String, Outcome> getMap() {
        return map;
    }
}
