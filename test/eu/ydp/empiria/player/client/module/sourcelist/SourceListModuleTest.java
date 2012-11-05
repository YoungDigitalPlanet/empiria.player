package eu.ydp.empiria.player.client.module.sourcelist;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import eu.ydp.empiria.player.client.AbstractTestBase;

@SuppressWarnings("PMD")
public class SourceListModuleTest extends AbstractTestBase {

	private SourceListModule instance;
	@Before
	public void before(){
		instance = injector.getInstance(SourceListModule.class);
	}

	@Test
	public void testFactoryMethod(){
		assertNotNull(instance.getNewInstance());
	}

}
