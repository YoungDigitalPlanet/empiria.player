package eu.ydp.empiria.player.client.animation.css;

import java.util.Set;

import com.google.common.collect.Sets;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.animation.AnimationConfig;
import eu.ydp.empiria.player.client.util.geom.Size;

public class CssAnimationClassBuilder {
	private static final String ANIMATE_CLASS_TEMPLATE = " .$name { $styleProperty background-image: url($backgroundImage) } ";
	private static final String ANIMATE_PROPERTY = " $prefixanimation: $keyframesName $animationTimems steps($stepsCount, end);";

	private final Set<String> generatedClassNames = Sets.newHashSet();
	private final StyleAppender styleInjector;
	private final CssKeyFrameBuilder cssKeyFrameBuilder;
	
	@Inject
	public CssAnimationClassBuilder(StyleAppender styleInjector, CssKeyFrameBuilder cssKeyFrameBuilder) {
		this.styleInjector = styleInjector;
		this.cssKeyFrameBuilder = cssKeyFrameBuilder;
	}

	public String createAnimationCssClassName(AnimationConfig animationConfig, Size imgSize){
		CssAnimationConfig cssAnimationConfig = new CssAnimationConfig(animationConfig, imgSize);
		String animationStyleName = cssAnimationConfig.getAnimationStyleName();
		
		if(!generatedClassNames.contains(animationStyleName)){
			createAnimationCss(cssAnimationConfig);
			generatedClassNames.add(animationStyleName);
		}
		
		return animationStyleName;
	}

	private void createAnimationCss(CssAnimationConfig cssAnimationConfig) {
		String animationClassBody = generateAnimationClassBody(cssAnimationConfig);
		String animationCssClass = generateAnimationCssClass(cssAnimationConfig, animationClassBody);
		styleInjector.appendStyleToDocument(animationCssClass);
	}

	private String generateAnimationClassBody(CssAnimationConfig cssAnimationConfig) {
		String keyframesName = cssKeyFrameBuilder.generateAnimationKeyframesName(cssAnimationConfig);
		
		StringBuilder animationClassBody = new StringBuilder();
		for (CssAnimationPrefix prefix : CssAnimationPrefix.values()) {
			String styleProperty = generateCssAnimationPropertyValue(cssAnimationConfig, keyframesName, prefix);
			animationClassBody.append(styleProperty);
		}
		
		return animationClassBody.toString();
	}
	
	private String generateCssAnimationPropertyValue(CssAnimationConfig animationConfig, String keyframesName, CssAnimationPrefix prefix) {
		String styleProperty = ANIMATE_PROPERTY
									.replaceAll("\\$prefix", prefix.toCss())
									.replaceAll("\\$animationTime", animationConfig.getAnimationTime())
									.replaceAll("\\$stepsCount", String.valueOf(animationConfig.getFramesCount()))
									.replaceAll("\\$keyframesName", keyframesName);
		return styleProperty;
	}

	private String generateAnimationCssClass(CssAnimationConfig animationConfig, String animationClassBody) {
		String animationCssClass = ANIMATE_CLASS_TEMPLATE
										.replaceAll("\\$name", animationConfig.getAnimationStyleName())
							  			.replaceAll("\\$styleProperty", animationClassBody)
							  			.replaceAll("\\$backgroundImage", animationConfig.getSource());
		return animationCssClass;
	}
}
