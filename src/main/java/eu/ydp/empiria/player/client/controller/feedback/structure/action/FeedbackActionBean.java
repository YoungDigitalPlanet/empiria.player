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

package eu.ydp.empiria.player.client.controller.feedback.structure.action;

import com.google.common.collect.Lists;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "action")
public class FeedbackActionBean {

    @XmlElement(name = "showText")
    private ShowTextAction showText;

    @XmlElement(name = "showUrl")
    private List<ShowUrlAction> showUrls = Lists.newArrayList();

    public ShowTextAction getShowText() {
        return showText;
    }

    public void setShowText(ShowTextAction showText) {
        this.showText = showText;
    }

    public List<ShowUrlAction> getShowUrls() {
        return showUrls;
    }

    public void setShowUrls(List<ShowUrlAction> showUrls) {
        this.showUrls = showUrls;
    }

    public List<FeedbackAction> getAllActions() {
        List<FeedbackAction> allActions = new ArrayList<FeedbackAction>();
        if (showText != null) {
            allActions.add(showText);
        }
        allActions.addAll(showUrls);

        return allActions;
    }
}
