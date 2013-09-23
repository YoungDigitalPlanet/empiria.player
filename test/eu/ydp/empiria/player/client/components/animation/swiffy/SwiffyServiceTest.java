package eu.ydp.empiria.player.client.components.animation.swiffy;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.inject.Provider;

@RunWith(MockitoJUnitRunner.class)
public class SwiffyServiceTest {
	@Mock private SwiffyRuntimeLoader runtimeLoader;
	@Mock private SwiffyObject swiffyObject;
	@Mock private Provider<SwiffyObject> swiffyObjectProvider;
	@InjectMocks private SwiffyService instance;


	@Before
	public void before() {
		doReturn(swiffyObject).when(swiffyObjectProvider).get();
	}

	@Test
	public void getSwiffyObject() throws Exception {
		String animationUrl = "url";
		SwiffyObject swiffyObject = instance.getSwiffyObject("name", animationUrl);
		assertThat(swiffyObject).isNotNull();
		assertThat(swiffyObject).isEqualTo(this.swiffyObject);
		verify(runtimeLoader).loadRuntime();
		verify(swiffyObject).setAnimationUrl(eq(animationUrl));
	}

	@Test
	public void clear() throws Exception {
		String animationUrl = "url";
		String animationName = "name";
		SwiffyObject swiffyObject = instance.getSwiffyObject(animationName, animationUrl);
		instance.clear(animationName);
		instance.clear(animationName);
		verify(swiffyObject).destroy();
	}

}
