package eu.ydp.empiria.player.client.module.mathtext;

public class DTOMathTextDefaultFontPropertiesProvider {

    public static final int SIZE = 16;
    public static final String NAME = "Arial";
    public static final boolean BOLD = false;
    public static final boolean ITALIC = false;
    public static final String COLOR = "#000000";

    public DTOMathTextFontProperties createDefaultProprerties() {
        DTOMathTextFontProperties defaultProperties = new DTOMathTextFontProperties();
        defaultProperties.setBold(BOLD);
        defaultProperties.setName(NAME);
        defaultProperties.setColor(COLOR);
        defaultProperties.setItalic(ITALIC);
        defaultProperties.setSize(SIZE);

        return defaultProperties;
    }
}
