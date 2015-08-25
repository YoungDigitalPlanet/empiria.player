package eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport.mock;

import com.google.common.collect.Lists;
import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.module.item.ProgressToStringRangeMap;

import java.util.List;

public class DataSourceDataSupplierMock implements DataSourceDataSupplier {

    private List<String> itemTitles = Lists.newArrayList();

    private String assessmentTitle;

    public void setItemTitles(List<String> itemTitles) {
        this.itemTitles = itemTitles;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    @Override
    public int getItemsCount() {
        return itemTitles.size();
    }

    @Override
    public String getItemTitle(int itemIndex) {
        return itemTitles.get(itemIndex);
    }

    @Override
    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    @Override
    public Element getItem(int itemIndex) {
        return null;
    }

    @Override
    public ProgressToStringRangeMap getItemFeedbacks(int itemIndex) {
        return null;
    }
}
