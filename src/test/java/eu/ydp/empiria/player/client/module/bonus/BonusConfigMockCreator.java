package eu.ydp.empiria.player.client.module.bonus;

import static eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusActionType.ON_PAGE_ALL_OK_FIRST_TIME;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusAction;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusResource;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusResourceType;
import eu.ydp.gwtutil.client.util.geom.Size;

public class BonusConfigMockCreator {

	public static BonusAction createMockAction(List<BonusResource> resources) {
		BonusAction action = mock(BonusAction.class);
		when(action.getType()).thenReturn(ON_PAGE_ALL_OK_FIRST_TIME);
		when(action.getBonuses()).thenReturn(resources);
		return action;
	}

	public static BonusResource createBonus(String url, Size size, BonusResourceType resourceType) {
		BonusResource resource = mock(BonusResource.class);
		when(resource.getAsset()).thenReturn(url);
		when(resource.getSize()).thenReturn(size);
		when(resource.getType()).thenReturn(resourceType);
		return resource;
	}
}
