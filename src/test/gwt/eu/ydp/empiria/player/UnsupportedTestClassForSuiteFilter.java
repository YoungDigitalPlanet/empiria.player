package eu.ydp.empiria.player;

import com.google.common.base.Predicate;

public class UnsupportedTestClassForSuiteFilter implements Predicate<Class<?>> {

    @Override
    public boolean apply(final Class<?> clazz) {
        return !(clazz.isAnnotationPresent(RunOutsideTestSuite.class));
    }

}
