package eu.ydp.empiria.player.client.animation.css;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.util.geom.Size;

public class CssKeyFrameBuilder {

	private final static String ANIMATE_KEYFRAMES_TEMPLATE = "@$prefixkeyframes $name { from {background-position:$from} to {background-position:$to} }";
	private final StyleAppender styleInjector;
	
	@Inject
	public CssKeyFrameBuilder(StyleAppender styleInjector) {
		this.styleInjector = styleInjector;
	}

	public String generateAnimationKeyframesName(CssAnimationConfig animationConfig){
		String keyFrameStyleName = animationConfig.getAnimationStyleName() + "_keyframes";
		for(CssAnimationPrefix prefix : CssAnimationPrefix.values()){
			String keyframes = createSingleKeyframesProperty(keyFrameStyleName, animationConfig.getImgSize(), prefix);
			appendStyleToDocument(keyframes);
		}
		return keyFrameStyleName;
	}

	private void appendStyleToDocument(String template) {
		styleInjector.appendStyleToDocument(template);
	}
	
	private String createSingleKeyframesProperty(String keyFrameStyleName, Size imgSize, CssAnimationPrefix prefix) {
		String keyframes = ANIMATE_KEYFRAMES_TEMPLATE
				.replaceAll("\\$prefix", prefix.toCss())
				.replaceAll("\\$name", keyFrameStyleName)
				.replaceAll("\\$from", "0px")
				.replaceAll("\\$to", -imgSize.getWidth()+"px");
		return keyframes;
	}
}
