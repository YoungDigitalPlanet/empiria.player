package eu.ydp.empiria.player.client.module.object;

import static org.mockito.Mockito.*;

import java.util.Map;

import com.google.common.collect.Maps;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

class MockElementFluentBuilder {
	private static final NodeList EMPTY_LIST;
	
	static {
		EMPTY_LIST = mock(NodeList.class);
		when(EMPTY_LIST.getLength()).thenReturn(0);
	}
	
	private Short nodeType;
	private Optional<String> value = Optional.absent();
	private Optional<NodeList> children = Optional.absent();
	private Optional<String> nodeTag = Optional.absent();
	private final Map<String, String> attributes = Maps.newHashMap();
	private final Map<String, NodeList> tagChildren = Maps.newHashMap();

	private MockElementFluentBuilder() {}

	public static MockElementFluentBuilder newNode() {
		return new MockElementFluentBuilder().ofType(Node.ELEMENT_NODE);
	}

	public static MockElementFluentBuilder newText(String text) {
		return new MockElementFluentBuilder().ofType(Node.TEXT_NODE).withValue(text);
	}

	public MockElementFluentBuilder ofType(short nodeType) {
		this.nodeType = nodeType;
		return this;
	}

	public MockElementFluentBuilder withTag(String nodeTag) {
		this.nodeTag = Optional.of(nodeTag);
		
		return this;
	}
	
	public MockElementFluentBuilder withAttribute(String attributeName, String attributeValue) {
		this.attributes.put(attributeName, attributeValue);
		return this;
	}

	public MockElementFluentBuilder withValue(String value) {
		this.value = Optional.of(value);
		return this;
	}

	public MockElementFluentBuilder withChildren(Element... children) {
		NodeList nodeList = buildNodeListFromElements(children);
		this.children = Optional.of(nodeList);
		
		return this;
	}

	public MockElementFluentBuilder withChildrenTags(String tag, Element... children) {
		NodeList nodeList = buildNodeListFromElements(children);
		tagChildren.put(tag, nodeList);

		return this;
	}

	private NodeList buildNodeListFromElements(Element... children) {
		NodeList nodeList = mock(NodeList.class);

		for (int i = 0; i < children.length; i++) {
			when(nodeList.item(i)).thenReturn(children[i]);
		}

		when(nodeList.getLength()).thenReturn(children.length);
		return nodeList;
	}

	public Element build() {
		Element result = mock(Element.class);

		when(result.getNodeType()).thenReturn(nodeType);
		
		if (value.isPresent()) {
			when(result.getNodeValue()).thenReturn(value.get());
		}

		if (nodeTag.isPresent()) {
			when(result.getTagName()).thenReturn(nodeTag.get());
		}
		
		if (children.isPresent()) {
			when(result.getChildNodes()).thenReturn(children.get());
		} else {
			when(result.getChildNodes()).thenReturn(EMPTY_LIST);
		}

		for (Map.Entry<String, String> attr : attributes.entrySet()) {
			when(result.getAttribute(attr.getKey())).thenReturn(attr.getValue());
		}

		for (Map.Entry<String, NodeList> tC : tagChildren.entrySet()) {
			when(result.getElementsByTagName(tC.getKey())).thenReturn(tC.getValue());
		}

		return result;
	}

}