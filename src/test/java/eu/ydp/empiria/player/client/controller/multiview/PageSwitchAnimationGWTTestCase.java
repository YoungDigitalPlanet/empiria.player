package eu.ydp.empiria.player.client.controller.multiview;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import eu.ydp.empiria.player.RunOutsideTestSuite;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.controller.multiview.animation.PageSwitchAnimation;

@RunOutsideTestSuite
public class PageSwitchAnimationGWTTestCase extends EmpiriaPlayerGWTTestCase {

    public void testAnimation() {
        final FlowPanel panel = new FlowPanel();
        panel.getElement().getStyle().setPosition(Position.ABSOLUTE);
        panel.getElement().getStyle().setLeft(100, Unit.PCT);
        PageSwitchAnimation animation = new PageSwitchAnimation(panel, 200) {
            @Override
            protected void onComplete() {
                super.onComplete();
                assertEquals("200.0%", panel.getElement().getStyle().getLeft());
                finishTest();
            }
        };
        delayTestFinish(500);
        animation.run(50);
    }
}
