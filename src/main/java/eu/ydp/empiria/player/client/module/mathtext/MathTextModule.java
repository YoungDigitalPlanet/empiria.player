package eu.ydp.empiria.player.client.module.mathtext;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.mathplayer.player.MathPlayerManager;
import com.mathplayer.player.geom.Font;
import eu.ydp.empiria.player.client.module.base.InlineModuleBase;

public class MathTextModule extends InlineModuleBase {

    private static final byte BASELINE_TO_VERTICAL_ALIGN_FACTOR = -1;
    protected Panel mainPanel;

    @Inject
    private MathTextFontInitializer mathTextFontInitializer;

    @Override
    public void initModule(Element element) {
        createMainPanel();
        MathPlayerManager mpm = new MathPlayerManager();
        initFont(element, mpm);

        boolean temporaryAttached = temporaryAttach();
        mpm.createMath(element.getChildNodes().toString(), mainPanel);
        temporaryDetach(temporaryAttached);

        updateVerticalAlign(mpm);
    }

    private void createMainPanel() {
        mainPanel = new FlowPanel();
        mainPanel.setStyleName("qp-mathtext");
    }

    private void initFont(Element element, MathPlayerManager mpm) {
        Font font = mathTextFontInitializer.initialize(this, getModuleSocket(), element);
        mpm.setFont(font);
    }

    private void updateVerticalAlign(MathPlayerManager mpm) {
        int verticalAlignPx = BASELINE_TO_VERTICAL_ALIGN_FACTOR * mpm.getBaseline();
        mainPanel.getElement().getStyle().setVerticalAlign(verticalAlignPx, Unit.PX);
    }

    private boolean temporaryAttach() {
        if (!mainPanel.isAttached()) {
            RootPanel.get().add(mainPanel);
            return true;
        }
        return false;
    }

    private void temporaryDetach(boolean temporaryAttached) {
        if (temporaryAttached) {
            mainPanel.removeFromParent();
        }
    }

    @Override
    public Widget getView() {
        return mainPanel;
    }
}
