package eu.ydp.empiria.player.client.module.slideshow.structure;

import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "narration")
public class SlideNarrationBean {

	@XmlValue
	private XMLContent narrationValue;

	public XMLContent getNarrationValue() {
		return narrationValue;
	}

	public void setNarrationValue(XMLContent narrationValue) {
		this.narrationValue = narrationValue;
	}
}
