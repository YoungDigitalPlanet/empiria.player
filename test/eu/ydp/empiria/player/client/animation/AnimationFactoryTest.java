package eu.ydp.empiria.player.client.animation;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.inject.Provider;

import eu.ydp.empiria.player.client.animation.css.CssAnimation;
import eu.ydp.empiria.player.client.animation.holder.AnimationHolder;
import eu.ydp.empiria.player.client.animation.js.JsAnimation;

@RunWith(MockitoJUnitRunner.class)
public class AnimationFactoryTest {
	@Mock private Provider<GenericAnimation> baseAnimationProvider;
	@Mock private Provider<CssAnimation> cssAnimationProvider;
	@Mock private CssAnimationSupportAnalizer cssAnimationSupportAnalizer;
	@Mock private Provider<JsAnimation> jsAnimationProvider;
	@InjectMocks private AnimationFactory instance;

	@Mock private AnimationConfig animationConfig;
	@Mock private AnimationHolder animationHolder;
	@Mock private GenericAnimation genericAnimation;

	@Before
	public void before() {
		doReturn(genericAnimation).when(baseAnimationProvider).get();
	}

	@Test
	public void getAnimationWithCss() throws Exception {
		doReturn(true).when(cssAnimationSupportAnalizer).isCssAnimationSupported();
		Animation animation = instance.getAnimation(animationConfig, animationHolder);
		verify(cssAnimationProvider).get();
		verify(jsAnimationProvider,never()).get();
		verify(baseAnimationProvider).get();
		verify(genericAnimation).init(any(AnimationWithRuntimeConfig.class), eq(animationConfig), eq(animationHolder));
		assertNotNull(animation);
	}

	@Test
	public void getAnimationWithJs() throws Exception {
		doReturn(false).when(cssAnimationSupportAnalizer).isCssAnimationSupported();
		Animation animation = instance.getAnimation(animationConfig, animationHolder);
		verify(cssAnimationProvider,never()).get();
		verify(jsAnimationProvider).get();
		verify(baseAnimationProvider).get();
		verify(genericAnimation).init(any(AnimationWithRuntimeConfig.class), eq(animationConfig), eq(animationHolder));
		assertNotNull(animation);
	}

}
