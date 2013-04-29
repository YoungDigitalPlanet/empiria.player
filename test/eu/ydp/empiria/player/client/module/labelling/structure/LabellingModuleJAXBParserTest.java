package eu.ydp.empiria.player.client.module.labelling.structure;

import static eu.ydp.empiria.player.client.module.labelling.LabellingTestAssets.CHILD_0;
import static eu.ydp.empiria.player.client.module.labelling.LabellingTestAssets.CHILD_1;
import static eu.ydp.empiria.player.client.module.labelling.LabellingTestAssets.CHILD_2;
import static eu.ydp.empiria.player.client.module.labelling.LabellingTestAssets.CHILD_3;
import static eu.ydp.empiria.player.client.module.labelling.LabellingTestAssets.IMAGE_BEAN;
import static eu.ydp.empiria.player.client.module.labelling.LabellingTestAssets.XML_FULL;
import static eu.ydp.empiria.player.client.module.labelling.LabellingTestAssets.XML_NO_CHILDREN;
import static eu.ydp.empiria.player.client.module.labelling.LabellingTestAssets.XML_NO_CHILDREN_NODE;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;

import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.module.labelling.ChildData;

public class LabellingModuleJAXBParserTest extends AbstractEmpiriaPlayerGWTTestCase {

	public void testParse() {
		// when
		LabellingInteractionBean labellingBean = parse(XML_FULL);
		
		// then
		assertEquals(IMAGE_BEAN, labellingBean.getImg());		
		assertChildren(labellingBean.getChildren().getChildBeanList(), CHILD_0, CHILD_1, CHILD_2, CHILD_3);		
	}

	public void testNoChildren() {
		// when
		LabellingInteractionBean labellingBean = parse(XML_NO_CHILDREN);
		
		// then
		assertEquals(IMAGE_BEAN, labellingBean.getImg());			
		assertChildren(labellingBean.getChildren().getChildBeanList());		
	}

	public void testNoChildrenNode() {
		// when
		LabellingInteractionBean labellingBean = parse(XML_NO_CHILDREN_NODE);
		
		// then
		assertEquals(IMAGE_BEAN, labellingBean.getImg());			
		assertNull(labellingBean.getChildren());		
	}
	
	private LabellingInteractionBean parse(String xml) {
		LabellingModuleJAXBParserFactory jaxbParserFactory = GWT.create(LabellingModuleJAXBParserFactory.class);
		JAXBParser<LabellingInteractionBean> jaxbParser = jaxbParserFactory.create();
		LabellingInteractionBean labellingBean = jaxbParser.parse(xml);
		return labellingBean;
	}
	
	private void assertChildren(List<ChildBean> children, ChildData... childDatas) {
		assertEquals(childDatas.length, children.size());
		for (int i = 0 ; i < children.size() ; i ++){
			assertChild(children.get(i), childDatas[i]);
		}
	}

	private void assertChild(ChildBean childBean, ChildData childData) {
		assertEquals(childData.getX(), childBean.getX());
		assertEquals(childData.getY(), childBean.getY());
		assertXmlRoughly(childBean, childData);
	}

	private void assertXmlRoughly(ChildBean childBean, ChildData childData) {
		String expectedXml = childData.getModulesXml();
		String expectedXmlPrep = prepareXmlString(expectedXml);
		
		String actualXml = childBean.getContent().getValue().getChildNodes().toString();
		String actualXmlPrep = prepareXmlString(actualXml);
		
		assertEquals(expectedXmlPrep, actualXmlPrep);
	}
	
	private String prepareXmlString(String in){
		return in.replaceAll("\\W", "");
	}
}
