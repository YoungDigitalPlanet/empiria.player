package eu.ydp.empiria.player.client.module.colorfill.fill;

import static org.fest.assertions.api.Assertions.assertThat;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

import eu.ydp.empiria.player.client.module.model.color.ColorModel;

@RunWith(JUnitParamsRunner.class)
@SuppressWarnings("PMD")
public class BlackColorContourDetectorJUnitTest {

	BlackColorContourDetector instance = new BlackColorContourDetector();

	@Test
	@Parameters({ "255,255,255,255,false", "123,123,45,0,false", "0,0,0,0,false", "0,0,0,255,true" })
	public void isContourColor(final int red, final int green, final int blue, final int alpha, final boolean assertResult) throws Exception {
		ColorModel rgba = ColorModel.createFromRgba(red, green, blue, alpha);
		assertThat(instance.isContourColor(rgba)).isEqualTo(assertResult);
	}

}
