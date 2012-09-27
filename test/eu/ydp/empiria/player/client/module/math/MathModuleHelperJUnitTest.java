package eu.ydp.empiria.player.client.module.math;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_MATH_INLINECHOICE_EMPTY_OPTION;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.VALUE_SHOW;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.components.ExListBox;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.math.MathModuleHelper.GapType;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;

public class MathModuleHelperJUnitTest {
	
	@Test
	public void inlineCholiceGapShowEmptyOption() {
		MathModuleHelper helper = mockHelper(true);
		
		helper.initStyles();
		
		assertThat(helper.inlineCholiceGapShowEmptyOption, equalTo(true));		
	}

	@Test
	public void emptyGapAddedWhenInlineChoiceEmptyOptionSetToShow() {	
		MathModuleHelper helper = mockHelper(true);
		
		helper.initStyles();
		List<MathGap> gaps = helper.initGaps();
		InlineChoiceGap gap = (InlineChoiceGap)gaps.get(0);		
		ExListBox listBox = gap.getListBox();		
		
		Mockito.verify(listBox, Mockito.times(1)).setSelectedIndex(0);
		Mockito.verify(helper, Mockito.times(2)).createInlineHTML(MathModuleHelper.INLINE_HTML_NBSP);
	}
	
	@Test
	public void noGapsAddedWhenInlineChoiceEmptySetToHide() {		
		MathModuleHelper helper = mockHelper(false);
		
		helper.initStyles();
		List<MathGap> gaps = helper.initGaps();
		InlineChoiceGap gap = (InlineChoiceGap)gaps.get(0);
		ExListBox listBox = gap.getListBox();		
		
		Mockito.verify(listBox, Mockito.times(1)).setSelectedIndex(-1);
	}	
	
	public MathModuleHelper mockHelper(boolean defineShowEmptyOptionCSS) {
		Element moduleElementMock = mock(Element.class);
		NodeList nodeListMock = mock(NodeList.class);
		when(nodeListMock.getLength()).thenReturn(1);
		Element nodeElementMock = mock(Element.class);
		when(nodeElementMock.hasAttribute(EmpiriaTagConstants.ATTR_TYPE)).thenReturn(true);
		when(nodeElementMock.getAttribute(EmpiriaTagConstants.ATTR_TYPE)).thenReturn(GapType.INLINE_CHOICE.getName());

		when(nodeListMock.item(0)).thenReturn(nodeElementMock);
		when(moduleElementMock.getElementsByTagName(EmpiriaTagConstants.NAME_GAP)).thenReturn(nodeListMock);
		NodeList optionNodes = mock(NodeList.class);
		when(optionNodes.getLength()).thenReturn(0);
		when(nodeElementMock.getElementsByTagName(EmpiriaTagConstants.NAME_INLINE_CHOICE)).thenReturn(optionNodes);

		ModuleSocket moduleSocketMock = mock(ModuleSocket.class);
		HashMap<String, String> stylesMap = new HashMap<String, String>();
		
		// EMPIRIA_MATH_INLINECHOICE_EMPTY_OPTION is set as default to "hide"
		if (defineShowEmptyOptionCSS) {
			stylesMap.put(EMPIRIA_MATH_INLINECHOICE_EMPTY_OPTION, VALUE_SHOW);
		}
		when(moduleSocketMock.getStyles(moduleElementMock)).thenReturn(stylesMap);
		
		return spy(new MathModuleHelperMock(moduleElementMock, moduleSocketMock, mock(Response.class), mock(IModule.class)));
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
