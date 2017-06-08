/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.util.localisation;

@Deprecated
public abstract class LocalePublisher {

    public static String getText(LocaleVariable var) {

        String varString = var.toString();
        String text = "";

        if (checkVariable(varString)) {
            text = getValue(varString);
        } else {
            text = getDefaultValue(var);
        }

        return text;
    }

    private static native boolean checkVariable(String varName)/*-{
        if (typeof $wnd.QtiPlayer !== 'undefined' && $wnd.QtiPlayer != null) {
            if (typeof $wnd.QtiPlayer.locale !== 'undefined' && $wnd.QtiPlayer.locale != null) {
                if (typeof $wnd.QtiPlayer.locale[varName] !== 'undefined' && $wnd.QtiPlayer.locale[varName] != null) {
                    return true;
                }
            }
        }
        return false;
    }-*/;

    private static native String getValue(String varName)/*-{
        return $wnd.QtiPlayer.locale[varName];
    }-*/;

    private static String getDefaultValue(LocaleVariable var) {
        switch (var) {
            case SUMMARY_PAGE:
                return "Page";
            case MESSAGE_TITLE_ERROR:
                return "Error:";
            case MESSAGE_TITLE_WARNING:
                return "Warning:";
            case MESSAGE_TITLE_INFO:
                return "Status:";
            case MESSAGE_LOADING:
                return "Loading contents...";
            case MESSAGE_LOADED:
                return "Contents loaded";
            case MESSAGE_ASSESSMENT_ERROR:
                return "Failed to display assessment";
            case MESSAGE_ITEM_ERROR:
                return "Failed to display item";
            case MESSAGE_READOPTIONS_ERROR:
                return "Failed to read options";
            case ERROR_ASSESSMENT_FAILED_TO_LOAD:
                return "Failed to load assessment: ";
            case ERROR_ITEM_FAILED_TO_LOAD:
                return "Failed to load item: ";
            case PLAYER_HEADER:
                return "";
            case PLAYER_FOOTER:
                return "";
            case TOC_TITLE:
                return "Table of Contents";
            case TOC_PAGE:
                return "Page";
            case TOC_PAGE_DOT:
                return ": ";
            case ITEM_SCORE1:
                return "Score: ";
            case ITEM_SCORE2:
                return " out of ";
            case ITEM_SCORE3:
                return " points.";
            case COMBO_TOC:
                return "Table of Contents";
            case COMBO_SUMMARY:
                return "Summary";
            case SUMMARY_INFO_YOURSCOREIS1:
                return "Your score is: ";
            case SUMMARY_INFO_YOURSCOREIS2:
                return "% ";
            case SUMMARY_INFO_YOURSCOREIS3:
                return " points. ";
            case SUMMARY_INFO_YOURSCOREIS4:
                return " Time: ";
            case SUMMARY_INFO_YOURSCOREIS5:
                return "";
            case SUMMARY_STATS_TIME_SUFIX:
                return "";
            case SUMMARY_STATS_TIME_NO:
                return "-";
            case SUMMARY_STATS_CHECKCOUNT_NO:
                return "-";
            case SUMMARY_STATS_CHECKCOUNT_SUFIX:
                return " checks";
            case SUMMARY_STATS_MISTAKES_SUFIX:
                return " mistakes";
            case SUMMARY_STATS_MISTAKES_NO:
                return "-";
            case SUMMARY_NOTSCORED:
                return "not scored";
            case SUMMARY_NOTVISITED:
                return "not visited";

        }
        return "";
    }

}
