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

package eu.ydp.empiria.player.client.module.math;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import com.mathplayer.player.MathPlayerManager;
import com.mathplayer.player.geom.Color;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Point;
import com.mathplayer.player.interaction.GapIdentifier;
import com.mathplayer.player.interaction.InteractionManager;
import com.mathplayer.player.model.interaction.CustomFieldDescription;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.core.flow.LifecycleModule;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.module.containers.AbstractActivityContainerModuleBase;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.NumberUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MathModule extends AbstractActivityContainerModuleBase implements LifecycleModule {

    private static final String MINUS = "-";

    private static final String PX = "px";

    private MathModuleViewUiBinder uiBinder;

    @UiTemplate("MathModuleView.ui.xml")
    interface MathModuleViewUiBinder extends UiBinder<Widget, MathModule> {
    }

    @UiField
    protected AbsolutePanel outerPanel;

    @UiField
    protected FlowPanel mainPanel;

    @UiField
    protected AbsolutePanel gapsPanel;

    @UiField
    protected FlowPanel placeholder;

    private int fontSize = 16;

    private String fontName = "Arial";

    private boolean fontBold = false;

    private boolean fontItalic = false;

    private String fontColor = "#000000";

    private Map<String, String> styles;

    private Element moduleElement;

    private List<MathGap> mathGaps;

    private MathPlayerManager mathManager;

    private InteractionManager mathInteractionManager;

    @Inject
    private StyleSocket styleSocket;

    @Override
    public void initModule(Element element) {
        uiBinder = GWT.create(MathModuleViewUiBinder.class);
        uiBinder.createAndBindUi(this);

        moduleElement = element;
        styles = styleSocket.getStyles(moduleElement);

        readAttributes(element);
        initStyles(styles);
        initializePanels();
        initializeMathPlayer();
        generateGaps(getBodyGenerator());
        setGapMathStyles();
    }

    protected void setGapMathStyles() {
        for (MathGap gap : getMathGaps()) {
            gap.setMathStyles(styles);
        }
    }

    @Override
    public Widget getView() {
        return placeholder;
    }

    public void initStyles(Map<String, String> styles) {
        if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_FONT_SIZE)) {
            fontSize = NumberUtils.tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_FONT_SIZE));
        }

        if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_FONT_FAMILY)) {
            fontName = styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_FONT_FAMILY);
        }

        if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_FONT_WEIGHT)) {
            fontBold = styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_FONT_WEIGHT).equalsIgnoreCase(EmpiriaStyleNameConstants.VALUE_BOLD);
        }

        if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_FONT_STYLE)) {
            fontItalic = styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_FONT_STYLE).equalsIgnoreCase(EmpiriaStyleNameConstants.VALUE_ITALIC);
        }

        if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_COLOR)) {
            fontColor = styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_COLOR).toUpperCase();
        }
    }

    @Override
    public void onBodyLoad() {
    }

    @Override
    public void onBodyUnload() {
    }

    @Override
    public void onSetUp() {
        placeGaps();
    }

    @Override
    public void onStart() {
        setSizeOfGapDummies();

        mathInteractionManager = createMath();
        gapsPanel.setWidth(mainPanel.getOffsetWidth() + PX);
        gapsPanel.setHeight(mainPanel.getOffsetHeight() + PX);

        String verticalAlign = MINUS + mathManager.getBaseline() + PX;
        placeholder.getElement().getStyle().setProperty("verticalAlign", verticalAlign);

        positionGaps();
    }

    private void positionGaps() {
        List<CustomFieldDescription> customFieldDescriptions = mathInteractionManager.getCustomFieldDescriptions();
        Iterator<CustomFieldDescription> customFieldDescriptionsIterator = customFieldDescriptions.iterator();

        for (MathGap gap : getMathGaps()) {
            if (!customFieldDescriptionsIterator.hasNext()) {
                break;
            }

            Point position = customFieldDescriptionsIterator.next().getPosition();
            gapsPanel.setWidgetPosition(gap.getContainer(), position.getX(), position.getY());
        }
    }

    private void setSizeOfGapDummies() {
        for (MathGap gap : getMathGaps()) {
            GapIdentifier gapId = GapIdentifier.createIdIdentifier(gap.getUid());

            if (gapId != null) {
                int width = gap.getContainer().getOffsetWidth();
                int height = gap.getContainer().getOffsetHeight();

                mathManager.setCustomFieldWidth(gapId, width);
                mathManager.setCustomFieldHeight(gapId, height);
            }
        }
    }

    @Override
    public void onClose() {
    }

    private void placeGaps() {
        for (MathGap gap : getMathGaps()) {
            Widget gapContainer = gap.getContainer();
            Style gapStyle = gapContainer.getElement().getStyle();

            gapStyle.setTop(0, Unit.PX);
            gapStyle.setLeft(0, Unit.PX);
            gapStyle.setPosition(Position.ABSOLUTE);
        }
    }

    private void initializePanels() {
        applyIdAndClassToView(mainPanel);

        outerPanel.getElement().getStyle().setOverflow(Overflow.VISIBLE);
        gapsPanel.getElement().getStyle().setOverflow(Overflow.VISIBLE);

        Style mainPanelStyle = mainPanel.getElement().getStyle();
        mainPanelStyle.setPosition(Position.ABSOLUTE);
        mainPanelStyle.setTop(0, Unit.PX);
        mainPanelStyle.setLeft(0, Unit.PX);
    }

    private void initializeMathPlayer() {
        mathManager = new MathPlayerManager();
        Integer fontColorInt = NumberUtils.tryParseInt(fontColor.trim().substring(1), 16, 0);
        Font font = new Font(fontSize, fontName, fontItalic, fontBold, new Color(fontColorInt / (256 * 256), fontColorInt / 256 % 256, fontColorInt % 256));

        mathManager.setFont(font);
    }

    private InteractionManager createMath() {
        return mathManager.createMath(moduleElement.getChildNodes().toString(), mainPanel);
    }

    private void generateGaps(BodyGeneratorSocket bgs) {
        NodeList gapsNodeList = moduleElement.getElementsByTagName(EmpiriaTagConstants.NAME_GAP);

        for (int i = 0; i < gapsNodeList.getLength(); i++) {
            Element gapElement = (Element) gapsNodeList.item(i);
            bgs.processNode(gapElement, gapsPanel);
        }
    }

    private List<MathGap> getMathGaps() {
        if (mathGaps == null) {
            mathGaps = new ArrayList<MathGap>();

            for (IModule child : getModuleSocket().getChildren(this)) {
                if (child instanceof MathGap) {
                    mathGaps.add((MathGap) child);
                }
            }
        }

        return mathGaps;
    }
}
