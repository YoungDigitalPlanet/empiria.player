package eu.ydp.empiria.player.client.module.bonus;

import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusResource;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusResourceType;
import eu.ydp.gwtutil.client.util.geom.Size;

public class BonusFactory {

    @Inject
    private Provider<ImageBonus> imageProvider;
    @Inject
    private Provider<SwiffyBonus> swiffyProvider;

    public BonusWithAsset createBonus(BonusResource bonusResource) {
        BonusWithAsset bonus = factorizeBonus(bonusResource.getType());
        updateBonusAsset(bonusResource, bonus);
        return bonus;
    }

    private void updateBonusAsset(BonusResource bonusResource, BonusWithAsset bonus) {
        String asset = bonusResource.getAsset();
        Size size = bonusResource.getSize();
        bonus.setAsset(asset, size);
    }

    private BonusWithAsset factorizeBonus(BonusResourceType type) {
        switch (type) {
            case IMAGE:
                return imageProvider.get();
            case SWIFFY:
                return swiffyProvider.get();
            default:
                throw new IllegalArgumentException("Unsupported BonusResource type.");
        }
    }
}
