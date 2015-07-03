package eu.ydp.empiria.player.client.inject;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.AbstractTestBase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

@SuppressWarnings("PMD")
public class InstanceJUnitTest extends AbstractTestBase {

    private static class InstanceToInject {

    }

    private static class ObjectWithInstance {
        @Inject
        Instance<InstanceToInject> instance;

        public InstanceToInject getInstance() {
            return instance.get();
        }
    }

    ObjectWithInstance instance;

    @Before
    public void before() {
        instance = injector.getInstance(ObjectWithInstance.class);
    }

    @Test
    public void testGet() {
        InstanceToInject injectInstance = instance.getInstance();
        InstanceToInject injectInstance2 = instance.getInstance();
        assertTrue(injectInstance == injectInstance2);
    }

}
