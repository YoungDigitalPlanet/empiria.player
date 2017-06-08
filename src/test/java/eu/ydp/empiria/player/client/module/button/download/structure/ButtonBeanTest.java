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

package eu.ydp.empiria.player.client.module.button.download.structure;

import eu.ydp.empiria.player.client.AbstractJAXBTestBase;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class ButtonBeanTest extends AbstractJAXBTestBase<ButtonBean> {
    private static final String HREF = "media/image.png";
    private static final String ALT = "Link to image file";
    private static final String ID = "dummy1";
    String xml = "<button alt=\"" + ALT + "\" href=\"" + HREF + "\" id=\"" + ID + "\"/>";

    @Test
    public void beanTest() {
        ButtonBean buttonBean = createBeanFromXMLString(xml);
        assertThat(buttonBean.getId()).isEqualTo(ID);
        assertThat(buttonBean.getAlt()).isEqualTo(ALT);
        assertThat(buttonBean.getHref()).isEqualTo(HREF);
    }

}
