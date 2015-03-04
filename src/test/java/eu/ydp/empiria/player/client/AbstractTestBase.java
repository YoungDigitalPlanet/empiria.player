package eu.ydp.empiria.player.client;

import org.junit.Before;

import com.google.inject.Guice;
import com.google.inject.Injector;

@SuppressWarnings("PMD")
public abstract class AbstractTestBase {

	protected Injector injector;

	@Before
	public void setUp() {
		injector = Guice.createInjector(new TestGuiceModule());
	}

	public final void setUp(Class<?>... ignoreClasses) {
		injector = Guice.createInjector(new TestGuiceModule(ignoreClasses, new Class<?>[0], new Class<?>[0]));
	}

	public final void setUp(Class<?>[] ignoreClasses, Class<?>[] classToMock, Class<?>[] classToSpy) {
		injector = Guice.createInjector(new TestGuiceModule(ignoreClasses, classToMock, classToSpy));
	}
}
