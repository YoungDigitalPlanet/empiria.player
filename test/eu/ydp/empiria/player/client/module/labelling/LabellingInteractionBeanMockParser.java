package eu.ydp.empiria.player.client.module.labelling;

import java.util.List;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import eu.ydp.empiria.player.client.TestJAXBParser;
import eu.ydp.empiria.player.client.module.labelling.structure.ChildBean;
import eu.ydp.empiria.player.client.module.labelling.structure.ChildrenBean;
import eu.ydp.empiria.player.client.module.labelling.structure.LabellingInteractionBean;
import eu.ydp.gwtutil.xml.XMLParser;

public class LabellingInteractionBeanMockParser extends TestJAXBParser<LabellingInteractionBean>{

	private List<ChildData> children;
	
	public LabellingInteractionBeanMockParser(List<ChildData> children) {
		this.children = children;
	}

	@Override
	public LabellingInteractionBean parse(String xml) {
		LabellingInteractionBean bean = super.parse(xml);
		
		ChildrenBean currChildren = bean.getChildren();
		if (currChildren != null){
			updateXmlContent(currChildren);
		}
		return bean;
	}

	private void updateXmlContent(ChildrenBean currChildren) {
		List<ChildBean> childBeans = currChildren.getChildBeanList();
		for (int i = 0 ; i < childBeans.size() ; i ++){
			XMLContent content = createXmlContent(i);
			childBeans.get(i).setContent(content);
		}
	}

	private XMLContent createXmlContent(int childIndex) {
		final Element childElement = createChildElement(childIndex);
		return new XMLContent() {

			@Override
			public Element getValue() {
				return childElement;
			}
		};
	}

	private Element createChildElement(int childIndex) {
		ChildData child = children.get(childIndex);
		String xml = child.xml();
		Document document = XMLParser.parse(xml);
		return document.getDocumentElement();
	}
}