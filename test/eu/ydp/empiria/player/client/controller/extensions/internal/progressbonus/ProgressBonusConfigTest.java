package eu.ydp.empiria.player.client.controller.extensions.internal.progressbonus;



import java.util.List;

import org.junit.Test;

import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressAssetConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressBonusConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.js.ProgressBonusConfigJs;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;

public class ProgressBonusConfigTest extends AbstractEmpiriaPlayerGWTTestCase {
	
	private final OverlayTypesParser overlayTypesParser = new OverlayTypesParser();
	
	private static final String JSON_CONFIG = "" +
			"{" +
			"\"progresses\": " +
			"	[" +
			"		{" +
			"			\"from\": 0," +
			"			\"assets\": " +
			"				[" +
			"					{" +
			"						\"asset\": \"report_award_off.png\"," +
			"						\"width\": 111," +
			"						\"height\": 222" +
			"					}" +
			"				]" +
			"		}," +
			"		{" +
			"			\"from\": 100," +
			"			\"assets\": " +
			"				[" +
			"					{" +
			"						\"asset\": \"report_award1_on_%.png\"," +
			"						\"count\": 2," +
			"						\"width\": 333," +
			"						\"height\": 444" +
			"					}," +
			"					{" +
			"						\"asset\": \"report_award2_on_%.png\"," +
			"						\"count\": 3," +
			"						\"width\": 555," +
			"						\"height\": 666" +
			"					}" +
			"				]" +
			"		}" +
			"	]" +
			"}";
	
	
	@Test
	public void test() {
		//		given
		assertTrue(overlayTypesParser.isValidJSON(JSON_CONFIG));
		ProgressBonusConfigJs configJs = overlayTypesParser.get(JSON_CONFIG);

		//		when
		ProgressBonusConfig progressBonusConfig = ProgressBonusConfig.fromJs(configJs);

		//		then
		assertProgresses(progressBonusConfig.getProgresses());
	}


	private void assertProgresses(List<ProgressConfig> progresses) {
		
		ProgressConfig progress1 = progresses.get(0);
		assertEquals(0, progress1.getFrom());
		assertAsset(progress1.getAssets().get(0), "report_award_off.png", 1, 111, 222);
		
		ProgressConfig progress2 = progresses.get(1);
		assertEquals(100, progress2.getFrom());
		assertAsset(progress2.getAssets().get(0), "report_award1_on_%.png", 2, 333, 444);
		assertAsset(progress2.getAssets().get(1), "report_award2_on_%.png", 3, 555, 666);
	}

	private void assertAsset(ProgressAssetConfig asset, String assetPath, int count, int width, int height) {
		assertEquals(assetPath, asset.getPath());
		assertEquals(count, asset.getCount());
		assertEquals(width, asset.getSize().getWidth());
		assertEquals(height, asset.getSize().getHeight());
	}
}
