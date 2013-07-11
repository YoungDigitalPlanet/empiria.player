package eu.ydp.empiria.player.client.animation.css;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import eu.ydp.empiria.player.client.animation.AnimationConfig;
import eu.ydp.empiria.player.client.util.geom.Size;

@RunWith(MockitoJUnitRunner.class)
public class CssAnimationClassBuilderTest {
	@Mock StyleAppender styleAppender;
	@InjectMocks CssAnimationClassBuilder instance;
	private Size frameSize;
	private String source;
	private AnimationConfig animationConfig;
	private Size imageSize;
	private String keyframesName;
	private String animationStyleName;

	@Before
	public void before() {
		frameSize = new Size(20,30);
		source = "http://dummy/jj.png";
		animationConfig = new AnimationConfig(20, frameSize, source);
		imageSize = new Size(200,30);
		keyframesName = "HTTP___DUMMY_JJ_PNG_keyframes";
		animationStyleName = "HTTP___DUMMY_JJ_PNG";

	}

	@Test
	public void getAnimationCssClassName() throws Exception {
		String animationCssClassName = instance.getAnimationCssClassName(animationConfig, imageSize);
		assertThat(animationCssClassName).isNotNull().isNotEmpty();
		assertThat(animationCssClassName).isEqualTo(animationStyleName);
	}

	@Test
	public void shouldReturnSameAnimationCssClassName() throws Exception {
		String animationCssClassName = instance.getAnimationCssClassName(animationConfig, imageSize);
		assertThat(animationCssClassName).isNotNull().isNotEmpty();
		String secondAnimationCssClassName = instance.getAnimationCssClassName(animationConfig, imageSize);
		assertThat(secondAnimationCssClassName).isNotNull().isNotEmpty();
		assertThat(animationCssClassName).isEqualTo(secondAnimationCssClassName);
		verify(styleAppender,times(4)).appendStyleToDocument(anyString());
	}

	@Test
	public void keyframesAnimationCssClassBody() throws Exception {
		ArgumentCaptor<String> classBodyCaptor = ArgumentCaptor.<String>forClass(String.class);
		String animationCssClassName = instance.getAnimationCssClassName(animationConfig, imageSize);
		verify(styleAppender,times(4)).appendStyleToDocument(classBodyCaptor.capture());

		String webkitKeyframes = "@-webkit-keyframes HTTP___DUMMY_JJ_PNG_keyframes { from {background-position:0px} to {background-position:-200px}}";
		String mozKeyframes ="@-moz-keyframes HTTP___DUMMY_JJ_PNG_keyframes { from {background-position:0px} to {background-position:-200px}}";
		String w3cKeyframes ="@keyframes HTTP___DUMMY_JJ_PNG_keyframes { from {background-position:0px} to {background-position:-200px}}";

		List<String> generatedClass = classBodyCaptor.getAllValues();

		for(int x=0;x<3;++x){
			assertThat(generatedClass.get(x)).contains(keyframesName);
		}
		assertThat(generatedClass.get(3)).contains(animationCssClassName);

		final Set<String> requireKeyframes = Sets.newHashSet(webkitKeyframes.replaceAll(" ", ""),
													   mozKeyframes.replaceAll(" ", ""),
													   w3cKeyframes.replaceAll(" ", ""));

		boolean allKeyframesPresent = Iterables.all(Iterables.limit(generatedClass, 3), new Predicate<String>() {
			@Override
			public boolean apply(String keyframesWithBody) {
				String keyframesWithoutSpaces = keyframesWithBody.replaceAll(" ", "");
				return requireKeyframes.contains(keyframesWithoutSpaces);
			}
		});

		assertThat(allKeyframesPresent).isTrue();
	}

	@Test
	public void animationClassBody(){
		ArgumentCaptor<String> classBodyCaptor = ArgumentCaptor.<String>forClass(String.class);
		String animationCssClassName = instance.getAnimationCssClassName(animationConfig, imageSize);
		verify(styleAppender,times(4)).appendStyleToDocument(classBodyCaptor.capture());
		List<String> generatedClass = classBodyCaptor.getAllValues();
		String animationClassBody = generatedClass.get(3);
		String requiredAimationClassBody = " .HTTP___DUMMY_JJ_PNG {  -webkit-animation: HTTP___DUMMY_JJ_PNG_keyframes 500ms steps(10, end); -moz-animation: HTTP___DUMMY_JJ_PNG_keyframes 500ms steps(10, end); animation: HTTP___DUMMY_JJ_PNG_keyframes 500ms steps(10, end); background-image: url("+source+")} ";
		assertThat(animationClassBody).contains(animationCssClassName);
		assertThat(requiredAimationClassBody.replaceAll(" ", "")).isEqualTo(animationClassBody.replaceAll(" ", ""));
	}
}
