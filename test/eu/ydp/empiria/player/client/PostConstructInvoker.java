package eu.ydp.empiria.player.client;

import java.lang.reflect.Method;

import javax.annotation.PostConstruct;

import com.google.inject.spi.InjectionListener;

public class PostConstructInvoker implements InjectionListener {
	@Override
	public void afterInjection(Object injectee) {
		try {
			Method[] methods = injectee.getClass().getMethods();
			for(Method method:methods){
				if (method.isAnnotationPresent(PostConstruct.class)) {
					method.invoke(injectee);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}