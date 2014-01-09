package eu.ydp.empiria.player.client.module.containers;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.module.IActivity;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.IResetable;

public class ModulesStatesControllerJUnitTest {

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

		IModule module1 = mock(IModule.class, withSettings().extraInterfaces(IActivity.class));
		IModule module2 = mock(IModule.class, withSettings().extraInterfaces(IActivity.class));

		modules = Lists.newArrayList(module1, module2);

		// when
		testObj.showCorrectAnswers(modules, show);

		// then
		verify((IActivity) module1).showCorrectAnswers(show);
		verify((IActivity) module2).showCorrectAnswers(show);
	}

	@Test
	public void shouldMarkAnswersInModules() {
		// given
		boolean mark = true;

		IModule module1 = mock(IModule.class, withSettings().extraInterfaces(IActivity.class));
		IModule module2 = mock(IModule.class, withSettings().extraInterfaces(IActivity.class));

		modules = Lists.newArrayList(module1, module2);

		// when
		testObj.markAnswers(modules, mark);

		// then
		verify((IActivity) module1).markAnswers(mark);
		verify((IActivity) module2).markAnswers(mark);
	}

	@Test
	public void shouldLockModules() {
		// given
		boolean state = true;

		IModule module1 = mock(IModule.class, withSettings().extraInterfaces(IActivity.class));
		IModule module2 = mock(IModule.class, withSettings().extraInterfaces(IActivity.class));

		modules = Lists.newArrayList(module1, module2);

		// when
		testObj.lock(modules, state);

		// then
		verify((IActivity) module1).lock(state);
		verify((IActivity) module2).lock(state);
	}

	@Test
	public void shouldResetModules() {
		// given

		IModule module1 = mock(IModule.class, withSettings().extraInterfaces(IResetable.class));
		IModule module2 = mock(IModule.class, withSettings().extraInterfaces(IResetable.class));

		modules = Lists.newArrayList(module1, module2);

		// when
		testObj.reset(modules);

		// then
		verify((IResetable) module1).reset();
		verify((IResetable) module2).reset();
	}

}
