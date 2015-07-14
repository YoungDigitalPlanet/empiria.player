package eu.ydp.empiria.player.client.module.colorfill.structure;

import com.google.inject.Inject;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.Collection;
import java.util.List;

public class ColorfillBeanProxy {

    @Inject
    @ModuleScoped
    private ColorfillInteractionStructure structure;

    public ColorfillInteractionBean getColofillBean() {
        return getBean();
    }

    public List<Area> getAreas() {
        return getBean().getAreas().getAreas();
    }

    public Collection<? extends Area> getFakeAreas() {
        return getBean().getFakeAreas().getAreas();
    }

    private ColorfillInteractionBean getBean() {
        return structure.getBean();
    }
}
