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

public class MathTextFontHelper {
	private final ModuleSocket moduleSocket;
	private final IInlineModule module;
	
	public MathTextFontHelper(IInlineModule module, ModuleSocket moduleSocket) {
		this.moduleSocket = moduleSocket;
		this.module = module;
	}
	
	public Font initializeFont(Element element) {
		Map<String, String> styles = this.moduleSocket.getStyles(element);
		
		int fontSize = 16;
		String fontName = "Arial";
		boolean fontBold = false;
		boolean fontItalic = false;
		String fontColor = "#000000";
		
		if (styles.containsKey("-empiria-math-font-size")){
			fontSize = NumberUtils.tryParseInt(styles.get("-empiria-math-font-size"));
		}
		if (styles.containsKey("-empiria-math-font-family")){
			fontName = styles.get("-empiria-math-font-family");
		}
		if (styles.containsKey("-empiria-math-font-weight")){
			fontBold = styles.get("-empiria-math-font-weight").equalsIgnoreCase("bold");
		}
		if (styles.containsKey("-empiria-math-font-style")){
			fontItalic = styles.get("-empiria-math-font-style").equalsIgnoreCase("italic");
		}
		if (styles.containsKey("-empiria-math-color")){
			fontColor = styles.get("-empiria-math-color").toUpperCase();
		}
		
		Set<InlineFormattingContainerType> inlineStyles = this.moduleSocket.getInlineFormattingTags(module);		
		for (InlineFormattingContainerType inlineFormattingContainerType : inlineStyles) {
			switch (inlineFormattingContainerType) {
				case BOLD:
					fontBold = true;
					break;
				case ITALIC:
					fontItalic = true;
					break;
				default:
			}
		}
		
		Integer fontColorInt = NumberUtils.tryParseInt(fontColor.trim().substring(1), 16, 0); 
		Font font = new Font(fontSize, fontName, fontItalic, fontBold, new Color(fontColorInt / (256 * 256), fontColorInt / 256 % 256, fontColorInt % 256));
		return font;
	}

}
