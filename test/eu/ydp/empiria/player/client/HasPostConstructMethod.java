package eu.ydp.empiria.player.client;

import java.lang.reflect.Method;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.google.inject.TypeLiteral;
import com.google.inject.matcher.AbstractMatcher;

public class HasPostConstructMethod extends AbstractMatcher<TypeLiteral<?>> {

	private final Set<Class<?>> classWithDisabledPostConstruct;

	public HasPostConstructMethod(Set<Class<?>> classWithDisabledPostConstruct) {
		this.classWithDisabledPostConstruct = classWithDisabledPostConstruct;
	}

	@Override
	public boolean matches(TypeLiteral<?> tpe) {
		try {
			if (!classWithDisabledPostConstruct.contains(tpe.getRawType())) {
				Method[] methods = tpe.getRawType().getMethods();
				for (Method method : methods) {
					if (method.isAnnotationPresent(PostConstruct.class)) {
						return true;
					}
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}

	}
}