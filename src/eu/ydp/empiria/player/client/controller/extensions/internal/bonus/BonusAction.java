package eu.ydp.empiria.player.client.controller.extensions.internal.bonus;

import static eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusActionType.ON_PAGE_ALL_OK_FIRST_TIME;

import java.util.List;

import com.google.common.collect.Lists;

public class BonusAction {

	public BonusActionType getType() {
		return ON_PAGE_ALL_OK_FIRST_TIME;
	}
	
	public List<BonusResource> getBonuses(){
		return Lists.newArrayList();
	}
}
