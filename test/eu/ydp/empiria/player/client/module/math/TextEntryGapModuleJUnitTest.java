package eu.ydp.empiria.player.client.module.math;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_MAXLENGTH;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_WIDTH_ALIGN;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gwt.dev.util.collect.HashMap;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseValue;
import eu.ydp.empiria.player.client.module.binding.BindingType;
import eu.ydp.empiria.player.client.module.binding.BindingValue;
import eu.ydp.empiria.player.client.module.binding.gapmaxlength.GapMaxlengthBindingValue;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.gwtutil.xml.XMLParser;

public class TextEntryGapModuleJUnitTest {
	
	@Test
	public void testIfSubOrSupIsCorrectlyDetected() {
		TextEntryGapModule textGap = mockTextGap();
		
		Element node = XMLParser.parse("<gap type=\"text-entry\" uid=\"uid_0000\" />").getDocumentElement();
		Element parentNode = XMLParser.parse(
				"<mmultiscripts>" +
				"<gap type=\"text-entry\" uid=\"uid_0000\" />" +
				"<none/>" +
				"<mprescripts/><none/><none/>" +			
				"</mmultiscripts>").getDocumentElement();
		
		Assert.assertTrue(textGap.isSubOrSup(node, parentNode));
	}
	
	@Test
	public void testIfCorrectAnswerIsFound() {
		TextEntryGapModule textGap = mockTextGap();
		textGap.setIndex(1);
		Assert.assertTrue(textGap.getCorrectAnswer().equals("12"));
	}
	
	@Test
	public void testLongestAnswerLength() {
		TextEntryGapModule textGap = mockTextGap();
		int answerLength = textGap.getLongestAnswerLength();
		assertThat(answerLength, is(4));
	}
	
	@Test
	public void testMaxlengthBindingValue() {
		Map<String, String> styles = new HashMap<String, String>();
		styles.put(EMPIRIA_TEXTENTRY_GAP_MAXLENGTH, "3");
		TextEntryGapModuleMock textGap = mockTextGap();
		textGap.invokeSetMaxlengthBinding(styles);
		BindingValue bindingValue = textGap.getBindingValue(BindingType.GAP_MAXLENGHTS);
		int maxlength = ((GapMaxlengthBindingValue) bindingValue).getGapCharactersCount();
		assertThat(maxlength, is(3));
	}
	
	@Test
	public void testWidthBindingValue() {
		Map<String, String> styles = new HashMap<String, String>();
		styles.put(EMPIRIA_TEXTENTRY_GAP_WIDTH_ALIGN, "gap");
		TextEntryGapModuleMock textGap = mockTextGap();
		textGap.invokeSetWidthBinding(styles);
		int width = textGap.invokeCalculateTextBoxWidth();
		assertThat(width, is(80));
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
    
	public TextEntryGapModuleMock mockTextGap(Map<String, String> styles) {		
		return new TextEntryGapModuleMock(styles);
	}
	
	public TextEntryGapModuleMock mockTextGap() {		
		return new TextEntryGapModuleMock(new HashMap<String, String>());
	}
	
	private class TextEntryGapModuleMock extends TextEntryGapModule {
		
		public TextEntryGapModuleMock(Map<String, String> styles) {
			this.styles = styles;
		}
		
		@Override
		protected void setPresenter() {
			presenter = mock(InlineChoiceGapModulePresenter.class);
		}
		
		@Override
		protected EventsBus getEventsBus() {
			return mock(EventsBus.class);
		}
		
		@Override
		protected Response getResponse() {
			Response response = mock(Response.class);
			response.groups = new HashMap<String, List<Integer>>();
			response.correctAnswers = new CorrectAnswers();
			response.correctAnswers.add(new ResponseValue("1332"));
			response.correctAnswers.add(new ResponseValue("12"));
			response.correctAnswers.add(new ResponseValue("555"));
			
			return response;
		}
		
		@Override
		public int getFontSize() {
			return 20;
		}
		
		public void invokeSetMaxlengthBinding(Map<String, String> styles) {
			setMaxlengthBinding(styles, XMLParser.parse("<gap type=\"text-entry\" uid=\"uid_0000\" />").getDocumentElement());
		}
		
		public void invokeSetWidthBinding(Map<String, String> styles) {
			setWidthBinding(styles, XMLParser.parse("<gap type=\"text-entry\" uid=\"uid_0000\" />").getDocumentElement());
		}
		
		public int invokeCalculateTextBoxWidth() {
			return getLongestAnswerLength() * getFontSize();
		}
	}
}
