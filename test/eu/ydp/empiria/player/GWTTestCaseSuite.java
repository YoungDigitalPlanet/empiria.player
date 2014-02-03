package eu.ydp.empiria.player;

import java.util.Collection;
import java.util.Set;

import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.AllTests;
import org.reflections.Reflections;
import org.reflections.Store;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.google.common.base.Predicate;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.junit.tools.GWTTestSuite;

@RunWith(AllTests.class)
@SuppressWarnings("PMD")
public class GWTTestCaseSuite extends GWTTestSuite {

	private final static Predicate<Class<?>> unsupportedClazzFilter = new UnsupportedTestClassForSuiteFilter();

	/**
	 * create suite for junit
	 * 
	 * @return
	 */
	public static TestSuite suite() {
		TestSuite suite = new TestSuite("GWT tests");
		for (Class<? extends GWTTestCase> test : getTestClasses()) {
			suite.addTestSuite(test);
		}
		return suite;
	}

	public static Set<Class<? extends GWTTestCase>> getTestClasses() {
		return Sets.filter(getAllTestClasses(), unsupportedClazzFilter);
	}

	public static Set<Class<? extends GWTTestCase>> getAllTestClasses() {
		Reflections reflections = createReflectionsForGwtTestCase();
		Collection<String> classNames = getGwtTestCaseClassNames(reflections);
		return convertClassNamesToClass(classNames);
	}

	private static Reflections createReflectionsForGwtTestCase() {
		ConfigurationBuilder inputFilterByName = new ConfigurationBuilder();
		inputFilterByName.setUrls(ClasspathHelper.forPackage("eu.ydp.empiria"));
		inputFilterByName.setScanners(new GwtTestCaseScanner(), new SubTypesScanner());
		return new Reflections(inputFilterByName);
	}

	private static Collection<String> getGwtTestCaseClassNames(final Reflections reflections) {
		Store store = reflections.getStore();
		Multimap<String, String> multimap = store.get(GwtTestCaseScanner.class);
		return multimap.values();
	}

	private static Set<Class<? extends GWTTestCase>> convertClassNamesToClass(final Collection<String> classNames) {
		Set<Class<? extends GWTTestCase>> allGwtTestCaseClasses = Sets.newHashSet();
		for (String className : classNames) {
			try {
				Class<? extends GWTTestCase> clazz = (Class<? extends GWTTestCase>) GWTTestCaseSuite.class.getClassLoader().loadClass(className);
				allGwtTestCaseClasses.add(clazz);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return allGwtTestCaseClasses;
	}
}
