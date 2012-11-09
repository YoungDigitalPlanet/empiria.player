package eu.ydp.empiria.player.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.inject.Guice;
import com.google.inject.Module;


@SuppressWarnings("PMD")
public class AbstractTestBaseWithoutAutoInjectorInit extends AbstractTestBase {

	@Override
	public void setUp() {

	}

	public final void setUp(Class<?>[] ignoreClasses, Module module) {
		setUp(ignoreClasses, new Module[] { module });
	}

	public final void setUp(Class<?>[] ignoreClasses, Module... modules) {
		List<Module> moduleList = new ArrayList<Module>();
		moduleList.addAll(Arrays.asList(modules));
		moduleList.add(new TestGuiceModule(ignoreClasses, new Class<?>[0], new Class<?>[0]));
		injector = Guice.createInjector(moduleList);
	}
}
