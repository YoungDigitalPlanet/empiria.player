package eu.ydp.empiria.player.client.module.simple.mathtext;

import java.util.Map;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.mathplayer.player.MathPlayerManager;
import com.mathplayer.player.geom.Font;

import eu.ydp.empiria.player.client.module.IInlineModule;
import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;
import eu.ydp.empiria.player.client.util.IntegerUtils;

public class MathTextModule implements IInlineModule {

	protected Panel mainPanel;
	
	@Override
	public void initModule(Element element, ModuleSocket ms) {
		MathPlayerManager mpm = new MathPlayerManager();
		Map<String, String> styles = ms.getStyles(element);
		int fontSize = 16;
		String fontName = "Arial";
		boolean fontBold = false;
		boolean fontItalic = false;
		if (styles.containsKey("-empiria-math-font-size")){			
			fontSize = IntegerUtils.tryParseInt(styles.get("-empiria-math-font-size"));
		}
		if (styles.containsKey("-empiria-math-font-family")){			
			fontName = styles.get("-empiria-math-font-family");
		}
		if (styles.containsKey("-empiria-math-font-weight")){			
			fontBold = styles.get("-empiria-math-font-weight").toLowerCase().equals("bold");
		}
		if (styles.containsKey("-empiria-math-font-style")){			
			fontItalic = styles.get("-empiria-math-font-style").toLowerCase().equals("italic");
		}
		Font f = new Font(fontSize, fontName, fontBold, fontItalic);
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


}
