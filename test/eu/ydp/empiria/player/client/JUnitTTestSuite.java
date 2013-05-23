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
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;

import eu.ydp.gwtutil.junit.runners.ExMockRunner;

@RunWith(AllTests.class)
public class JUnitTTestSuite {
	public static TestSuite suite() {
		TestSuite junitSuite = new TestSuite();

		for (Class<?> clazz : getAllTestsClass()) {
			if (clazz.isAnnotationPresent(RunWith.class)) {
				if (clazz.getAnnotation(RunWith.class).value().isAssignableFrom(ExMockRunner.class)) {
					continue;
				}
			}
			JUnit4TestAdapter adapter = new JUnit4TestAdapter(clazz);
			junitSuite.addTest(adapter);
		}

		return junitSuite;
	}

	public static Set<Class<?>> getAllTestsClass() {

		Reflections reflections = new Reflections(new ConfigurationBuilder().filterInputsBy(new FilterBuilder().add(new Predicate<String>() {
			@Override
			public boolean apply(String name) {
				return name.endsWith("Test.class");
			}
		})).setUrls(ClasspathHelper.forPackage("eu.ydp.empiria"))
				.setScanners(new SubTypesScanner(), new MethodAnnotationsScanner().filterResultsBy(new Predicate<String>() {
					@Override
					public boolean apply(String name) {
						return name.contains("org.junit.Test");
					}
				})));

		Set<Class<?>> classes = Sets.newHashSet();
		Set<Method> methodsAnnotatedWith = reflections.getMethodsAnnotatedWith(Test.class);
		for (Method method : methodsAnnotatedWith) {
			classes.add(method.getDeclaringClass());
		}
		return classes;

	}
}
