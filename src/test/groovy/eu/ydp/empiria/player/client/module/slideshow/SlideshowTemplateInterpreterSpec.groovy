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

package eu.ydp.empiria.player.client.module.slideshow

import eu.ydp.empiria.player.client.module.slideshow.structure.SlideshowPagerBean
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideshowPlayerBean
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideshowTemplate
import spock.lang.Specification

class SlideshowTemplateInterpreterSpec extends Specification {

    def testObj = new SlideshowTemplateInterpreter();

    def "should return true when pager template is not null"() {
        given:
        def slideshowPlayer = new SlideshowPlayerBean()
        def template = new SlideshowTemplate()
        def pager = new SlideshowPagerBean()
        template.setSlideshowPager(pager)
        slideshowPlayer.setTemplate(template)

        when:
        def result = testObj.isPagerTemplateActivate(slideshowPlayer)

        then:
        assert result == true
    }

    def "should return false when pager template is null"() {
        given:
        def slideshowPlayer = new SlideshowPlayerBean()
        def template = new SlideshowTemplate()
        template.setSlideshowPager(null)
        slideshowPlayer.setTemplate(template)

        when:
        def result = testObj.isPagerTemplateActivate(slideshowPlayer)

        then:
        assert result == false
    }

    def "should return false when template is null"() {
        given:
        def slideshowPlayer = new SlideshowPlayerBean()
        slideshowPlayer.setTemplate(null)

        when:
        def result = testObj.isPagerTemplateActivate(slideshowPlayer)

        then:
        assert result == false
    }
}
