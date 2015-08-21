package eu.ydp.empiria.player.client.gin.module;

import com.google.inject.TypeLiteral;
import eu.ydp.empiria.player.client.module.math.MathGapModel;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopedProvider;

public class MathGinModule extends BaseGinModule {

    @Override
    protected void configure() {
        bindModuleScoped(MathGapModel.class, new TypeLiteral<ModuleScopedProvider<MathGapModel>>() {
        });
    }
}
