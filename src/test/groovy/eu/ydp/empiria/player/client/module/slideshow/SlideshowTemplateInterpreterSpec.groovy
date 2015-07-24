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
