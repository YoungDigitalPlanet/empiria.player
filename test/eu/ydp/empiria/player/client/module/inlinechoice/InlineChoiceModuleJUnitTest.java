package eu.ydp.empiria.player.client.module.inlinechoice;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.GWTMockUtilities;

import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.module.IUniqueModule;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;

public class InlineChoiceModuleJUnitTest {
	
	@Test
	public void shouldSetParentInlineModuleCorrectlyForDefaultController() {
		IUniqueModule inlineChoice = new InlineChoiceModuleMock(InlineChoiceModuleMock.DEFAULT_CONTROLLER);
		((InlineChoiceModule) inlineChoice).initModule();
		IUniqueModule parentModule = ((InlineChoiceModule) inlineChoice).getController().getParentInlineModule();
		
		assertThat(parentModule, is(inlineChoice));
	}
	
	@Test
	public void shouldSetParentInlineModuleCorrectlyForPopupController() {
		IUniqueModule inlineChoice = new InlineChoiceModuleMock(InlineChoiceModuleMock.POPUP_CONTROLLER);
		((InlineChoiceModule) inlineChoice).initModule();
		IUniqueModule parentModule = ((InlineChoiceModule) inlineChoice).getController().getParentInlineModule();
		
		assertThat(parentModule, is(inlineChoice));
	}
	
	@BeforeClass
	public static void prepareTestEnviroment() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void restoreEnviroment() {
		GWTMockUtilities.restore();
	}

	private class InlineChoiceModuleMock extends InlineChoiceModule {
		
		public static final String DEFAULT_CONTROLLER = "default";
		public static final String POPUP_CONTROLLER = "popup";
		
		protected String controllerType;
		
		public InlineChoiceModuleMock(String controllerType) {
			this.controllerType = controllerType;
		}
		
		@Override
		protected EventsBus getEventsBus() {
			return mock(EventsBus.class);
		}
		
		@Override
		protected Map<String, String> getStyles() {
			return null;
		}
		
		@Override
		protected void setStyles() {
			if (controllerType.equals(DEFAULT_CONTROLLER)) {
				controller = new InlineChoiceDefaultControllerMock();
			} else if (controllerType.equals(POPUP_CONTROLLER)) {
				controller = new InlineChoicePopupControllerMock();
			}
		}
		
	}
	
	private class InlineChoiceDefaultControllerMock extends InlineChoiceDefaultController {
		
		@Override
		protected EventsBus getEventsBus() {
			return mock(EventsBus.class);
		}
		
	}
	
	private class InlineChoicePopupControllerMock extends InlineChoicePopupController {
		
		@Override
		protected EventsBus getEventsBus() {
			return mock(EventsBus.class);
		}
		
	}
	
}
