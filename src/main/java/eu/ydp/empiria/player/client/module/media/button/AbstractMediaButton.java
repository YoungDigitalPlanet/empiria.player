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

package eu.ydp.empiria.player.client.module.media.button;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

import static com.google.gwt.user.client.Event.*;

/**
 * bazowy przycisk dla kontrolerow multimediow
 *
 */
public abstract class AbstractMediaButton extends AbstractMediaController {
    private String baseStyleName;
    private String onClickStyleName;
    private String hoverStyleName;
    private String originalStyleName;
    private boolean active = false;
    private final FlowPanel divElement = new FlowPanel();
    private boolean singleClick = true;

    @Inject
    private UserAgentUtil userAgentUtil;

    /**
     * bazowy przycisk dla kontrolerow multimediow
     *
     * @param baseStyleName
     * @param singleClick   czy element jest zwyklym przyciskiem i mousup jest ignorowany wartosc true<br/>
     *                      false wywoluje ponownie akcje na mouseup
     */
    public AbstractMediaButton(String baseStyleName, boolean singleClick) {
        this.originalStyleName = baseStyleName;
        setStyleNames();
        this.singleClick = singleClick;
        initWidget(divElement);
    }

    public AbstractMediaButton(String baseStyleName) {
        this(baseStyleName, true);
    }

    @Override
    public final void setStyleNames() {
        String toAdd = getSuffixToAdd();
        this.baseStyleName = this.originalStyleName + toAdd;
        this.onClickStyleName = baseStyleName + toAdd + CLICK_SUFFIX;
        this.hoverStyleName = baseStyleName + toAdd + HOVER_SUFFIX;
    }

    @Override
    public void init() {
        if (isSupported()) {
            initEvents();
            this.setStyleName(this.baseStyleName);
        } else {
            this.setStyleName(this.baseStyleName + UNSUPPORTED_SUFFIX);
        }
    }

    private void initEvents() {
        if (userAgentUtil.isMobileUserAgent()) {
            sinkEvents(ONTOUCHSTART | ONTOUCHEND);
        } else {
            sinkEvents(ONMOUSEOVER | ONMOUSEOUT | ONMOUSEDOWN | ONMOUSEUP);
        }
    }

    @Override
    public void onBrowserEvent(Event event) {
        event.preventDefault();
        int eventType = event.getTypeInt();
        switch (eventType) {
            case ONMOUSEDOWN:
            case ONTOUCHSTART:
                onClick();
                break;
            case ONMOUSEUP:
                if (!singleClick) {
                    onClick();
                }
                break;
            case ONMOUSEOVER:
                onMouseOver();
                break;
            case ONTOUCHEND:
            case ONMOUSEOUT:
                onMouseOut();
                break;
        }
    }

    /**
     * zdarzenie click
     */
    protected abstract void onClick();

    protected boolean isActive() {
        return active;
    }

    protected void setActive(boolean active) {
        this.active = active;
    }

    /**
     * zmiana stylu elementu dla zdarzenia click
     */
    protected void changeStyleForClick() {
        if (active) {
            divElement.getElement().addClassName(onClickStyleName);
        } else {
            divElement.getElement().removeClassName(onClickStyleName);
        }
    }

    /**
     * zmiana stylu elementu dla zdarzenia onMouseOver
     */
    protected void onMouseOver() {
        if (hoverStyleName.trim().isEmpty()) {
            divElement.getElement().addClassName(hoverStyleName);
        }
    }

    /**
     * zmiana stylu elementu dla zdarzenia onMouseOut
     */
    protected void onMouseOut() {
        if (hoverStyleName.trim().isEmpty()) {
            divElement.getElement().removeClassName(hoverStyleName);
        }
    }

}
