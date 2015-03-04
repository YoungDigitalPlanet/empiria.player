package eu.ydp.empiria.player;

import com.google.common.base.Predicate;
import java.util.Set;
import junit.framework.*;
import org.junit.runner.RunWith;
import org.junit.runners.AllTests;
import org.mockito.configuration.MockitoConfiguration;
import org.reflections.Reflections;
import org.reflections.util.*;
import spock.lang.Specification;

@RunWith(AllTests.class)
public class SpockTestSuite {

	private final static Predicate<String> onlyClassEndsWithTest = new Predicate<String>() {
		@Override
		public boolean apply(final String name) {
			return name.endsWith("Test.class");
		}
	};

	public static TestSuite suite() {
		MockitoConfiguration.setenableClassCache(false);
		TestSuite junitSuite = new TestSuite();

		Iterable<Class<? extends Specification>> filteredClasses = getTestClasses();
		for (Class<? extends Specification> clazz : filteredClasses) {
			JUnit4TestAdapter adapter = new JUnit4TestAdapter(clazz);
			junitSuite.addTest(adapter);
		}
		return junitSuite;
	}

	private static Set<Class<? extends Specification>> getTestClasses() {
		Reflections reflections = createSpockTestReflections();
		return reflections.getSubTypesOf(Specification.class);
	}

	private static Reflections createSpockTestReflections() {
		ConfigurationBuilder inputFilterByName = new ConfigurationBuilder().filterInputsBy(new FilterBuilder().add(onlyClassEndsWithTest));
		inputFilterByName.setUrls(ClasspathHelper.forPackage("eu.ydp.empiria"));
		return inputFilterByName.build();
	}
}
