package eu.ydp.empiria.player.client.module.slideshow.view.pager;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class SlideshowPagerViewImpl extends Composite implements SlideshowPagerView {

    @UiTemplate("SlideshowPagerView.ui.xml")
    interface SlideshowPagerUiBinder extends UiBinder<Widget, SlideshowPagerViewImpl> {
    }

    ;

    private final SlideshowPagerUiBinder uiBinder = GWT.create(SlideshowPagerUiBinder.class);

    @UiField
    FlowPanel pagerPanel;

    public SlideshowPagerViewImpl() {
        uiBinder.createAndBindUi(this);
        initWidget(pagerPanel);
    }

    @Override
    public void addPager(Widget widget) {
        pagerPanel.add(widget);
    }
}
