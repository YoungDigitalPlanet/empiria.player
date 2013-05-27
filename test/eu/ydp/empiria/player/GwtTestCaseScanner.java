package eu.ydp.empiria.player;

import org.reflections.scanners.AbstractScanner;

import com.google.gwt.junit.client.GWTTestCase;

public class GwtTestCaseScanner extends AbstractScanner {

	@Override
	public boolean acceptsInput(String file) {
		return file.endsWith("Test.class");
	}

	@Override
	public void scan(Object clazz) {
		String className = getMetadataAdapter().getClassName(clazz);
		String superclassName = getMetadataAdapter().getSuperclassName(clazz);
		Class<?> loadClass;
		try {
			loadClass = this.getClass().getClassLoader().loadClass(className);
			if (GWTTestCase.class.isAssignableFrom(loadClass)) {
				if (acceptResult(superclassName)) {
					getStore().put(superclassName, className);
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
