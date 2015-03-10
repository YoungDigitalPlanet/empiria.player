package eu.ydp.empiria.player;

import com.google.common.base.Predicate;
import junit.framework.JUnit4TestAdapter;
import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.AllTests;
import org.mockito.configuration.MockitoConfiguration;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import spock.lang.Specification;

import java.util.Set;

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
		TestSuite spockSuite = new TestSuite();

		Iterable<Class<? extends Specification>> filteredClasses = getTestClasses();
		for (Class<? extends Specification> clazz : filteredClasses) {
			JUnit4TestAdapter adapter = new JUnit4TestAdapter(clazz);
			spockSuite.addTest(adapter);
		}
		return spockSuite;
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
