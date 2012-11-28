package eu.ydp.empiria.player.client.controller.feedback;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
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
	
	@Test
	public void gettingPropetyValueByPropertyStringName(){
		properties.addBooleanProperty(FeedbackPropertyName.ALL_OK, true);
		
		assertThat(properties.getProperty("allOk"), is(instanceOf(Boolean.class)));
		assertThat((Boolean)properties.getProperty("allOk"), is(equalTo(true)));
		assertThat("not existing name", (String)properties.getProperty("notExisting"), is(equalTo(FeedbackProperties.EMPTY)));
		assertThat("exisiting name, not existing value", (String)properties.getProperty("text"), is(equalTo(FeedbackProperties.EMPTY)));
	}
	
	@Test
	public void hasValueTest(){
		properties.addBooleanProperty(FeedbackPropertyName.ALL_OK, true);
		
		assertThat(properties.hasValue("allOk"), is(equalTo(true)));
		assertThat("not existing name", properties.hasValue("notExisting"), is(equalTo(false)));
		assertThat("exisiting name, not existing value", properties.hasValue("text"), is(equalTo(false)));
	}
	
	@Test
	public void mergingToEmptyProperties(){
		FeedbackProperties propertiesToMerge = new FeedbackProperties();
		properties = new FeedbackProperties();
		
		propertiesToMerge.addBooleanProperty(FeedbackPropertyName.ALL_OK, true);
		propertiesToMerge.addIntegerProperty(FeedbackPropertyName.TODO, 2);
		propertiesToMerge.addStringProperty(FeedbackPropertyName.TEXT, "text prop");
		propertiesToMerge.addDoubleProperty(FeedbackPropertyName.RESULT, 50.0);
		
		properties.merge(propertiesToMerge);
		
		assertThat(properties.getBooleanProperty(FeedbackPropertyName.ALL_OK), is(equalTo(true)));
		assertThat(properties.getIntegerProperty(FeedbackPropertyName.TODO), is(equalTo(2)));
		assertThat(properties.getStringProperty(FeedbackPropertyName.TEXT), is(equalTo("text prop")));
		assertThat(properties.getDoubleProperty(FeedbackPropertyName.RESULT), is(equalTo(50.0)));
	}
	
	@Test
	public void mergingToNotEmptyProperties(){
		FeedbackProperties propertiesToMerge = new FeedbackProperties();
		properties = new FeedbackProperties();
		properties.addBooleanProperty(FeedbackPropertyName.ALL_OK, false);
		properties.addIntegerProperty(FeedbackPropertyName.TODO, 3);
		properties.addStringProperty(FeedbackPropertyName.TEXT, "Good");
		properties.addDoubleProperty(FeedbackPropertyName.RESULT, 7.5);
		
		propertiesToMerge.addBooleanProperty(FeedbackPropertyName.ALL_OK, true);
		propertiesToMerge.addIntegerProperty(FeedbackPropertyName.TODO, 2);
		propertiesToMerge.addStringProperty(FeedbackPropertyName.TEXT, "text prop");
		propertiesToMerge.addDoubleProperty(FeedbackPropertyName.RESULT, 50.0);
		
		properties.merge(propertiesToMerge);
		
		assertThat(properties.getBooleanProperty(FeedbackPropertyName.ALL_OK), is(equalTo(false)));
		assertThat(properties.getIntegerProperty(FeedbackPropertyName.TODO), is(equalTo(5)));
		assertThat(properties.getStringProperty(FeedbackPropertyName.TEXT), is(equalTo("text prop")));
		assertThat(properties.getDoubleProperty(FeedbackPropertyName.RESULT), is(equalTo(57.5)));
	}
	
	@Test
	public void mergingEmptyProperties(){
		properties.addBooleanProperty(FeedbackPropertyName.ALL_OK, true);
		properties.addIntegerProperty(FeedbackPropertyName.TODO, 3);
		properties.addStringProperty(FeedbackPropertyName.TEXT, "Good");
		properties.addDoubleProperty(FeedbackPropertyName.RESULT, 7.5);
		
		properties.merge(new FeedbackProperties());
		
		assertThat(properties.getBooleanProperty(FeedbackPropertyName.ALL_OK), is(equalTo(true)));
		assertThat(properties.getIntegerProperty(FeedbackPropertyName.TODO), is(equalTo(3)));
		assertThat(properties.getStringProperty(FeedbackPropertyName.TEXT), is(equalTo("Good")));
		assertThat(properties.getDoubleProperty(FeedbackPropertyName.RESULT), is(equalTo(7.5)));
	}

}
