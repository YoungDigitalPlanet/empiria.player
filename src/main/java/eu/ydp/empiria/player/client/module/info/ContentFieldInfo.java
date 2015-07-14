package eu.ydp.empiria.player.client.module.info;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import eu.ydp.empiria.player.client.module.info.handler.FieldValueHandler;
import eu.ydp.gwtutil.client.StringUtils;

public class ContentFieldInfo {

    public enum FieldType {
        TEST("test"), ITEM("item"), UNKNOWN("unknown");

        private final String value;

        private FieldType(String value) {
            this.value = value;
        }

        public static FieldType getType(String value) {
            FieldType type = UNKNOWN;

            for (FieldType typeValue : values()) {
                if (value.startsWith(typeValue.value)) {
                    type = typeValue;
                    break;
                }
            }

            return type;
        }
    }

    private static final String TAG_FORMAT = "$[%1$s]";

    private static final String PATTERN_FORMAT = "\\$\\[%1$s]";

    private String tag;

    private String pattern;

    private String valueName;

    private FieldValueHandler handler;

    private FieldType type;

    public ContentFieldInfo setTagName(String tagName) {
        this.tag = format(TAG_FORMAT, tagName);
        this.pattern = format(PATTERN_FORMAT, tagName);
        this.type = FieldType.getType(tagName);
        this.valueName = createValueName(tagName);
        return this;
    }

    private String createValueName(String tagName) {
        String[] tagNameParts = tagName.split("\\.");
        return (tagNameParts.length == 2) ? tagNameParts[1].toUpperCase() : StringUtils.EMPTY_STRING;
    }

    public String getValueName() {
        return valueName;
    }

    public String getPattern() {
        return pattern;
    }

    public String getTag() {
        return tag;
    }

    public ContentFieldInfo setHandler(FieldValueHandler handler) {
        this.handler = handler;
        return this;
    }

    public String getValue(int refItemIndex) {
        String value = StringUtils.EMPTY_STRING;

        if (handler != null) {
            value = handler.getValue(this, refItemIndex);
        }

        return value;
    }

    public FieldType getType() {
        return type;
    }

    private String format(String pattern, String value) {
        Splitter splitter = Splitter.on("%1$s");
        Joiner joiner = Joiner.on(value);
        return joiner.join(splitter.split(pattern));
    }
}
