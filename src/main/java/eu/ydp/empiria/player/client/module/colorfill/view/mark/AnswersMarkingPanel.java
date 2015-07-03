package eu.ydp.empiria.player.client.module.colorfill.view.mark;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;

import java.util.List;

public class AnswersMarkingPanel extends AbsolutePanel {

    private String markStyle;

    public AnswersMarkingPanel() {
        setSize("100%", "100%");
        hide();
    }

    public void setMarkStyle(String markStyle) {
        this.markStyle = markStyle;
    }

    public void markAndShow(List<Area> points) {
        markPoints(points);
        show();
    }

    public void clearAndHide() {
        clear();
        hide();
    }

    public void markPoints(List<Area> points) {
        for (Area point : points) {
            addMarkingPoint(point);
        }
    }

    public void show() {
        setVisible(true);
    }

    public void hide() {
        setVisible(false);
    }

    private void addMarkingPoint(Area point) {
        FlowPanel pointPanel = new FlowPanel();
        pointPanel.setStyleName(markStyle);
        add(pointPanel, point.getX(), point.getY());
    }

}
