package eu.ydp.empiria.player;

import java.lang.reflect.Method;
import java.util.Set;

import junit.framework.JUnit4TestAdapter;
import junit.framework.TestSuite;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.AllTests;
import org.mockito.configuration.MockitoConfiguration;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.Scanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;

@RunWith(AllTests.class)
@SuppressWarnings("PMD")
public class JUnitTestSuite {

	private final static Predicate<Class<?>> unsupportedClazzFilter = new UnsupportedTestClassForSuiteFilter();

	private final static Predicate<String> onlyMethodWithTestAnnotation = new Predicate<String>() {
		@Override
		public boolean apply(final String name) {
			return name.contains("org.junit.Test");
		}
	};

	private final static Predicate<String> onlyClassEndsWithTest = new Predicate<String>() {
		@Override
		public boolean apply(final String name) {
			return name.endsWith("Test.class");
		}
	};

	/**
	 * TEST suite
	 * 
	 * @return
	 */
	public static TestSuite suite() {
		MockitoConfiguration.setenableClassCache(false);
		TestSuite junitSuite = new TestSuite();

		Iterable<Class<?>> filteredClasses = getTestClasses();
		for (Class<?> clazz : filteredClasses) {
			JUnit4TestAdapter adapter = new JUnit4TestAdapter(clazz);
			junitSuite.addTest(adapter);
		}
		return junitSuite;
	}

	public static Set<Class<?>> getTestClasses() {
		return Sets.filter(getAllTestClasses(), unsupportedClazzFilter);
	}

	public static Set<Class<?>> getAllTestClasses() {

		ConfigurationBuilder inputFilterByName = new ConfigurationBuilder().filterInputsBy(new FilterBuilder().add(onlyClassEndsWithTest));
		Scanner methodFilterByTestAnnotation = new MethodAnnotationsScanner().filterResultsBy(onlyMethodWithTestAnnotation);
		inputFilterByName.setUrls(ClasspathHelper.forPackage("eu.ydp.empiria"));
		inputFilterByName.setScanners(new SubTypesScanner(), methodFilterByTestAnnotation);
		Reflections reflections = new Reflections(inputFilterByName);

		Set<Class<?>> classes = Sets.newHashSet();
		Set<Method> methodsAnnotatedWith = reflections.getMethodsAnnotatedWith(Test.class);
		for (Method method : methodsAnnotatedWith) {
			classes.add(method.getDeclaringClass());
		}
		return classes;

	}
}
