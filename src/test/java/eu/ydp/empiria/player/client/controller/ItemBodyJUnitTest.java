package eu.ydp.empiria.player.client.controller;

import eu.ydp.empiria.player.client.controller.body.ModuleHandlerManager;
import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class ItemBodyJUnitTest {

    @InjectMocks
    private ItemBody itemBody;
    @Mock
    private DisplayContentOptions options;
    @Mock
    private ModuleSocket moduleSocket;
    @Mock
    private ModuleHandlerManager moduleHandlerManager;
    @Mock
    private ModulesRegistrySocket modulesRegistrySocket;
    @Mock
    private ModulesStateLoader modulesStateLoader;

    @Before
    public void prepare() {
        itemBody.modules = new ArrayList<IModule>();

        IModule testNonInteractionModule = mock(IModule.class);
        itemBody.modules.add(testNonInteractionModule);

        IModule testNonInteractionModule2 = mock(IModule.class);
        itemBody.modules.add(testNonInteractionModule2);
    }

    @Test
    @SuppressWarnings("PMD")
    public void hasInteractiveModulesNullGivenExpectsFalse() {
        itemBody.modules = null;

        assertThat(itemBody.hasInteractiveModules(), equalTo(false));
    }

    @Test
    public void hasInteractiveModulesBasicModulesGivenExpectsFalse() {
        assertThat(itemBody.hasInteractiveModules(), equalTo(false));
    }

    @Test
    public void hasInteractiveModulesInteractionModuleGivenExpectsTrue() {
        IInteractionModule testInteractionModule = mock(IInteractionModule.class);
        itemBody.modules.add(testInteractionModule);

        assertThat(itemBody.hasInteractiveModules(), equalTo(true));
    }
}
