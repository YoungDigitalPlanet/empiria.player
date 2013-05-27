package eu.ydp.empiria.player;

import java.util.Collection;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.reflections.Reflections;
import org.reflections.Store;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.junit.tools.GWTTestSuite;

@SuppressWarnings("PMD")
public class GWTTestCaseSuite extends GWTTestSuite {

	public static Test suite() {
		TestSuite suite = new TestSuite("GWT tests");
		for (Class<? extends GWTTestCase> test : getAllTestsClass()) {
			suite.addTestSuite(test);
		}
		return suite;
	}

	private static Set<Class<? extends GWTTestCase>> getAllTestsClass() {
		Reflections reflections = createReflectionsForGwtTestCase();
		Collection<String> classNames = getGwtTestCaseClassNames(reflections);
		return convertClassNamesToClass(classNames);
	}

	private static Set<Class<? extends GWTTestCase>> convertClassNamesToClass( Collection<String> multimap) {
		Set<Class<? extends GWTTestCase>> allGwtTestCaseClasses = Sets.newHashSet();
		for (String className : multimap) {
			try {
				Class<? extends GWTTestCase> clazz = (Class<? extends GWTTestCase>) GWTTestCaseSuite.class.getClassLoader().loadClass(className);
				allGwtTestCaseClasses.add(clazz);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return allGwtTestCaseClasses;
	}

	private static Collection<String> getGwtTestCaseClassNames(Reflections reflections) {
		Store store = reflections.getStore();
		Multimap<String, String> multimap = store.get(GwtTestCaseScanner.class);
		return multimap.values();
	}

	private static Reflections createReflectionsForGwtTestCase() {
		ConfigurationBuilder inputFilterByName = new ConfigurationBuilder();
		return new Reflections(inputFilterByName.setUrls(ClasspathHelper.forPackage("eu.ydp.empiria")).setScanners(new GwtTestCaseScanner(),
				new SubTypesScanner()));
	}
}
