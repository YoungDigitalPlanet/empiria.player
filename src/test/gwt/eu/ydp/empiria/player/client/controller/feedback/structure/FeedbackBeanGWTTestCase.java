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

package eu.ydp.empiria.player.client.controller.feedback.structure;

import com.google.gwt.core.client.GWT;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackParserFactory;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowTextAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowUrlAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowUrlActionSource;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.AndConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.CountConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.PropertyConditionBean;

import java.util.List;

public class FeedbackBeanGWTTestCase extends EmpiriaPlayerGWTTestCase {

    private FeedbackBean feedback;

    private final String xmlString = "<feedback>" + "<condition>" + "<and>" + "<countCondition count=\"3\" operator=\"==\">"
            + "<propertyCondition operator=\"&gt;=\" property=\"wrong\" value=\"1\"/>" + "<propertyCondition property=\"ok\"/>" + "</countCondition>"
            + "</and>" + "</condition>" + "<action>" + "<showText>testowy tekst</showText>" + "<showUrl href=\"sound.mp3\" type=\"narration\">"
            + "<source src=\"sound.mp3\" type=\"audio/mp4\"/>" + "<source src=\"sound.ogg\" type=\"audio/ogg\"/>" + "</showUrl>"
            + "<showUrl href=\"video.swf\" type=\"video\" />" + "</action>" + "</feedback>";

    @Override
    protected void gwtSetUp() throws Exception {
        FeedbackParserFactory feedbackParserFactory = GWT.create(FeedbackParserFactory.class);
        feedback = feedbackParserFactory.create().parse(xmlString);
        super.gwtSetUp();
    }

    public void testShouldHaveActionAndCondition() {
        assertNotNull(feedback.getCondition());
        assertNotNull(feedback.getCondition());
    }

    public void testShouldHaveFirstLevelConditions() {
        assertTrue(feedback.getCondition() instanceof AndConditionBean);
    }

    public void testShouldHaveCountCondition() {
        AndConditionBean andConditionBean = (AndConditionBean) feedback.getCondition();
        assertTrue(andConditionBean.getAllConditions().get(0) instanceof CountConditionBean);
    }

    public void testShouldHaveCorrectAttributesInCountCondition() {
        AndConditionBean andConditionBean = (AndConditionBean) feedback.getCondition();
        CountConditionBean countConditionBean = (CountConditionBean) andConditionBean.getAllConditions().get(0);
        assertTrue(countConditionBean.getCount() == 3);
        assertTrue(countConditionBean.getOperator().equals("=="));
    }

    public void testShouldHavePropertyCondition() {
        AndConditionBean andConditionBean = (AndConditionBean) feedback.getCondition();
        CountConditionBean countConditionBean = (CountConditionBean) andConditionBean.getAllConditions().get(0);
        assertTrue(countConditionBean.getAllConditions().get(0) instanceof PropertyConditionBean);
    }

    public void testShouldHaveCorrectAttributesInPropertyCondition() {
        AndConditionBean andConditionBean = (AndConditionBean) feedback.getCondition();
        CountConditionBean countConditionBean = (CountConditionBean) andConditionBean.getAllConditions().get(0);
        PropertyConditionBean propertyConditionBean = (PropertyConditionBean) countConditionBean.getAllConditions().get(0);
        assertTrue(propertyConditionBean.getProperty().equals("wrong"));
        assertTrue(propertyConditionBean.getValue().equals("1"));
        assertTrue(propertyConditionBean.getOperator().equals(">="));
    }

    public void testShouldHaveCorrectDefaultAttributesInPropertyCondition() {
        AndConditionBean andConditionBean = (AndConditionBean) feedback.getCondition();
        CountConditionBean countConditionBean = (CountConditionBean) andConditionBean.getAllConditions().get(0);
        PropertyConditionBean propertyConditionBean = (PropertyConditionBean) countConditionBean.getAllConditions().get(1);
        assertTrue(propertyConditionBean.getProperty().equals("ok"));
        assertTrue(propertyConditionBean.getValue().equals("true"));
        assertTrue(propertyConditionBean.getOperator().equals("=="));
    }

    public void testShouldHaveShowTextAction() {
        List<FeedbackAction> allActions = feedback.getActions();
        assertTrue(allActions.get(0) instanceof ShowTextAction);
    }

    public void testShouldHaveCorrectValueInShowTextAction() {
        List<FeedbackAction> allActions = feedback.getActions();
        ShowTextAction showTextAction = (ShowTextAction) allActions.get(0);
        assertEquals(showTextAction.getContent().getValue().getChildNodes().toString(), "testowy tekst");
    }

    public void testShouldHaveShowUrlActions() {
        List<FeedbackAction> allActions = feedback.getActions();
        assertTrue(allActions.get(1) instanceof ShowUrlAction);
        assertTrue(allActions.get(2) instanceof ShowUrlAction);
    }

    public void testShouldHaveCorrectAttributesInShowUrlActions() {
        List<FeedbackAction> allActions = feedback.getActions();
        ShowUrlAction firstShowUrlAction = (ShowUrlAction) allActions.get(1);
        ShowUrlAction secondShowUrlAction = (ShowUrlAction) allActions.get(2);
        assertTrue(firstShowUrlAction.getHref().equals("sound.mp3"));
        assertTrue(firstShowUrlAction.getType().equals("narration"));
        assertTrue(secondShowUrlAction.getHref().equals("video.swf"));
        assertTrue(secondShowUrlAction.getType().equals("video"));
    }

    public void testShouldHaveCorrectSourcesInShowUrlActionOfNarrationType() {
        List<FeedbackAction> allActions = feedback.getActions();
        ShowUrlAction firstShowUrlAction = (ShowUrlAction) allActions.get(1);
        List<ShowUrlActionSource> sources = firstShowUrlAction.getSources();
        ShowUrlActionSource firstSource = sources.get(0);
        ShowUrlActionSource secondSource = sources.get(1);
        assertTrue(firstSource.getSrc().equals("sound.mp3"));
        assertTrue(firstSource.getType().equals("audio/mp4"));
        assertTrue(secondSource.getSrc().equals("sound.ogg"));
        assertTrue(secondSource.getType().equals("audio/ogg"));
    }
}
