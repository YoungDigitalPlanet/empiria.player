package eu.ydp.empiria.player.client.module.colorfill.presenter.compare;

import eu.ydp.empiria.player.client.module.colorfill.structure.Area;

public class AreasMapComparationResult {

    public static AreasMapComparationResult ofAddedOrChanged(Area area) {
        AreasMapComparationResult result = new AreasMapComparationResult();
        result.area = area;
        result.addedOrChanged = true;
        return result;
    }

    public static AreasMapComparationResult ofRemoved(Area area) {
        AreasMapComparationResult result = new AreasMapComparationResult();
        result.area = area;
        result.addedOrChanged = false;
        return result;
    }

    public static AreasMapComparationResult ofSame() {
        return new AreasMapComparationResult();
    }

    private Area area;
    private boolean addedOrChanged;

    private AreasMapComparationResult() {
    }

    public Area getArea() {
        return area;
    }

    public boolean isDifference() {
        return area != null;
    }

    public boolean isAddedOrChanged() {
        return addedOrChanged;
    }
}
