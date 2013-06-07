package eu.ydp.empiria.player;

import org.junit.runner.RunWith;

import com.google.common.base.Predicate;

import eu.ydp.gwtutil.junit.runners.ExMockRunner;

public class UnsupportedTestClassForSuiteFilter implements Predicate<Class<?>> {

	@Override
	public boolean apply(final Class<?> clazz) {
		return !(clazz.isAnnotationPresent(RunOutsideTestSuite.class) || isRunByExMockRunner(clazz));
	}

	public boolean isRunByExMockRunner(Class<?> clazz){
		if(clazz.isAnnotationPresent(RunWith.class)){
			return ExMockRunner.class.isAssignableFrom(clazz.getAnnotation(RunWith.class).value());
		}
		return false;
	}

}
