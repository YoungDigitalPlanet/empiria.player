package eu.ydp.empiria.player.client.rebind;

import com.google.gwt.core.client.GWT;

import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;

@SuppressWarnings("PMD")
public class PostConstructTests extends AbstractEmpiriaPlayerGWTTestCase {

	public void testSimpleInject() {
		TestGinjector injector = GWT.create(TestGinjector.class);
		SimpleInject simpleInject = injector.getSimpleInject();
		assertTrue(simpleInject.getInject().isPostConstructFire());
	}

	public void testProviderInject() {
		TestGinjector injector = GWT.create(TestGinjector.class);
		ProviderInject inject = injector.getProviderInject();
		assertTrue(inject.getInject().isPostConstructFire());
	}

	public void testFactoryInject() {
		TestGinjector injector = GWT.create(TestGinjector.class);
		SimpleFactory inject = injector.getSimpleFactory();
		assertTrue(inject.getSimpleInject().getInject().isPostConstructFire());
	}

	public void testFactoryAssistedInjectInject() {
		TestGinjector injector = GWT.create(TestGinjector.class);
		SimpleFactory inject = injector.getSimpleFactory();
		assertTrue(inject.getSimpleInject("module").getInject().isPostConstructFire());
	}

	public void testFactoryByInterfaceInject() {
		TestGinjector injector = GWT.create(TestGinjector.class);
		InterfaceFactory inject = injector.getInterfaceFactory();
		assertTrue(inject.getSimpleInject().isPostConstructFire());
	}

	public void testFactoryByInterfaceWithAssistedInject() {
		TestGinjector injector = GWT.create(TestGinjector.class);
		InterfaceFactory inject = injector.getInterfaceFactory();
		assertTrue(inject.getSimpleInject("sss").isPostConstructFire());
	}
}
