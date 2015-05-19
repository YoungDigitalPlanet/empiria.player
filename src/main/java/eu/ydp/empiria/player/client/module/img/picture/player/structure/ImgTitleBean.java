package eu.ydp.empiria.player.client.module.img.picture.player.structure;

import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "title")
public class ImgTitleBean {

	@XmlValue
	private XMLContent titleName;

	public XMLContent getTitleName() {
		return titleName;
	}

	public void setTitleName(XMLContent titleName) {
		this.titleName = titleName;
	}
}
