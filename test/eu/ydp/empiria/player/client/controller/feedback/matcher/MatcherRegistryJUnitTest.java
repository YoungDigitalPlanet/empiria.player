package eu.ydp.empiria.player.client.controller.feedback.matcher;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackRegistry;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.AndConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.CountConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.NotConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.OrConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.PropertyConditionBean;

public class MatcherRegistryJUnitTest extends AbstractTestBase {
	
	private MatcherRegistry registry;
	
	@Override
	public void setUp() {
		super.setUp();
		registry = injector.getInstance(MatcherRegistry.class);
	}
	
	@Test
	public void shouldReturnCorrectMatcherForFeedbackCondition() {
		assertThat(registry.getMatcher(new PropertyConditionBean()), is(instanceOf(PropertyConditionMatcher.class)));
		assertThat(registry.getMatcher(new CountConditionBean()), is(instanceOf(CountConditionMatcher.class)));
		assertThat(registry.getMatcher(new AndConditionBean()), is(instanceOf(AndConditionMatcher.class)));
		assertThat(registry.getMatcher(new OrConditionBean()), is(instanceOf(OrConditionMatcher.class)));
		assertThat(registry.getMatcher(new NotConditionBean()), is(instanceOf(NotConditionMatcher.class)));
	}
	
	@Test
	public void shouldReturnSameMatcherForSameFeedbackCondition() {
		FeedbackMatcher propertyMatcher1 = registry.getMatcher(new PropertyConditionBean());
		FeedbackMatcher propertyMatcher2 = registry.getMatcher(new PropertyConditionBean());
		
		FeedbackMatcher countMatcher1 = registry.getMatcher(new CountConditionBean());
		FeedbackMatcher countMatcher2 = registry.getMatcher(new CountConditionBean());
		
		FeedbackMatcher andMatcher1 = registry.getMatcher(new AndConditionBean());
		FeedbackMatcher andMatcher2 = registry.getMatcher(new AndConditionBean());
		
		FeedbackMatcher orMatcher1 = registry.getMatcher(new OrConditionBean());
		FeedbackMatcher orMatcher2 = registry.getMatcher(new OrConditionBean());
		
		FeedbackMatcher notMatcher1 = registry.getMatcher(new NotConditionBean());
		FeedbackMatcher notMatcher2 = registry.getMatcher(new NotConditionBean());
		
		assertThat(propertyMatcher1.equals(propertyMatcher2), is(true));
		assertThat(countMatcher1.equals(countMatcher2), is(true));
		assertThat(andMatcher1.equals(andMatcher2), is(true));
		assertThat(orMatcher1.equals(orMatcher2), is(true));
		assertThat(notMatcher1.equals(notMatcher2), is(true));
	}

}
