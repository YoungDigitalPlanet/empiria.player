package eu.ydp.empiria.player.client;

import java.lang.reflect.Method;
import java.util.Set;

import junit.framework.JUnit4TestAdapter;
import junit.framework.TestSuite;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.AllTests;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.Scanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import eu.ydp.gwtutil.junit.runners.ExMockRunner;

@RunWith(AllTests.class)
@SuppressWarnings("PMD")
public class JUnitTTestSuite {

	private final static Predicate<Class<?>> filterUnsupportedClazz = new Predicate<Class<?>>() {
		@Override
		public boolean apply(Class<?> clazz) {
			if (clazz.isAnnotationPresent(RunWith.class)) {
				if (clazz.getAnnotation(RunWith.class).value().isAssignableFrom(ExMockRunner.class)) {
					return false;
				}
			}
			return true;
		}
	};


	private final static Predicate<String> onlyMethodWithTestAnnotation = new Predicate<String>() {
		@Override
		public boolean apply(String name) {
			return name.contains("org.junit.Test");
		}
	};

	private final static Predicate<String> onlyClassEndsWithTest = new Predicate<String>() {
		@Override
		public boolean apply(String name) {
			return name.endsWith("Test.class");
		}
	};
	public static TestSuite suite() {
		TestSuite junitSuite = new TestSuite();
		Iterable<Class<?>> filteredClasses = Iterables.filter(getAllTestsClass(), filterUnsupportedClazz);
		for (Class<?> clazz :  filteredClasses) {
			JUnit4TestAdapter adapter = new JUnit4TestAdapter(clazz);
			junitSuite.addTest(adapter);
		}

		return junitSuite;
	}

	public static Set<Class<?>> getAllTestsClass() {

		ConfigurationBuilder inputFilterByName = new ConfigurationBuilder().filterInputsBy(new FilterBuilder().add(onlyClassEndsWithTest));
		Scanner methodFilterByTestAnnotation = new MethodAnnotationsScanner().filterResultsBy(onlyMethodWithTestAnnotation);

		Reflections reflections = new Reflections(inputFilterByName.
				 setUrls(ClasspathHelper.forPackage("eu.ydp.empiria"))
				.setScanners(new SubTypesScanner(), methodFilterByTestAnnotation));

		Set<Class<?>> classes = Sets.newHashSet();
		Set<Method> methodsAnnotatedWith = reflections.getMethodsAnnotatedWith(Test.class);
		for (Method method : methodsAnnotatedWith) {
			classes.add(method.getDeclaringClass());
		}
		return classes;

	}
}
