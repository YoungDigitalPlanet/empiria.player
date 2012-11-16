package eu.ydp.empiria.player.client.controller.feedback.matcher;

public interface MatcherRegistryFactory {
	
	CountConditionMatcher getCountConditionMatcher();
	PropertyConditionMatcher getPropertyConditionMatcher();
	AndConditionMatcher getAndConditionMatcher();
	OrConditionMatcher getOrConditionMatcher();
	NotConditionMatcher getNotConditionMatcher();

}
