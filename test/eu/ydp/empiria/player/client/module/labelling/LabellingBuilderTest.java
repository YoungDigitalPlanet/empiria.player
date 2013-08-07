package eu.ydp.empiria.player.client.module.labelling;

import static eu.ydp.empiria.player.client.module.labelling.LabellingTestAssets.CHILDREN_FULL;
import static eu.ydp.empiria.player.client.module.labelling.LabellingTestAssets.CHILDREN_FULL_X;
import static eu.ydp.empiria.player.client.module.labelling.LabellingTestAssets.CHILDREN_FULL_Y;
import static eu.ydp.empiria.player.client.module.labelling.LabellingTestAssets.ID;
import static eu.ydp.empiria.player.client.module.labelling.LabellingTestAssets.IMAGE_BEAN;
import static eu.ydp.empiria.player.client.module.labelling.LabellingTestAssets.XML_FULL;
import static eu.ydp.empiria.player.client.module.labelling.LabellingTestAssets.XML_NO_CHILDREN;
import static eu.ydp.empiria.player.client.module.labelling.LabellingTestAssets.XML_NO_CHILDREN_NODE;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.xml.sax.SAXException;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HasWidgets.ForIsWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;

import eu.ydp.empiria.player.client.AbstractTestWithMocksBase;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.labelling.structure.LabellingModuleJAXBParserFactory;
import eu.ydp.empiria.player.client.module.labelling.view.LabellingView;
import eu.ydp.gwtutil.xml.XMLParser;

public class LabellingBuilderTest extends AbstractTestWithMocksBase {

	private LabellingBuilder builder;
	private LabellingView view;
	private LabellingModuleJAXBParserFactory parserFactory;

	@Override
	public void setUp() {
		super.setUp(LabellingBuilder.class, LabellingViewBuilder.class);

		XMLUnit.setIgnoreWhitespace(true);

		builder = injector.getInstance(LabellingBuilder.class);

		view = injector.getInstance(LabellingView.class);
		stub(view.getContainer()).toReturn(mock(ForIsWidget.class));

		parserFactory = injector.getInstance(LabellingModuleJAXBParserFactory.class);
		stub(parserFactory.create()).toReturn(new LabellingInteractionBeanMockParser(CHILDREN_FULL));
	}

	@Test
	public void test() throws SAXException, IOException {
		// given
		BodyGeneratorSocket bgs = mock(BodyGeneratorSocket.class);
		Element element = XMLParser.parse(XML_FULL).getDocumentElement();

		// when
		LabellingView view = builder.build(element, bgs);

		// then
		verify(view).setBackground(eq(IMAGE_BEAN));
		verify(view).setViewId(eq(ID));
		verifyChildrenPositioning(view, CHILDREN_FULL);
		verifyChildrenGeneration(bgs, CHILDREN_FULL);

	}

	@Test
	public void test_noChildren() throws SAXException, IOException {
		// given
		BodyGeneratorSocket bgs = mock(BodyGeneratorSocket.class);
		Element element = XMLParser.parse(XML_NO_CHILDREN).getDocumentElement();

		// when
		LabellingView view = builder.build(element, bgs);

		// then
		verify(view).setBackground(eq(IMAGE_BEAN));
		verify(view, never()).addChild(any(IsWidget.class), anyInt(), anyInt());

	}

	@Test
	public void test_noChildrenNode() throws SAXException, IOException {
		// given
		BodyGeneratorSocket bgs = mock(BodyGeneratorSocket.class);
		Element element = XMLParser.parse(XML_NO_CHILDREN_NODE).getDocumentElement();

		// when
		LabellingView view = builder.build(element, bgs);

		// then
		verify(view).setBackground(eq(IMAGE_BEAN));
		verify(view, never()).addChild(any(IsWidget.class), anyInt(), anyInt());

	}

	private void verifyChildrenPositioning(LabellingView view, List<ChildData> children) {
		ArgumentCaptor<Integer> leftCaptor = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> topCaptor = ArgumentCaptor.forClass(Integer.class);

		verify(view, times(children.size())).addChild(any(IsWidget.class), leftCaptor.capture(), topCaptor.capture());

		assertThat(leftCaptor.getAllValues(), equalTo(CHILDREN_FULL_X));
		assertThat(topCaptor.getAllValues(), equalTo(CHILDREN_FULL_Y));
	}

	private void verifyChildrenGeneration(BodyGeneratorSocket bgs, List<ChildData> children) throws SAXException, IOException {
		int childrenCount = children.size();

		ArgumentCaptor<Node> nodeCaptor = ArgumentCaptor.forClass(Node.class);
		verify(bgs, times(childrenCount)).generateBody(nodeCaptor.capture(), any(HasWidgets.class));

		Iterator<Node> iterator = nodeCaptor.getAllValues().iterator();
		for (ChildData child : children){
			assertXMLEqual(child.xml(), iterator.next().toString());
		}
	}


}
