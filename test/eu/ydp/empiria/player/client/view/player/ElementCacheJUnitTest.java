package eu.ydp.empiria.player.client.view.player;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class ElementCacheJUnitTest {

	class SimpleCache extends AbstractElementCache<String> {
		@Override
		protected String getElement(Integer index) {
			return null;
		}
	}

	protected SimpleCache cache = null;

	@Before
	public void setUp() {
		cache = spy(new SimpleCache());
		when(cache.getElement(Mockito.anyInt())).then(new Answer<String>() {
			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				return String.valueOf(invocation.getArguments()[0]);
			}
		});
	}

	@Test
	public void cacheEmptyTest() {
		assertTrue("cache is not empty", cache.isEmpty());
		cache.get(0);
		cache.get(1);
		assertFalse("cache is empty", cache.isEmpty());
	}

	@Test
	public void cacheAddTest() {
		assertTrue("wrong return value from cache", cache.get(0).equals("0"));
		assertTrue("wrong return value from cache", cache.get(1).equals("1"));
		assertTrue("wrong return value from cache", cache.get(0).equals("0"));
		Mockito.verify(cache, Mockito.times(2)).getElement(Mockito.anyInt());
	}

	@Test
	public void cacheContainsTest() {
		cache.get(0);
		cache.get(1);
		assertFalse("wrong value in cache", cache.isPresent(20));
		assertTrue("missing value in cache", cache.isPresent(0));
		assertTrue("missing value in cache", cache.isPresent(1));
	}
}
