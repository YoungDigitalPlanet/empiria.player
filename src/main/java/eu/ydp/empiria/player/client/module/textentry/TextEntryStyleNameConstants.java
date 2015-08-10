package eu.ydp.empiria.player.client.module.textentry;

import com.google.gwt.i18n.client.Constants;

public interface TextEntryStyleNameConstants extends Constants {

    /**
     * Poniewaz podstawowa clasa gapy 'qp-text-textentry' zmienia sie na
     * *-corect, *-wrong Dodalem ten style aby w pewny sposob wyszukiwac gapy w
     * testach E2E.
     */

    @DefaultStringValue("qp-textentry")
    String QP_TEXTENTRY();

    @DefaultStringValue("qp-text-textentry")
    String QP_TEXT_TEXTENTRY();

    @DefaultStringValue("qp-text-textentry-content")
    String QP_TEXT_TEXTENTRY_CONTENT();

    @DefaultStringValue("qp-text-textentry-correct")
    String QP_TEXT_TEXTENTRY_CORRECT();

    @DefaultStringValue("qp-text-textentry-none")
    String QP_TEXT_TEXTENTRY_NONE();

    @DefaultStringValue("qp-text-textentry-prefix")
    String QP_TEXT_TEXTENTRY_PREFIX();

    @DefaultStringValue("qp-text-textentry-sufix")
    String QP_TEXT_TEXTENTRY_SUFIX();

    @DefaultStringValue("qp-text-textentry-wrong")
    String QP_TEXT_TEXTENTRY_WRONG();

}
