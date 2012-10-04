package eu.ydp.empiria.player.client.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.IModule;

public class AssessmentControllerJunitTest extends PageControllerJunitTestBase {
	
	@Test
	public void createAndUseIItemPropertiesTest() {		
		@SuppressWarnings("unchecked")
		List<IModule> modules = prepareMockModulesList(IInteractionModule.class);
		prepareItemControllerMock(modules);
		final AssessmentController assesmentController = 
				mock(AssessmentController.class, Mockito.CALLS_REAL_METHODS);
		assesmentController.pageController = pageController;
		
		IItemProperties itemProperties = assesmentController.createItemProperties();		
		
		assertThat(itemProperties.hasInteractiveModules(), equalTo(true));		
	}
	
}
