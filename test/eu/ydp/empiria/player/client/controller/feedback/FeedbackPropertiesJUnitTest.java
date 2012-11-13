package eu.ydp.empiria.player.client.controller.feedback;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import eu.ydp.gwtutil.client.StringUtils;

public class FeedbackPropertiesJUnitTest {
	
	private FeedbackProperties properties;
	
	@Before
	public void createProperties(){
		properties = new FeedbackProperties();
	}
	
	@Test
	public void shouldReturnDefaultValuesWhen_thereIsNoSuchProperty(){
		assertThat(properties.getBooleanProperty(FeedbackPropertyName.OK), is(equalTo(false)));
		assertThat(properties.getStringProperty(FeedbackPropertyName.OK), is(equalTo(StringUtils.EMPTY_STRING)));
	}
	
	@Test
	public void addingBooleanProperty(){		
		properties.addBooleanProperty(FeedbackPropertyName.ALL_OK, true);
		
		assertThat(properties.getBooleanProperty(FeedbackPropertyName.ALL_OK), is(equalTo(true)));
		assertThat(properties.getStringProperty(FeedbackPropertyName.ALL_OK), is(equalTo(StringUtils.EMPTY_STRING)));
	}
	
	@Test
	public void addingStringProperty(){
		properties.addStringProperty(FeedbackPropertyName.TEXT, "my text");
		
		assertThat(properties.getStringProperty(FeedbackPropertyName.TEXT), is(equalTo("my text")));
		assertThat(properties.getBooleanProperty(FeedbackPropertyName.TEXT), is(equalTo(false)));
	}
	
	@Test
	public void shouldMergePropetiesWithUniquePropertyNames(){
		properties.addBooleanProperty(FeedbackPropertyName.ALL_OK, true);
		properties.addBooleanProperty(FeedbackPropertyName.OK, false);
		
		FeedbackProperties newProperties = new FeedbackProperties();
		newProperties.addStringProperty(FeedbackPropertyName.RESULT, "res 0");
		newProperties.addBooleanProperty(FeedbackPropertyName.WRONG, true);
		
		properties.appendProperties(newProperties);
		
		assertThat(properties.getBooleanProperty(FeedbackPropertyName.ALL_OK), is(equalTo(true)));
		assertThat(properties.getBooleanProperty(FeedbackPropertyName.OK), is(equalTo(false)));
		assertThat(properties.getStringProperty(FeedbackPropertyName.RESULT), is(equalTo("res 0")));
		assertThat(properties.getBooleanProperty(FeedbackPropertyName.WRONG), is(equalTo(true)));
	}
	
	@Test
	public void shouldMergeAndOverrideProperties(){
		properties.addBooleanProperty(FeedbackPropertyName.ALL_OK, true);
		properties.addStringProperty(FeedbackPropertyName.RESULT, "res 1");
		
		FeedbackProperties newProperties = new FeedbackProperties();
		newProperties.addStringProperty(FeedbackPropertyName.RESULT, "res 0");
		newProperties.addBooleanProperty(FeedbackPropertyName.WRONG, true);
		
		properties.appendProperties(newProperties);
		
		assertThat(properties.getBooleanProperty(FeedbackPropertyName.ALL_OK), is(equalTo(true)));
		assertThat(properties.getStringProperty(FeedbackPropertyName.RESULT), is(equalTo("res 0")));
		assertThat(properties.getBooleanProperty(FeedbackPropertyName.WRONG), is(equalTo(true)));
	}

}
