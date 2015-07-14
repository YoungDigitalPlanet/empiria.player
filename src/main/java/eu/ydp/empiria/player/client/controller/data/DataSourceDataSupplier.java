package eu.ydp.empiria.player.client.controller.data;

import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.module.item.ProgressToStringRangeMap;

public interface DataSourceDataSupplier {
    int getItemsCount();

    String getItemTitle(int itemIndex);

    String getAssessmentTitle();

    /**
     * Zwraca wezel assessmentItemRef o wskazanym id
     *
     * @param index index wezla
     * @return Element lub null gdy element o podanym indeksie nie istnieje
     */
    Element getItem(int itemIndex);

    ProgressToStringRangeMap getItemFeedbacks(int itemIndex);
}
