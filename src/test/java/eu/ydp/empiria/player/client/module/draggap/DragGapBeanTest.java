package eu.ydp.empiria.player.client.module.draggap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import eu.ydp.empiria.player.client.AbstractJAXBTestBase;
import eu.ydp.empiria.player.client.module.draggap.structure.DragGapBean;
import eu.ydp.gwtutil.client.StringUtils;

public class DragGapBeanTest extends AbstractJAXBTestBase<DragGapBean> {

	@Test
	public void shouldReturnDragGap() {
		// given
		String emptyXmlString = "<dragInteraction/>";

		// when
		DragGapBean dragGapBean = createBeanFromXMLString(emptyXmlString);

		// then
		assertThat(dragGapBean.getId(), equalTo(StringUtils.EMPTY_STRING));
		assertThat(dragGapBean.getName(), equalTo(StringUtils.EMPTY_STRING));
		assertThat(dragGapBean.getExpressionMode(), equalTo(StringUtils.EMPTY_STRING));
		assertThat(dragGapBean.getWidthBindingGroup(), equalTo(StringUtils.EMPTY_STRING));
		assertThat(dragGapBean.getResponseIdentifier(), equalTo(StringUtils.EMPTY_STRING));
	}

	@Test
	public void shouldReturnDragGapWithEmptyValues() {
		// given
		StringBuilder fullXmlStringBuilder = new StringBuilder();
		fullXmlStringBuilder.append("<dragInteraction ");
		fullXmlStringBuilder.append("id=\"id1\" ");
		fullXmlStringBuilder.append("name=\"name1\" ");
		fullXmlStringBuilder.append("expressionMode=\"expressionMode1\" ");
		fullXmlStringBuilder.append("widthBindingGroup=\"widthBindingGroup1\" ");
		fullXmlStringBuilder.append("responseIdentifier=\"responseIdentifier1\" ");
		fullXmlStringBuilder.append("sourcelistId=\"sourceListIdentifier1\">");
		fullXmlStringBuilder.append("</dragInteraction>");

		// when
		DragGapBean dragGapBean = createBeanFromXMLString(fullXmlStringBuilder.toString());

		// then
		assertThat(dragGapBean.getId(), equalTo("id1"));
		assertThat(dragGapBean.getName(), equalTo("name1"));
		assertThat(dragGapBean.getExpressionMode(), equalTo("expressionMode1"));
		assertThat(dragGapBean.getWidthBindingGroup(), equalTo("widthBindingGroup1"));
		assertThat(dragGapBean.getResponseIdentifier(), equalTo("responseIdentifier1"));
		assertThat(dragGapBean.getSourcelistId(), equalTo("sourceListIdentifier1"));
	}

}
