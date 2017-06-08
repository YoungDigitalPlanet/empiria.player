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

package eu.ydp.empiria.player.client.controller.data;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import eu.ydp.empiria.player.client.controller.style.StyleLinkDeclaration;
import eu.ydp.empiria.player.client.module.item.ProgressToStringRangeMap;
import eu.ydp.empiria.player.client.module.item.ReportFeedbacksParser;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import eu.ydp.empiria.player.client.util.localisation.LocalePublisher;
import eu.ydp.empiria.player.client.util.localisation.LocaleVariable;

import java.util.ArrayList;
import java.util.List;

public class ItemDataSource {


    public static final String STYLE_DECLARATION = "styleDeclaration";
    public static final String ASSESSMENT_ITEM = "assessmentItem";
    public static final String TITLE = "title";
    public static final String IDENTIFIER = "identifier";
    public static final String REPORT_FEEDBACK_TEXT = "reportFeedbackText";

    private XmlData data;
    private StyleLinkDeclaration styleDeclaration;
    private String title;
    private ReportFeedbacksParser reportFeedbacksParser = new ReportFeedbacksParser();
    private ProgressToStringRangeMap reportFeedbacks;
    private final String errorMessage;
    private String identifier;

    public String getPageIdentifier() {
        return identifier;
    }

    public ItemDataSource(XmlData d) {
        data = d;
        styleDeclaration = new StyleLinkDeclaration(data.getDocument().getElementsByTagName(STYLE_DECLARATION), data.getBaseURL());
        Node rootNode = data.getDocument().getElementsByTagName(ASSESSMENT_ITEM).item(0);
        Element rootElement = (Element) rootNode;
        title = rootElement.getAttribute(TITLE);
        identifier = rootElement.getAttribute(IDENTIFIER);
        NodeList feedbacksNodeList = rootElement.getElementsByTagName(REPORT_FEEDBACK_TEXT);
        this.reportFeedbacks = reportFeedbacksParser.parse(feedbacksNodeList);
        errorMessage = "";
    }

    public ItemDataSource(String err) {
        String detail = "";
        if (err.contains(":")) {
            detail = err.substring(0, err.indexOf(":"));
        }
        errorMessage = LocalePublisher.getText(LocaleVariable.ERROR_ITEM_FAILED_TO_LOAD) + detail;
    }

    public XmlData getItemData() {
        return data;
    }

    public List<String> getStyleLinksForUserAgent(String userAgent) {
        if (styleDeclaration != null) {
            return styleDeclaration.getStyleLinksForUserAgent(userAgent);
        }
        return new ArrayList<String>();
    }

    public boolean isError() {
        return errorMessage.length() > 0;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getTitle() {
        if (title != null) {
            return title;
        } else {
            return "";
        }
    }

    public ProgressToStringRangeMap getFeedbacks() {
        return reportFeedbacks;
    }
}
