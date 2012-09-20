package eu.ydp.empiria.player.client.module.choice;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Vector;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.Panel;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseValue;
import eu.ydp.empiria.player.client.module.components.choicebutton.MultiChoiceButton;

public class ChoiceModuleJUnitTest {	
	
	@Test
    public void resetSelectedChoicesExpectsAllSetToFalse() {
		choice3.setSelected(true);
		choice4.setSelected(true);
		choiceModule.updateResponse(null, false);
				
		choiceModule.reset();
		
		assertThat(choice1.isSelected(), equalTo(false));
		assertThat(choice2.isSelected(), equalTo(false));
		assertThat(choice3.isSelected(), equalTo(false));
		assertThat(choice4.isSelected(), equalTo(false));
    }	
		
	@Test
    public void showCorrectAnswersExpectsProperChoicesSelected() {
		choice2.setSelected(true);
		choiceModule.updateResponse(null, false);
						
		choiceModule.showCorrectAnswers(true);
				
		assertThat(choice1.isSelected(), equalTo(true));
		assertThat(choice2.isSelected(), equalTo(false));
		assertThat(choice3.isSelected(), equalTo(true));
		assertThat(choice4.isSelected(), equalTo(true));
    }		
	
	@Test
    public void resetInAnswerModeExpectsOperationIsNotExecuted() {
		choice1.setSelected(true);
		choiceModule.updateResponse(null, false);
				
		choiceModule.showCorrectAnswers(true);
		choiceModule.reset();		
		choiceModule.showCorrectAnswers(true);
		choiceModule.showCorrectAnswers(false);
				
		// Choice1 is set to true, because value is not changed in answer mode.
		// ActivityContainerModuleBase.reset() is (globally) responsible for this.		
		assertThat(choice1.isSelected(), equalTo(true));
    }	
	    
	@Test
    public void simpleChoiceMocksTestExpectValueChanged() {
		choice4.setSelected(true);

		// Check that the setSelected method has been successfully performed on mocked object,
		// otherwise the choice4 will be set to false.
		assertThat(choice1.isSelected(), equalTo(false));
		assertThat(choice4.isSelected(), equalTo(true));		
	}		
	
    @SuppressWarnings("unchecked")
	@Before
    public void setUpTest() {    			
    	choiceModule = mock(MockChoiceModule.class);
		doCallRealMethod().when(choiceModule).reset();
				
		choiceModule.interactionElements = new Vector<SimpleChoice>(); // NOPMD
		choice1 = mockSimpleChoice("MCHOICE_RESPONSE_1_0");
		choiceModule.interactionElements.add(choice1);		
		choice2 = mockSimpleChoice("MCHOICE_RESPONSE_1_1");		
		choiceModule.interactionElements.add(choice2);		
		choice3 = mockSimpleChoice("MCHOICE_RESPONSE_1_2");		
		choiceModule.interactionElements.add(choice3);		
		choice4 = mockSimpleChoice("MCHOICE_RESPONSE_1_3");		
		choiceModule.interactionElements.add(choice4);
		
		doCallRealMethod().when(choiceModule).showCorrectAnswers(anyBoolean());
		doNothing().when(choiceModule).fireStateChanged(anyBoolean());
		doCallRealMethod().when(choiceModule).updateResponse((SimpleChoice)anyObject(), anyBoolean());
		
		Response mockResponse = mock(Response.class);
		doCallRealMethod().when(mockResponse).set((Vector<String>)anyObject()); // NOPMD
		
		mockResponse.correctAnswers = new CorrectAnswers(); 
		
		ResponseValue responseValue1 = new ResponseValue("MCHOICE_RESPONSE_1_0");				
		mockResponse.correctAnswers.add(responseValue1);
		ResponseValue responseValue2 = new ResponseValue("MCHOICE_RESPONSE_1_2");
		mockResponse.correctAnswers.add(responseValue2);
		ResponseValue responseValue3 = new ResponseValue("MCHOICE_RESPONSE_1_3");
		mockResponse.correctAnswers.add(responseValue3);	
		
		when(choiceModule.getResponse()).thenReturn(mockResponse);		
    }    
    
    /**
     * Mock SimpleChoice
     * @return
     */
    private SimpleChoice mockSimpleChoice(String identifier) {
		SimpleChoice choice = mock(SimpleChoice.class);
		choice.identifier = identifier;
		choice.button = mock(MultiChoiceButton.class);
		doCallRealMethod().when(choice.button).setSelected(anyBoolean());
		doCallRealMethod().when(choice.button).setSelected(anyBoolean());
		doCallRealMethod().when(choice.button).isSelected();
		doCallRealMethod().when(choice).setSelected(anyBoolean());
		doCallRealMethod().when(choice).isSelected();		
		doCallRealMethod().when(choice).getIdentifier();
		choice.markAnswersPanel = mock(Panel.class);
		doNothing().when(choice.markAnswersPanel).setStyleName(anyString());
		doCallRealMethod().when(choice).reset();		
		return choice;
    }
    
    @BeforeClass
    public static void prepareTestEnviroment() {
    	/**
    	 * disable GWT.create() behavior for pure JUnit testing
    	 */
    	GWTMockUtilities.disarm();    	
    }
    
    @AfterClass
    public static void restoreEnviroment() {
    	/**
    	 * restore GWT.create() behavior
    	 */
    	GWTMockUtilities.restore();
    }
	
	private static class MockChoiceModule extends ChoiceModule {
		
		@Override
		public Response getResponse() {	// NOPMD		
			return super.getResponse();
		}
		
		@Override
		public void fireStateChanged(boolean userInteract) { // NOPMD	
			super.fireStateChanged(userInteract);
		}
	}

	private MockChoiceModule choiceModule;
	private SimpleChoice choice1;
	private SimpleChoice choice2;
	private SimpleChoice choice3;
	private SimpleChoice choice4;	
}
