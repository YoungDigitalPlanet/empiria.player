package eu.ydp.empiria.player.client.module.colorfill.presenter;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Lists;

import static org.fest.assertions.api.Assertions.*;

import static org.junit.Assert.*;

import eu.ydp.empiria.player.client.module.colorfill.model.ColorModel;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;



public class ResponseUserAnswersConverterTest {

	private final ResponseUserAnswersConverter responseUserAnswersConverter = new ResponseUserAnswersConverter();
	
	@Test
	public void shouldParseUserAnswersToAreaColorMap() throws Exception {
		List<String> currentAnswers = Lists.newArrayList("2,3 ff00ff", "10,30 00ff00");
		
		Map<Area, ColorModel> areaColorMap = responseUserAnswersConverter.convertResponseAnswersToAreaColorMap(currentAnswers);
		
		assertEquals(2, areaColorMap.size());
		
		ColorModel colorModel = areaColorMap.get(new Area(2, 3));
		assertEquals(ColorModel.createFromRgbString("ff00ff"), colorModel);
		
		ColorModel secondColorModel = areaColorMap.get(new Area(10, 30));
		assertEquals(ColorModel.createFromRgbString("00ff00"), secondColorModel);
	}
	
	@Test
	public void shouldBuildResponseAnswerFromColorAndArea() throws Exception {
		Area area = new Area(123, 654);
		ColorModel colorModel = ColorModel.createFromRgbString("00ff00");
		
		String responseAnswer = responseUserAnswersConverter.buildResponseAnswerFromAreaAndColor(area, colorModel);
		
		assertThat(responseAnswer).isEqualTo("123,654 00ff00");
	}
	
}
