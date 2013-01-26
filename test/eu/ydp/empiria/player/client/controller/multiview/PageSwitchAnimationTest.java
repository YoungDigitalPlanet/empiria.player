package eu.ydp.empiria.player.client.controller.multiview;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.FlowPanel;

import eu.ydp.empiria.player.client.controller.multiview.animation.PageSwitchAnimation;

@SuppressWarnings("PMD")
public class PageSwitchAnimationTest extends GWTTestCase {

	public void testAnimation() {
		final FlowPanel panel = new FlowPanel();
		panel.getElement().getStyle().setPosition(Position.ABSOLUTE);
		panel.getElement().getStyle().setLeft(100, Unit.PCT);
		PageSwitchAnimation animation = new PageSwitchAnimation(panel, 200){
			@Override
			protected void onComplete() {
				super.onComplete();
				assertEquals("200px", panel.getElement().getStyle().getLeft());
				finishTest();
			};
		};
		delayTestFinish(500);
		animation.run(50);
	}

	@Override
	public String getModuleName() {
		return "eu.ydp.empiria.player.Player";
	}

}
