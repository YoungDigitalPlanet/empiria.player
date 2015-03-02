package eu.ydp.empiria.player.client.controller.feedback.matcher;

public interface MatcherRegistryFactory {

	CountConditionMatcher getCountConditionMatcher(MatcherRegistry registry);

	PropertyConditionMatcher getPropertyConditionMatcher(MatcherRegistry registry);

	AndConditionMatcher getAndConditionMatcher(MatcherRegistry registry);

	OrConditionMatcher getOrConditionMatcher(MatcherRegistry registry);

	NotConditionMatcher getNotConditionMatcher(MatcherRegistry registry);

}
