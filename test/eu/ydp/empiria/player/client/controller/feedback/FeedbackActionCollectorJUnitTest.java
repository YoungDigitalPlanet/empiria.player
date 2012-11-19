package eu.ydp.empiria.player.client.controller.feedback;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Ordering;

import eu.ydp.empiria.player.client.controller.feedback.FeedbackAppendActionTestData.FeedbackActionData;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ActionType;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowTextAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowUrlAction;
import eu.ydp.empiria.player.client.module.IModule;

public class FeedbackActionCollectorJUnitTest {
	
	private IModule source;
	
	private FeedbackActionCollector collector;
	
	@Before
	public void initialize(){
		source = mock(IModule.class);
		collector = new FeedbackActionCollector(source);		
	}
	
	@Test
	public void shouldAddSourceModule(){		
		assertThat(collector.getSource(), is(equalTo(source)));
		assertThat(collector.getSourceProperties(source), is(nullValue()));
	}
	
	@Test
	public void shouldAppendPropertiesToSource(){
		//given
		FeedbackProperties sourceProperties = new FeedbackProperties();
		FeedbackProperties moduleProperties = new FeedbackProperties();
		IModule module = mock(IModule.class);
		String textPropertyValue = "correct text";
		
		sourceProperties.addBooleanProperty(FeedbackPropertyName.ALL_OK, true);
		sourceProperties.addStringProperty(FeedbackPropertyName.TEXT, textPropertyValue);
		
		moduleProperties.addBooleanProperty(FeedbackPropertyName.OK, true);
		moduleProperties.addIntegerProperty(FeedbackPropertyName.RESULT, 2);
		moduleProperties.addBooleanProperty(FeedbackPropertyName.WRONG, false);
		
		//when
		collector.appendPropertiesToSource(sourceProperties, source);
		collector.appendPropertiesToSource(moduleProperties, module);
		
		FeedbackProperties sourceRetrievedProperties = collector.getSourceProperties(source);
		FeedbackProperties moduleRetrievedProperties = collector.getSourceProperties(module);
		
		//then
		assertThat("sourceProps", sourceRetrievedProperties, is(notNullValue()));
		assertThat("sourceProps, allok", sourceRetrievedProperties.getBooleanProperty(FeedbackPropertyName.ALL_OK), is(equalTo(true)));
		assertThat("sourceProps, text", sourceRetrievedProperties.getStringProperty(FeedbackPropertyName.TEXT), is(equalTo(textPropertyValue)));
		assertThat("sourceProps, ok", sourceRetrievedProperties.getBooleanProperty(FeedbackPropertyName.OK), is(equalTo(false)));
		
		assertThat("moduleProps", moduleRetrievedProperties, is(notNullValue()));
		assertThat("moduleProps, ok", moduleRetrievedProperties.getBooleanProperty(FeedbackPropertyName.OK), is(equalTo(true)));
		assertThat("moduleProps, result", moduleRetrievedProperties.getIntegerProperty(FeedbackPropertyName.RESULT), is(equalTo(2)));
		assertThat("moduleProps, wrong", moduleRetrievedProperties.getBooleanProperty(FeedbackPropertyName.WRONG), is(equalTo(false)));
		assertThat("moduleProps, allok", moduleRetrievedProperties.getBooleanProperty(FeedbackPropertyName.ALL_OK), is(equalTo(false)));
	}
	
	@Test
	public void shouldUpdateSourceProperties(){
		//given
		FeedbackProperties properties = new FeedbackProperties();
		String textPropertyValue = "correct text";
		
		properties.addBooleanProperty(FeedbackPropertyName.ALL_OK, true);
		properties.addStringProperty(FeedbackPropertyName.TEXT, "fist value");
		collector.appendPropertiesToSource(properties, source);
		
		FeedbackProperties retrievedProperties = collector.getSourceProperties(source);
		retrievedProperties.addBooleanProperty(FeedbackPropertyName.OK, true);
		retrievedProperties.addStringProperty(FeedbackPropertyName.TEXT, textPropertyValue);
		
		//when
		FeedbackProperties newRetrievedProperties = collector.getSourceProperties(source);
		
		//then
		assertThat(newRetrievedProperties.getStringProperty(FeedbackPropertyName.TEXT), is(equalTo(textPropertyValue)));
		assertThat(newRetrievedProperties.getBooleanProperty(FeedbackPropertyName.ALL_OK), is(equalTo(true)));
		assertThat(newRetrievedProperties.getBooleanProperty(FeedbackPropertyName.OK), is(equalTo(true)));
		assertThat(newRetrievedProperties.getBooleanProperty(FeedbackPropertyName.WRONG), is(equalTo(false)));
	}	
	
	@Test
	public void shouldAppendActionsToSource(){
		//given
		FeedbackAppendActionTestData testData = new FeedbackAppendActionTestData();
		prepareAndAppendActions(testData);
		
		//when		
		List<FeedbackAction> actions = collector.getActions();
		actions = Ordering.usingToString().sortedCopy(actions);
		
		//then
		assertThat(actions.size(), is(equalTo(5)));
		
		for(int i = 0; i < testData.getActionsSize(); i++){
			FeedbackActionData actionData = testData.getActionDataAtIndex(i);
			FeedbackAction expectedAction = testData.getActionAtIndex(i);
			
			assertThat(actions.get(i).getClass().getName(), is(equalTo(actionData.getActionClass().getName())));
			
			if(actions.get(i) instanceof ShowTextAction){
				ShowTextAction textAction = (ShowTextAction) expectedAction;
				ShowTextAction actualAction = (ShowTextAction) actions.get(i);
				
				assertThat(actualAction.getText(), is(equalTo(textAction.getText())));
			}else if(actions.get(i) instanceof ShowUrlAction){
				ShowUrlAction urlAction = (ShowUrlAction) expectedAction;
				ShowUrlAction actualAction = (ShowUrlAction) actions.get(i);
				
				assertThat(actualAction.getType(), is(equalTo(urlAction.getType())));
				assertThat(actualAction.getHref(), is(equalTo(urlAction.getHref())));
			}
			
		}		
	}
	
	private void prepareAndAppendActions(FeedbackAppendActionTestData testData){
		IModule module = mock(IModule.class);
		
		testData.addShowTextAction(0, source, "Very goood!!!");
		testData.addShowUrlAction(2, source, "/commons/very_good.mp3", ActionType.NARRATION);
		testData.addShowUrlAction(3, source, "/commons/very_good.mp4", ActionType.VIDEO);
		
		testData.addShowTextAction(1, module, "wrong");
		testData.addShowUrlAction(4, module, "/commons/wrong.mp3", ActionType.NARRATION);
		
		collector.appendActionsToSource(testData.getModuleActions(source), source);
		collector.appendActionsToSource(testData.getModuleActions(module), module);
	}
}
