package eu.ydp.empiria.player.client.controller.data;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.gwt.xml.client.Element;

@RunWith(MockitoJUnitRunner.class)
public class ElementStyleSelectorBuilderTest {

	@Mock private Element element;
	private final ElementStyleSelectorBuilder instance = new ElementStyleSelectorBuilder();

	@Test
	public void getElementSelector_classAndIdAttributeNoSet() throws Exception {
		String elementName = "eName";
		doReturn(elementName).when(element).getNodeName();
		List<String> selectors = instance.getElementSelectors(element);
		assertThat(selectors).hasSize(1);
		assertThat(selectors).contains(elementName.toLowerCase());
	}

	@Test
	public void getElementSelector_classAttributeSet() throws Exception {
		String elementName = "eName";
		String className = "cName";
		doReturn(elementName).when(element).getNodeName();
		doReturn(className).when(element).getAttribute(eq("class"));
		doReturn(true).when(element).hasAttribute(eq("class"));
		List<String> requiredSelectors = Lists.newArrayList(
				elementName.toLowerCase(),
				"."+className,
				elementName.toLowerCase() + "."+className);

		List<String> selectors = instance.getElementSelectors(element);
		assertThat(selectors).hasSize(3);
		assertThat(selectors).containsAll(requiredSelectors);

	}

	@Test
	public void getElementSelector_classAttributeWithMultipleCssClasses() throws Exception {
		String elementName = "eName";
		List<String> classNames = Lists.newArrayList("cName","cName2","cName3");
		doReturn(elementName).when(element).getNodeName();
		doReturn(Joiner.on(" ").join(classNames)).when(element).getAttribute(eq("class"));
		doReturn(true).when(element).hasAttribute(eq("class"));
		List<String> requiredSelectors = Lists.newArrayList(elementName.toLowerCase());
		for(String className : classNames){
			requiredSelectors.add("."+className);
			requiredSelectors.add(elementName.toLowerCase() + "."+className);
		}

		List<String> selectors = instance.getElementSelectors(element);

		assertThat(selectors).hasSize(7);
		assertThat(selectors).containsAll(requiredSelectors);

	}
	@Test
	public void getElementSelector_IdAttributeSet() throws Exception {
		String elementName = "eName";
		String idValue = "cName";
		doReturn(elementName).when(element).getNodeName();
		doReturn(idValue).when(element).getAttribute(eq("id"));
		doReturn(true).when(element).hasAttribute(eq("id"));
		List<String> requiredSelectors = Lists.newArrayList(
				elementName.toLowerCase(),
				"#"+idValue,
				elementName.toLowerCase() + "#"+idValue);

		List<String> selectors = instance.getElementSelectors(element);
		assertThat(selectors).hasSize(3);
		assertThat(selectors).containsAll(requiredSelectors);

	}

	@Test
	public void getElementSelector_IdAndClassAttributeSet() throws Exception {
		String elementName = "eName";
		String idValue = "idValue";
		String className = "cName";
		doReturn(elementName).when(element).getNodeName();
		doReturn(className).when(element).getAttribute(eq("class"));
		doReturn(true).when(element).hasAttribute(eq("class"));
		doReturn(idValue).when(element).getAttribute(eq("id"));
		doReturn(true).when(element).hasAttribute(eq("id"));

		List<String> selectors = instance.getElementSelectors(element);
		assertThat(selectors).hasSize(5);
		List<String> requiredSelectors = Lists.newArrayList(
				elementName.toLowerCase(),
				"#"+idValue,
				elementName.toLowerCase() + "#"+idValue,
				"."+className,
				elementName.toLowerCase() + "."+className);
		assertThat(selectors).containsAll(requiredSelectors);

	}



}
