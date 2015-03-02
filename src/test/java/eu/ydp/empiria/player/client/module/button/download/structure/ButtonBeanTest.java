package eu.ydp.empiria.player.client.module.button.download.structure;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import eu.ydp.empiria.player.client.AbstractJAXBTestBase;

public class ButtonBeanTest extends AbstractJAXBTestBase<ButtonBean> {
	private static final String HREF = "media/image.png";
	private static final String ALT = "Link to image file";
	private static final String ID = "dummy1";
	String xml = "<button alt=\"" + ALT + "\" href=\"" + HREF + "\" id=\"" + ID + "\"/>";

	@Test
	public void beanTest() {
		ButtonBean buttonBean = createBeanFromXMLString(xml);
		assertThat(buttonBean.getId()).isEqualTo(ID);
		assertThat(buttonBean.getAlt()).isEqualTo(ALT);
		assertThat(buttonBean.getHref()).isEqualTo(HREF);
	}

}
