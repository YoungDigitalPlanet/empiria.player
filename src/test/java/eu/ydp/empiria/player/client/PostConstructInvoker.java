package eu.ydp.empiria.player.client;

import com.google.inject.spi.InjectionListener;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;

public class PostConstructInvoker implements InjectionListener {
    @Override
    public void afterInjection(Object injectee) {
        try {
            Method[] methods = injectee.getClass().getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(PostConstruct.class)) {
                    method.invoke(injectee);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
