package eu.ydp.empiria.player.client.controller.feedback.matcher;

import java.util.Map;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.feedback.structure.condition.AndConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.CountConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.NotConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.OrConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.PropertyConditionBean;

public class MatcherRegistry {

	Map<Class<? extends FeedbackCondition>, FeedbackMatcher> map;

	@Inject
	private MatcherRegistryFactory factory;

	public void init() {
		map = Maps.newHashMap();
		map.put(CountConditionBean.class, factory.getCountConditionMatcher(this));
		map.put(PropertyConditionBean.class, factory.getPropertyConditionMatcher(this));
		map.put(AndConditionBean.class, factory.getAndConditionMatcher(this));
		map.put(OrConditionBean.class, factory.getOrConditionMatcher(this));
		map.put(NotConditionBean.class, factory.getNotConditionMatcher(this));
	}

	public FeedbackMatcher getMatcher(FeedbackCondition condition) {
		if (map == null) {
			init();
		}
		return map.get(condition.getClass());
	}

}
