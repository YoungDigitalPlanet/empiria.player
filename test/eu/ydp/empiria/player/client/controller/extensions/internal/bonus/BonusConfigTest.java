package eu.ydp.empiria.player.client.controller.extensions.internal.bonus;

import java.util.List;

import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.js.BonusConfigJs;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;

public class BonusConfigTest extends AbstractEmpiriaPlayerGWTTestCase {

	private static final String JSON_CONFIG = ""
			+ "{"
			+"	\"actions\": ["
			+"		{"
			+"			\"type\": \"ON_PAGE_ALL_OK_FIRST_TIME\","
			+"			\"bonuses\": ["
			+"				{"
			+"					\"asset\": \"alex.png\","
			+"					\"type\": \"IMAGE\","
			+"					\"width\": 111,"
			+"					\"height\": 222"
			+"				},"
			+"				{"
			+"					\"asset\": \"animation1\","
			+"					\"type\": \"SWIFFY\","
			+"					\"width\": 333,"
			+"					\"height\": 444"
			+"				}"
			+"			]"
			+"		}"
			+"	]"
			+"}";
	
	private final OverlayTypesParser overlayTypesParser = new OverlayTypesParser();
	private BonusConfigJs bonusConfigJs;
	
	public void testShouldParseBonusConfigFromJs() throws Exception {
		//given
		assertTrue(overlayTypesParser.isValidJSON(JSON_CONFIG));
		bonusConfigJs = overlayTypesParser.get(JSON_CONFIG);

		//when
		BonusConfig bonusConfig = BonusConfig.fromJs(bonusConfigJs);
		
		//then
		assertNotNull(bonusConfig);
		List<BonusAction> actions = bonusConfig.getActions();

		assertCorrectAction(actions);
	}

	private void assertCorrectAction(List<BonusAction> actions) {
		assertNotNull(actions);
		assertEquals(1, actions.size());
		
		BonusAction bonusAction = actions.get(0);
		assertEquals(BonusActionType.ON_PAGE_ALL_OK_FIRST_TIME, bonusAction.getType());

		assertCorrectBonuses(bonusAction.getBonuses());
	}

	private void assertCorrectBonuses(List<BonusResource> bonuses) {
		assertNotNull(bonuses);
		assertEquals(2, bonuses.size());
		
		assertCorrectBonus(bonuses.get(0), "alex.png", BonusResourceType.IMAGE, 111, 222);
		assertCorrectBonus(bonuses.get(1), "animation1", BonusResourceType.SWIFFY, 333, 444);
	}

	private void assertCorrectBonus(BonusResource bonusResource, String asset, BonusResourceType type, int width, int height) {
		assertNotNull(bonusResource);
		assertEquals(asset, bonusResource.getAsset());
		assertEquals(type, bonusResource.getType());
		assertEquals(width, bonusResource.getSize().getWidth());
		assertEquals(height, bonusResource.getSize().getHeight());
	}
}
