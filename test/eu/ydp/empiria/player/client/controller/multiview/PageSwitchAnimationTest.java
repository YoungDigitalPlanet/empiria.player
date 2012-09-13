package eu.ydp.empiria.player.client.controller.multiview;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.ui.FlowPanel;

@SuppressWarnings("PMD")
public class PageSwitchAnimationTest extends GWTTestCase {

	public void testAnimation() {
		final FlowPanel panel = new FlowPanel();
		panel.getElement().getStyle().setPosition(Position.ABSOLUTE);
		PageSwitchAnimation animation = new PageSwitchAnimation(panel, 100, 200){
			@Override
			protected void onComplete() {
				super.onComplete();
				assertEquals("200.0%", panel.getElement().getStyle().getLeft());
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
