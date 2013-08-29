package eu.ydp.empiria.player.client.controller.feedback;

import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken.CORRECT;
import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken.WRONG;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackPropertiesCollectorTestHelper.ModuleInfo;
import eu.ydp.empiria.player.client.module.IContainerModule;
import eu.ydp.empiria.player.client.module.math.MathModule;

public class FeedbackPropertiesCollectorJUnitTest extends AbstractTestBase {

	private static final String MODULE_3 = "MODULE_3";

	private static final String MODULE_2 = "MODULE_2";

	private static final String MODULE_1 = "MODULE_1";

	private FeedbackPropertiesCollector propertiesCollector;

	private FeedbackPropertiesCollectorTestHelper helper;
	
	private ModuleInfo[] moduleInfos;
	
	@Before
	public void initialize(){
		helper = new FeedbackPropertiesCollectorTestHelper();
	}
	
	@Test
	public void shouldCollectPropertiesFromSingleModule() {
		moduleInfos = new ModuleInfo[] { 
								ModuleInfo.create(MODULE_1).setLastOk(CORRECT).setTodo(2).setDone(1).setErrors(0),
								ModuleInfo.create(MODULE_2).setLastOk(WRONG).setTodo(1).setDone(2).setErrors(0),
								ModuleInfo.create(MODULE_3).setLastOk(WRONG).setTodo(1).setDone(2).setErrors(0) 
							};
		
		initializeModules();
		FeedbackProperties sourceProperties = propertiesCollector.collect(helper.getSender(), helper.getSender());

		assertThat(sourceProperties.getBooleanProperty(FeedbackPropertyName.OK), is(equalTo(true)));
		assertThat(sourceProperties.getIntegerProperty(FeedbackPropertyName.DONE), is(equalTo(1)));
		assertThat(sourceProperties.getIntegerProperty(FeedbackPropertyName.TODO), is(equalTo(2)));
		assertThat(sourceProperties.getIntegerProperty(FeedbackPropertyName.ERRORS), is(equalTo(0)));
		assertThat(sourceProperties.getBooleanProperty(FeedbackPropertyName.ALL_OK), is(equalTo(false)));
		assertThat(sourceProperties.getDoubleProperty(FeedbackPropertyName.RESULT), is(equalTo(50.0)));
	}
	
	@Test
	public void shouldCollectContainerProperties(){
		moduleInfos = new ModuleInfo[] { 
								ModuleInfo.create(MODULE_1).setLastOk(CORRECT).setTodo(2).setDone(1).setErrors(0),
								ModuleInfo.create(MODULE_2).setLastOk(WRONG).setTodo(3).setDone(2).setErrors(1),
								ModuleInfo.create(MODULE_3).setLastOk(WRONG).setTodo(4).setDone(2).setErrors(1) 
							};
		initializeModules();
		FeedbackProperties properties = propertiesCollector.collect(helper.getContainer(), helper.getSender());
		
		assertThat(properties.getBooleanProperty(FeedbackPropertyName.OK), is(equalTo(true)));
		assertThat(properties.getIntegerProperty(FeedbackPropertyName.DONE), is(equalTo(5)));
		assertThat(properties.getIntegerProperty(FeedbackPropertyName.TODO), is(equalTo(9)));
		assertThat(properties.getIntegerProperty(FeedbackPropertyName.ERRORS), is(equalTo(2)));
		assertThat(properties.getBooleanProperty(FeedbackPropertyName.ALL_OK), is(equalTo(false)));
		assertThat(properties.getDoubleProperty(FeedbackPropertyName.RESULT), is(equalTo(56.0)));
	}
	
	@Test
	public void shouldCollectContainerPropertiesWhen_isAllOk(){
		moduleInfos = new ModuleInfo[] { 
								ModuleInfo.create(MODULE_1).setLastOk(WRONG).setTodo(2).setDone(2).setErrors(0),
								ModuleInfo.create(MODULE_2).setLastOk(WRONG).setTodo(3).setDone(3).setErrors(0),
								ModuleInfo.create(MODULE_3).setLastOk(WRONG).setTodo(4).setDone(4).setErrors(0) 
							};
		initializeModules();
		FeedbackProperties properties = propertiesCollector.collect(helper.getContainer(), helper.getSender());
		
		assertThat(properties.getBooleanProperty(FeedbackPropertyName.OK), is(equalTo(false)));
		assertThat(properties.getIntegerProperty(FeedbackPropertyName.DONE), is(equalTo(9)));
		assertThat(properties.getIntegerProperty(FeedbackPropertyName.TODO), is(equalTo(9)));
		assertThat(properties.getIntegerProperty(FeedbackPropertyName.ERRORS), is(equalTo(0)));
		assertThat(properties.getBooleanProperty(FeedbackPropertyName.ALL_OK), is(equalTo(true)));
		assertThat(properties.getDoubleProperty(FeedbackPropertyName.RESULT), is(equalTo(100.0)));
	}
	
	@Test
	public void shouldCollectContainerPropertiesWhen_allAreDoneWithErrors(){
		moduleInfos = new ModuleInfo[] { 
								ModuleInfo.create(MODULE_1).setLastOk(WRONG).setTodo(2).setDone(2).setErrors(0),
								ModuleInfo.create(MODULE_2).setLastOk(WRONG).setTodo(3).setDone(3).setErrors(0),
								ModuleInfo.create(MODULE_3).setLastOk(WRONG).setTodo(4).setDone(4).setErrors(1) 
							};
		initializeModules();
		FeedbackProperties properties = propertiesCollector.collect(helper.getContainer(), helper.getSender());
		
		assertThat(properties.getBooleanProperty(FeedbackPropertyName.OK), is(equalTo(false)));
		assertThat(properties.getIntegerProperty(FeedbackPropertyName.DONE), is(equalTo(9)));
		assertThat(properties.getIntegerProperty(FeedbackPropertyName.TODO), is(equalTo(9)));
		assertThat(properties.getIntegerProperty(FeedbackPropertyName.ERRORS), is(equalTo(1)));
		assertThat(properties.getBooleanProperty(FeedbackPropertyName.ALL_OK), is(equalTo(false)));
		assertThat(properties.getDoubleProperty(FeedbackPropertyName.RESULT), is(equalTo(100.0)));
	}
	
	@Test
	public void shouldReturnCorrectResultWhen_calledMoreThanOnce(){
		moduleInfos = new ModuleInfo[] { 
				ModuleInfo.create(MODULE_1).setLastOk(WRONG).setTodo(2).setDone(1).setErrors(0)
			};
		
		initializeModules();
		FeedbackProperties properties = propertiesCollector.collect(helper.getSender(), helper.getSender());
		assertThat(properties.getDoubleProperty(FeedbackPropertyName.RESULT), is(equalTo(50.0)));
		
		moduleInfos = new ModuleInfo[] { 
				ModuleInfo.create(MODULE_1).setLastOk(WRONG).setTodo(3).setDone(1).setErrors(0)
			};
		
		helper.createHierarchy(moduleInfos);
		propertiesCollector.setVariables(helper.getVariables());
		properties = propertiesCollector.collect(helper.getSender(), helper.getSender());
		assertThat(properties.getDoubleProperty(FeedbackPropertyName.RESULT), is(equalTo(33.0)));
	}
	
	@Test
	public void shouldReturnCorrectChildrenPropertiesWhen_parentIsMathModule(){
		moduleInfos = new ModuleInfo[] { 
				ModuleInfo.create(MODULE_1).setLastOk(WRONG).setTodo(2).setDone(2).setErrors(0),
				ModuleInfo.create(MODULE_2).setLastOk(WRONG).setTodo(3).setDone(3).setErrors(0),
				ModuleInfo.create(MODULE_3).setLastOk(WRONG).setTodo(4).setDone(4).setErrors(1) 
			};
		
		initializeModules(MathModule.class);
		FeedbackProperties properties = propertiesCollector.collect(helper.getContainer(), helper.getSender());
		
		assertThat(properties.getBooleanProperty(FeedbackPropertyName.OK), is(equalTo(false)));
		assertThat(properties.getIntegerProperty(FeedbackPropertyName.DONE), is(equalTo(9)));
		assertThat(properties.getIntegerProperty(FeedbackPropertyName.TODO), is(equalTo(9)));
		assertThat(properties.getIntegerProperty(FeedbackPropertyName.ERRORS), is(equalTo(1)));
		assertThat(properties.getBooleanProperty(FeedbackPropertyName.ALL_OK), is(equalTo(false)));
		assertThat(properties.getDoubleProperty(FeedbackPropertyName.RESULT), is(equalTo(100.0)));
	}
	
	private void initializeModules(Class<? extends IContainerModule> ModuleClass){
		helper.createHierarchy(moduleInfos, ModuleClass);
		initializeproperties();
	}
	
	private void initializeModules(){
		helper.createHierarchy(moduleInfos);
		initializeproperties();
	}
	
	private void initializeproperties() {
		propertiesCollector = injector.getInstance(FeedbackPropertiesCollector.class);
		propertiesCollector.setVariables(helper.getVariables());
	}
}
