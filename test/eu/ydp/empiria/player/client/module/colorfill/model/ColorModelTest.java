package eu.ydp.empiria.player.client.module.colorfill.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;

import org.junit.Test;

public class ColorModelTest {
	
	private ColorModel colorModel;  

	@Test
	public void intsToStringRgb() {
		// when
		colorModel = ColorModel.createFromRgba(20, 30, 40, 50);
		
		// then
		assertThat(colorModel.toStringRgb(), equalToIgnoringCase("141E28"));
	}

	@Test
	public void intsToStringRgba() {
		// when
		colorModel = ColorModel.createFromRgba(20, 30, 40, 50);
		
		// then
		assertThat(colorModel.toStringRgba(), equalToIgnoringCase("141E2832"));
	}

	@Test
	public void stringToStringRgb() {
		// when
		colorModel = ColorModel.createFromRgbString("141e28");
		
		// then
		assertThat(colorModel.toStringRgb(), equalToIgnoringCase("141E28"));
	}

	@Test
	public void stringToStringRgba() {
		// when
		colorModel = ColorModel.createFromRgbString("141e28");
		
		// then
		assertThat(colorModel.toStringRgba(), equalToIgnoringCase("141E28ff"));
	}

	@Test
	public void eraser() {
		// when
		colorModel = ColorModel.createEraser();
		
		// then
		assertThat(colorModel.toStringRgba(), equalToIgnoringCase("00000000"));
	}

}
