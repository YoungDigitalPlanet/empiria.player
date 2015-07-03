package eu.ydp.empiria.player.client.module.mathtext;

import com.google.gwt.xml.client.Element;
import com.mathplayer.player.geom.Color;
import com.mathplayer.player.geom.Font;
import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.module.IInlineModule;
import eu.ydp.empiria.player.client.module.InlineFormattingContainerType;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.NumberUtils;

import java.util.Map;
import java.util.Set;

public class MathTextFontInitializer {

    private DTOMathTextDefaultFontPropertiesProvider defaultFontPropertiesProvider; // TODO:
    // use
    // @Inject
    // here

    public Font initialize(IInlineModule module, ModuleSocket moduleSocket, Element element) {

        PlayerGinjector playerGinjector = PlayerGinjectorFactory.getPlayerGinjector();
        StyleSocket styleSocket = playerGinjector.getStyleSocket();

        return initialize(module, moduleSocket, element, styleSocket);
    }

    public Font initialize(IInlineModule module, ModuleSocket moduleSocket, Element element, StyleSocket styleSocket) {

        defaultFontPropertiesProvider = new DTOMathTextDefaultFontPropertiesProvider();
        DTOMathTextFontProperties fontProperties = defaultFontPropertiesProvider.createDefaultProprerties();
        updateFontPropertiesAccordingToStyles(styleSocket.getStyles(element), fontProperties);
        updateFontPropertiesAccordingToInlineFormatters(moduleSocket.getInlineFormattingTags(module), fontProperties);

        Integer fontColorInt = NumberUtils.tryParseInt(fontProperties.getColor().trim().substring(1), 16, 0);
        Color color = new Color(fontColorInt / (256 * 256), fontColorInt / 256 % 256, fontColorInt % 256);
        Font font = new Font(fontProperties.getSize(), fontProperties.getName(), fontProperties.isItalic(), fontProperties.isBold(), color);

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
