package eu.ydp.empiria.player.client.module.slideshow.structure;

import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "title")
public class SlideshowTitleBean {

	@XmlValue
	private XMLContent titleValue;

	public XMLContent getTitleValue() {
		return titleValue;
	}

	public void setTitleValue(XMLContent titleValue) {
		this.titleValue = titleValue;
	}
}
