package eu.ydp.empiria.player.client.module;

public enum InlineFormattingContainerType {
    BOLD("b"), ITALIC("i");

    private String htmlTag;

    private InlineFormattingContainerType(String htmlTag) {
        this.htmlTag = htmlTag;
    }

    public String getHtmlTag() {
        return htmlTag;
    }
}
