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

package eu.ydp.empiria.player.client.module.colorfill.structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "buttons")
public class ButtonsContainer {

    @XmlElement(name = "button")
    private List<ColorButton> buttons;

    @XmlElement(name = "eraserButton")
    private EraserButton eraserButton;

    public List<ColorButton> getButtons() {
        return buttons;
    }

    public void setButtons(List<ColorButton> buttons) {
        this.buttons = buttons;
    }

    public EraserButton getEraserButton() {
        return eraserButton;
    }

    public void setEraserButton(EraserButton eraserButton) {
        this.eraserButton = eraserButton;
    }

}
