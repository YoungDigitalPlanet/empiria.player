package eu.ydp.empiria.player;

import com.google.gwt.junit.client.GWTTestCase;
import org.reflections.scanners.AbstractScanner;

import java.lang.reflect.Modifier;

public class GwtTestCaseScanner extends AbstractScanner {

    @Override
    public void scan(Object classMetadata) {
        String className = getMetadataAdapter().getClassName(classMetadata);
        String superclassName = getMetadataAdapter().getSuperclassName(classMetadata);

        try {
            Class<?> clazz = loadClass(className);
            if (isGwtTestCase(clazz) && !isAbstract(clazz)) {
                if (acceptResult(superclassName)) {
                    getStore().put(superclassName, className);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Class<?> loadClass(String className) throws ClassNotFoundException {
        return this.getClass().getClassLoader().loadClass(className);
    }

    private boolean isAbstract(Class<?> clazz) {
        int modifiers = clazz.getModifiers();
        return Modifier.isAbstract(modifiers);
    }

    private boolean isGwtTestCase(Class<?> clazz) {
        return GWTTestCase.class.isAssignableFrom(clazz);
    }
}
