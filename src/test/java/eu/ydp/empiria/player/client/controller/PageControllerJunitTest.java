package eu.ydp.empiria.player.client.controller;

import eu.ydp.empiria.player.client.module.core.base.IInteractionModule;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PageControllerJunitTest extends PageControllerJunitTestBase {

    @Test
    public void hasInteractiveModulesNullGivenExpectsFalse() {
        prepareItemControllerMock(null);

        boolean checkHasInteractiveModules = pageController.hasInteractiveModules();

        assertThat(checkHasInteractiveModules, equalTo(false));
        checkInvocationTimes();
    }

    @Test
    public void hasInteractiveModulesExpectsFalse() {
        @SuppressWarnings("unchecked")
        List<IModule> modules = prepareMockModulesList(IModule.class, IModule.class);
        prepareItemControllerMock(modules);

        boolean checkHasInteractiveModules = pageController.hasInteractiveModules();

        assertThat(checkHasInteractiveModules, equalTo(false));
        checkInvocationTimes();
    }

    @Test
    public void hasInteractiveModulesExpectsTrue() {
        @SuppressWarnings("unchecked")
        List<IModule> modules = prepareMockModulesList(IInteractionModule.class, IModule.class, IModule.class);
        prepareItemControllerMock(modules);

        boolean checkHasInteractiveModules = pageController.hasInteractiveModules();

        assertThat(checkHasInteractiveModules, equalTo(true));
        checkInvocationTimes();
    }

    private void checkInvocationTimes() {
        Mockito.verify(itemController, Mockito.times(1)).hasInteractiveModules();
        Mockito.verify(itemBody, Mockito.times(1)).hasInteractiveModules();
        Mockito.verify(item, Mockito.times(1)).hasInteractiveModules();
    }

}
