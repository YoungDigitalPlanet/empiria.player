package eu.ydp.empiria.player.client.module.containers;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.module.core.flow.IActivity;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.module.core.flow.IResetable;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class ModulesStatesControllerTest {

    private ModulesActivitiesController testObj;
    private List<IModule> modules;

    @Before
    public void setUp() {
        testObj = new ModulesActivitiesController();
    }

    @Test
    public void shouldShowCorrectAnswersInModules() {
        // given
        boolean show = true;

        IModule activityModule1 = mock(IModule.class, withSettings().extraInterfaces(IActivity.class));
        IModule activityModule2 = mock(IModule.class, withSettings().extraInterfaces(IActivity.class));
        IModule notActivityModule = mock(IModule.class);

        modules = Lists.newArrayList(activityModule1, activityModule2);

        // when
        testObj.showCorrectAnswers(modules, show);

        // then
        verify((IActivity) activityModule1).showCorrectAnswers(show);
        verify((IActivity) activityModule2).showCorrectAnswers(show);
        verifyZeroInteractions(notActivityModule);
    }

    @Test
    public void shouldMarkAnswersInModules() {
        // given
        boolean mark = true;

        IModule activityModule1 = mock(IModule.class, withSettings().extraInterfaces(IActivity.class));
        IModule activityModule2 = mock(IModule.class, withSettings().extraInterfaces(IActivity.class));
        IModule notActivityModule = mock(IModule.class);

        modules = Lists.newArrayList(activityModule1, activityModule2);

        // when
        testObj.markAnswers(modules, mark);

        // then
        verify((IActivity) activityModule1).markAnswers(mark);
        verify((IActivity) activityModule2).markAnswers(mark);
        verifyZeroInteractions(notActivityModule);
    }

    @Test
    public void shouldLockModules() {
        // given
        boolean state = true;

        IModule activityModule1 = mock(IModule.class, withSettings().extraInterfaces(IActivity.class));
        IModule activityModule2 = mock(IModule.class, withSettings().extraInterfaces(IActivity.class));
        IModule notActivityModule = mock(IModule.class);

        modules = Lists.newArrayList(activityModule1, activityModule2);

        // when
        testObj.lock(modules, state);

        // then
        verify((IActivity) activityModule1).lock(state);
        verify((IActivity) activityModule2).lock(state);
        verifyZeroInteractions(notActivityModule);
    }

    @Test
    public void shouldResetModules() {
        // given

        IModule resetableModule1 = mock(IModule.class, withSettings().extraInterfaces(IResetable.class));
        IModule resetableModule2 = mock(IModule.class, withSettings().extraInterfaces(IResetable.class));
        IModule notResetableModule = mock(IModule.class);

        modules = Lists.newArrayList(resetableModule1, resetableModule2);

        // when
        testObj.reset(modules);

        // then
        verify((IResetable) resetableModule1).reset();
        verify((IResetable) resetableModule2).reset();
        verifyZeroInteractions(notResetableModule);
    }

}
