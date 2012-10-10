package eu.ydp.empiria.player.client.module.textentry;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;

public class TextEntryModuleJUnitTest {
	
	private TextEntryModule textEntryModule;
	private TextBox textBox;
	private Element moduleElement;

	@Test
    public void setDimensionsPixelWidth() {
		String testWidth = "60";
		Map<String, String> styles = new HashMap<String, String>();				
		styles.put(EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_WIDTH, testWidth);		
		mockTextEntryModule(styles);
		
		textEntryModule.setDimensions(textBox, moduleElement);
		
		Mockito.verify(textBox, Mockito.times(1)).setWidth(testWidth + "px");
		Mockito.verify(textBox, Mockito.times(1)).setWidth(anyString());
		assertThat(textEntryModule.maxLength, equalTo(null));
	}

	@Test
    public void setDimensionsMaxlength() {
		String testMaxNumberOfGaps = "number";
		Map<String, String> styles = new HashMap<String, String>();				
		styles.put(EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_MAXLENGTH, testMaxNumberOfGaps);		
		mockTextEntryModule(styles);
		
		textEntryModule.setDimensions(textBox, moduleElement);
		
		assertThat(textEntryModule.maxLength, equalTo(testMaxNumberOfGaps.toUpperCase(Locale.ENGLISH)));
	}

	@Test
    public void setDimensionsSetFontSize() {
		String fontSize = "12";
		Map<String, String> styles = new HashMap<String, String>();				
		styles.put(EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_FONT_SIZE, fontSize);
		mockTextEntryModule(styles);
		
		textEntryModule.setDimensions(textBox, moduleElement);
		
		Mockito.verify(textEntryModule, Mockito.times(1)).setFontSize(Integer.parseInt(fontSize), Unit.PX);
	}	
	
	@Test
    public void setDimensionsGapWidth() {
		String gapWidth = "100";
		Map<String, String> styles = new HashMap<String, String>();				
		styles.put(EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_WIDTH, gapWidth);
		mockTextEntryModule(styles);
		
		textEntryModule.setDimensions(textBox, moduleElement);
		
		Mockito.verify(textBox, Mockito.times(1)).setWidth(gapWidth + "px");
		assertThat(textEntryModule.widthBindingIdentifier, equalTo(null));		
	}		
	
    private void mockTextEntryModule(Map<String, String> styles) {
		textEntryModule = mock(TextEntryModule.class);
		doCallRealMethod().when(textEntryModule).setDimensions((TextBox)anyObject(), (Element)anyObject());
		when(textEntryModule.getStyles(moduleElement)).thenReturn(styles);			
	}

	@Before
	public void setUp() {
    	moduleElement = mock(Element.class);
		textBox = mock(TextBox.class);
		doCallRealMethod().when(textBox).setMaxLength(anyInt());
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

}
