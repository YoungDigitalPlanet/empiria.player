package eu.ydp.empiria.player.client.module.mathtext;

import java.util.Map;
import java.util.Set;

import com.google.gwt.xml.client.Element;
import com.mathplayer.player.geom.Color;
import com.mathplayer.player.geom.Font;

import eu.ydp.empiria.player.client.module.IInlineModule;
import eu.ydp.empiria.player.client.module.InlineFormattingContainerType;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.gwtutil.client.NumberUtils;

public class MathTextFontInitializer {
		
	private DTOMathTextDefaultFontPropertiesProvider defaultFontPropertiesProvider; // TODO: use @Inject here
	
	public Font initialize(IInlineModule module, ModuleSocket moduleSocket, Element element) {
		defaultFontPropertiesProvider = new DTOMathTextDefaultFontPropertiesProvider();
		DTOMathTextFontProperties fontProperties = defaultFontPropertiesProvider.createDefaultProprerties();
		updateFontPropertiesAccordingToStyles(moduleSocket.getStyles(element), fontProperties);
		updateFontPropertiesAccordingToInlineFormatters(moduleSocket.getInlineFormattingTags(module), fontProperties);

		Integer fontColorInt = NumberUtils.tryParseInt(fontProperties.getColor().trim().substring(1), 16, 0);
		Color color = new Color(fontColorInt / (256 * 256), fontColorInt / 256 % 256, fontColorInt % 256);
		Font font = new Font(fontProperties.getSize(), fontProperties.getName(), fontProperties.isItalic(),
				fontProperties.isBold(), color);
		
		return font;
	}
	
	/**
	 * @param styles - style name key / style value
	 * @return 
	 */
	public void updateFontPropertiesAccordingToStyles(Map<String, String> styles, DTOMathTextFontProperties fontProperties) {
		if (styles.containsKey("-empiria-math-font-size")) {
			fontProperties.setSize(NumberUtils.tryParseInt(styles.get("-empiria-math-font-size")));
		}
		if (styles.containsKey("-empiria-math-font-family")) {
			fontProperties.setName(styles.get("-empiria-math-font-family"));
		}
		if (styles.containsKey("-empiria-math-font-weight")) {
			fontProperties.setBold(styles.get("-empiria-math-font-weight").equalsIgnoreCase("bold"));
		}
		if (styles.containsKey("-empiria-math-font-style")) {
			fontProperties.setItalic(styles.get("-empiria-math-font-style").equalsIgnoreCase("italic"));
		}
		if (styles.containsKey("-empiria-math-color")) {
			fontProperties.setColor(styles.get("-empiria-math-color").toUpperCase());
		}
	}

	private void updateFontPropertiesAccordingToInlineFormatters(Set<InlineFormattingContainerType> inlineTags, DTOMathTextFontProperties fontProperties) {
		for (InlineFormattingContainerType inlineFormattingContainerType : inlineTags) {
			switch (inlineFormattingContainerType) {
				case BOLD:
					fontProperties.setBold(true);
					break;
				case ITALIC:
					fontProperties.setItalic(true);
					break;
				default:
			}
		}
	}

}
