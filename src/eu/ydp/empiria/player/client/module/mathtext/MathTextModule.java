package eu.ydp.empiria.player.client.module.mathtext;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.mathplayer.player.MathPlayerManager;
import com.mathplayer.player.geom.Font;

import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.InlineModuleBase;

public class MathTextModule extends InlineModuleBase implements Factory<MathTextModule> {

	protected Panel mainPanel;

	@Override
	public void initModule(Element element) {
		MathTextFontInitializer fontHelper = new MathTextFontInitializer();
		MathPlayerManager mpm = new MathPlayerManager();		
		Font f = fontHelper.initialize(this, getModuleSocket(), element);
		mpm.setFont(f);
		mainPanel = new FlowPanel();
		mainPanel.setStyleName("qp-mathtext");
		boolean temporaryAttached = false;
		if (!mainPanel.isAttached()){
			RootPanel.get().add(mainPanel);
			temporaryAttached = true;
		}
		mpm.createMath(element.getChildNodes().toString(), mainPanel);
		if (temporaryAttached){
			mainPanel.removeFromParent();
		}

	}

	@Override
	public Widget getView() {
		return mainPanel;
	}

	@Override
	public MathTextModule getNewInstance() {
		return new MathTextModule();
	}
}
