package eu.ydp.empiria.player.client.module.labelling.structure;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.xml.client.Element;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;

import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;

public class LabellingModuleJAXBParserTest extends AbstractEmpiriaPlayerGWTTestCase {

	private static final ChildData CHILD_0 = new ChildData(0, 0, "<node/>");
	private static final ChildData CHILD_1 = new ChildData(10, 20, "<node><innerNode/>innerText</node>");
	private static final ChildData CHILD_2 = new ChildData(-10, -20, "");
	private static final String IMAGE_SRC = "image.png";
	private static final int IMAGE_WIDTH = 400;
	private static final int IMAGE_HEIGHT = 300;
	
	private static final String FULL_XML = "<labellingInteraction>" +
			"<img src='" + IMAGE_SRC + "' width='" + IMAGE_WIDTH + "' height='"+IMAGE_HEIGHT+"'/>" +
			"<children>" +
			"<child x='" + CHILD_0.getX() + "' y='" + CHILD_0.getX() + "'>"+CHILD_0.getXml()+"</child>" +
			"<child x='" + CHILD_1.getX() + "' y='" + CHILD_1.getY() + "'>"+CHILD_1.getXml()+"</child>" +
			"<child x='" + CHILD_2.getX() + "' y='" + CHILD_2.getY() + "'>"+CHILD_2.getXml()+"</child>" +
			"</children>" +
			"</labellingInteraction>";
	
	private static final String FULL_NO_CHILDREN = "<labellingInteraction>" +
			"<img src='" + IMAGE_SRC + "' width='" + IMAGE_WIDTH + "' height='"+IMAGE_HEIGHT+"'/>" +
			"<children>" +
			"</children>" +
			"</labellingInteraction>";

	public void testFull() {
		LabellingInteractionBean labellingBean = parse(FULL_XML);
		
		assertEquals(IMAGE_SRC, labellingBean.getImg().getSrc());
		assertEquals(IMAGE_WIDTH, labellingBean.getImg().getWidth());
		assertEquals(IMAGE_HEIGHT, labellingBean.getImg().getHeight());
		
		assertChildren(labellingBean.getChildren().getChildren(), CHILD_0, CHILD_1, CHILD_2);		
	}

	public void testNoChildren() {
		LabellingInteractionBean labellingBean = parse(FULL_NO_CHILDREN);
		
		assertEquals(IMAGE_SRC, labellingBean.getImg().getSrc());
		assertEquals(IMAGE_WIDTH, labellingBean.getImg().getWidth());
		assertEquals(IMAGE_HEIGHT, labellingBean.getImg().getHeight());
		
		assertChildren(labellingBean.getChildren().getChildren());		
	}

	private LabellingInteractionBean parse(String xml) {
		LabellingModuleJAXBParser jaxbParserFactory = GWT.create(LabellingModuleJAXBParser.class);
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
		Element childElement = childBean.getContent().getValue();
		if (childData.getXml().isEmpty()){
			assertEquals(0, childElement.getChildNodes().getLength());
		} else {
			assertEquals(childData.getXml(), childElement.getFirstChild().toString());
		}
	}

	private static class ChildData{
		private int x;
		private int y;
		private String xml;
		public ChildData(int x, int y, String xml) {
			super();
			this.x = x;
			this.y = y;
			this.xml = xml;
		}
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
		public String getXml() {
			return xml;
		}
		
	}
}
