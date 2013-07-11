package eu.ydp.empiria.player.client.animation.css;

import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.animation.AnimationConfig;
import eu.ydp.empiria.player.client.util.geom.Size;

public class CssAnimationClassBuilder {
	private @Inject @Nonnull StyleAppender styleInjector;
	private final String webkitPrefix = "-webkit-";
	private final String mozPrefix = "-moz-";
	private final String w3cPrefix = "";

	List<String> prefixes = Lists.newArrayList(webkitPrefix,mozPrefix, w3cPrefix);
	private final static String ANIMATE_KEYFRAMES_TEMPLATE = "@$prefixkeyframes $name { from {background-position:$from} to {background-position:$to} }";
	private final static String ANIMATE_CLASS_TEMPLATE = " .$name { $styleProperty background-image: url($backgroundImage) } ";
	private final static String ANIMATE_PROPERTY = " $prefixanimation: $keyframesName $animationTimems steps($stepsCount, end);";
	private final Set<String> generatedClassNames = Sets.newHashSet();

	private String getRawStyleName(AnimationConfig animationConfig) {
		String imgSource = animationConfig.getSource();
		return imgSource.toUpperCase().replaceAll("[\\/.: ]", "_");
	}

	private void appendStyleToDocument(String template) {
		styleInjector.appendStyleToDocument(template);
	}

	private String getAnimationKeyframesName(AnimationConfig animationConfig, Size imgSize){
		String animationStyleName = getRawStyleName(animationConfig);
		animationStyleName += "_keyframes";
		for(String prefix : prefixes){
			String keyframes = createSingleKeyframesProperty(imgSize, animationStyleName, prefix);
			appendStyleToDocument(keyframes);
		}
		return animationStyleName;
	}

	private String createSingleKeyframesProperty(Size imgSize, String animationStyleName, String prefix) {
		String keyframes = ANIMATE_KEYFRAMES_TEMPLATE
								.replaceAll("\\$prefix",prefix)
								.replaceAll("\\$name", animationStyleName)
								.replaceAll("\\$from", "0px")
								.replaceAll("\\$to", -imgSize.getWidth()+"px");
		return keyframes;
	}

	public String getAnimationCssClassName(AnimationConfig animationConfig, Size imgSize){
		String animationStyleName = getRawStyleName(animationConfig);
		if(generatedClassNames.contains(animationStyleName)){
			return animationStyleName;
		}
		createAnimationCss(animationConfig, imgSize, animationStyleName);
		generatedClassNames.add(animationStyleName);
		return animationStyleName;
	}

	private void createAnimationCss(AnimationConfig animationConfig, Size imgSize, String animationStyleName) {
		String keyframesName = getAnimationKeyframesName(animationConfig, imgSize);
		StringBuilder animationClassBody = new StringBuilder();
		int framesCount = imgSize.getWidth() / animationConfig.getFrameSize().getWidth();
		for (String prefix : prefixes) {
			String styleProperty = generateCssAnimationPropertyValue(animationConfig, keyframesName, framesCount, prefix);
			animationClassBody.append(styleProperty);
		}

		String animationCssClass = generateAnimationStyleBodyWithProperties(animationConfig, animationStyleName, animationClassBody);
		appendStyleToDocument(animationCssClass);
	}

	private String generateCssAnimationPropertyValue(AnimationConfig animationConfig, String keyframesName, int framesCount, String prefix) {
		String styleProperty = ANIMATE_PROPERTY
									.replaceAll("\\$prefix", prefix)
									.replaceAll("\\$animationTime", getAnimationTime(animationConfig, framesCount))
									.replaceAll("\\$stepsCount", String.valueOf(framesCount))
									.replaceAll("\\$keyframesName", keyframesName);
		return styleProperty;
	}

	private String generateAnimationStyleBodyWithProperties(AnimationConfig animationConfig, String animationStyleName, StringBuilder animationClassBody) {
		String animationCssClass = ANIMATE_CLASS_TEMPLATE
										.replaceAll("\\$name", animationStyleName)
							  			.replaceAll("\\$styleProperty", animationClassBody.toString())
							  			.replaceAll("\\$backgroundImage", animationConfig.getSource());
		return animationCssClass;
	}

	private String getAnimationTime(AnimationConfig animationConfig, int framesCount) {
		return String.valueOf(animationConfig.getIntervalMs() * framesCount);
	}


}
