package eu.ydp.empiria.player.client.controller.feedback.matcher;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackConditionMatcher;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackProperties;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackPropertyName;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.AndConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.NotConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.OrConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.PropertyConditionBean;

public class FeedbackConditionMatcherJUnitTest extends AbstractTestBase  {
	
	private FeedbackConditionMatcher matcher;
	
	@Before
	public void getMatcher() {
		matcher = injector.getInstance(FeedbackConditionMatcher.class);
	}
	
	@Test
	public void shouldReturnTrueForEmptyAndCondition() {
		FeedbackProperties properties = new FeedbackProperties();
		AndConditionBean condition = new AndConditionBean();
		
		assertThat(matcher.match(condition, properties), is(true));
	}
	
	@Test
	public void shouldReturnTrueForEmptyPropertyCondition() {
		FeedbackProperties properties = new FeedbackProperties();
		properties.addBooleanProperty(FeedbackPropertyName.OK, true);
		
		PropertyConditionBean propertyCondition = new PropertyConditionBean();
		propertyCondition.setProperty(FeedbackPropertyName.OK.getName());
		
		assertThat(matcher.match(propertyCondition, properties), is(true));
	}
	
	@Test
	public void shouldReturnFalseForNotConditionWhenPropertyConditionIsTrue() {
		FeedbackProperties properties = new FeedbackProperties();
		properties.addIntegerProperty(FeedbackPropertyName.WRONG, 3);
		
		PropertyConditionBean propertyCondition = new PropertyConditionBean();
		propertyCondition.setProperty(FeedbackPropertyName.WRONG.getName());
		propertyCondition.setValue("2");
		propertyCondition.setOperator(">");
		
		NotConditionBean notCondition = new NotConditionBean();
		notCondition.setPropertyConditions(Lists.newArrayList(propertyCondition));
		
		assertThat(matcher.match(notCondition, properties), is(false));
	}

	@Test
	public void shouldReturnFalseForAndConditionWhenOneOfThePropertyConditionsIsFalse() {
		assertThat(matcher.match(getAndCondition(), getFeedbackProperties()), is(false));
	}
	
	@Test
	public void shouldReturnTrueForOrConditionWhenOneOfThePropertyConditionsIsTrue() {
		assertThat(matcher.match(getOrCondition(), getFeedbackProperties()), is(true));
	}
	
	@Test
	public void shouldReturnFalseForAndConditionWhenOneOfTheContainerConditionsIsFalse() {
		AndConditionBean andCondition = new AndConditionBean();
		andCondition.setAndConditions(Lists.newArrayList(getAndCondition()));
		andCondition.setOrConditions(Lists.newArrayList(getOrCondition()));
		
		assertThat(matcher.match(andCondition, getFeedbackProperties()), is(false));
	}
	
	@Test
	public void shouldReturnTrueForOrConditionWhenOneOfTheContainerConditionsIsTrue() {
		OrConditionBean orCondition = new OrConditionBean();
		orCondition.setAndConditions(Lists.newArrayList(getAndCondition()));
		orCondition.setOrConditions(Lists.newArrayList(getOrCondition()));
		
		assertThat(matcher.match(orCondition, getFeedbackProperties()), is(true));
	}
	
	private AndConditionBean getAndCondition() {
		PropertyConditionBean propertyCondition1 = getOkPropertyCondition();
		PropertyConditionBean propertyCondition2 = getWrongPropertyCondition();
		
		AndConditionBean andCondition = new AndConditionBean();
		andCondition.setPropertyConditions(Lists.newArrayList(propertyCondition1, propertyCondition2));
		
		return andCondition;
	}
	
	private OrConditionBean getOrCondition() {
		PropertyConditionBean propertyCondition1 = getOkPropertyCondition();
		PropertyConditionBean propertyCondition2 = getWrongPropertyCondition();
		
		OrConditionBean orCondition = new OrConditionBean();
		orCondition.setPropertyConditions(Lists.newArrayList(propertyCondition1, propertyCondition2));
		
		return orCondition;
	}
	
	private PropertyConditionBean getOkPropertyCondition() {
		PropertyConditionBean propertyCondition = new PropertyConditionBean();
		propertyCondition.setProperty(FeedbackPropertyName.OK.getName());
		propertyCondition.setValue("odpowiedz");
		propertyCondition.setOperator("==");
		
		return propertyCondition;
	}
	
	private PropertyConditionBean getWrongPropertyCondition() {
		PropertyConditionBean propertyCondition = new PropertyConditionBean();
		propertyCondition.setProperty(FeedbackPropertyName.WRONG.getName());
		propertyCondition.setValue("odpowiedz");
		propertyCondition.setOperator("!=");
		
		return propertyCondition;
	}
	
	private FeedbackProperties getFeedbackProperties() {
		FeedbackProperties properties = new FeedbackProperties();
		properties.addStringProperty(FeedbackPropertyName.OK, "odpowiedz");
		properties.addStringProperty(FeedbackPropertyName.WRONG, "odpowiedz");
		
		return properties;
	}
}
