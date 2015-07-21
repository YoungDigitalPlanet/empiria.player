package eu.ydp.empiria.player.client.module.img.template;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGenerator;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.img.explorable.ExplorableImgContent;
import eu.ydp.empiria.player.client.module.img.ImgContent;
import eu.ydp.empiria.player.client.module.img.LabelledImgContent;
import eu.ydp.empiria.player.client.module.img.picture.player.PicturePlayerModule;
import eu.ydp.empiria.player.client.module.media.button.MediaController;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.AbstractTemplateParser;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_IMG_MODE;

public class ImgTemplateParser extends AbstractTemplateParser {
    protected final static Set<String> CONTROLLERS = new HashSet<>();
    private final Element baseElement;
    private final ModuleSocket moduleSocket;

    @Inject
    private Provider<PicturePlayerModule> defaultImgContentProvider;
    @Inject
    private Provider<ExplorableImgContent> explorableImgContentProvider;
    @Inject
    private StyleSocket styleSocket;
    @Inject
    private Provider<LabelledImgContent> labelledImgContentProvider;

    @Inject
    public ImgTemplateParser(@Assisted Element baseElement, @Assisted ModuleSocket moduleSocket) {
        this.baseElement = baseElement;
        this.moduleSocket = moduleSocket;
        if (CONTROLLERS.isEmpty()) {
            CONTROLLERS.add(ModuleTagName.MEDIA_TITLE.tagName());
            CONTROLLERS.add(ModuleTagName.MEDIA_DESCRIPTION.tagName());
            CONTROLLERS.add(ModuleTagName.MEDIA_FULL_SCREEN_BUTTON.tagName());
            CONTROLLERS.add(ModuleTagName.MEDIA_SCREEN.tagName());
        }
    }

    @Override
    public void beforeParse(Node mainNode, Widget parent) {
        //
    }

    @Override
    protected MediaController getMediaControllerNewInstance(String moduleName, Node node) {
        MediaController controller = null;
        if (isModuleSupported(moduleName)) {
            ModuleTagName tagName = ModuleTagName.getTag(moduleName);
            switch (tagName) {
                case MEDIA_TITLE:
                    controller = createWrapper(ModuleTagName.MEDIA_TITLE.tagName());
                    break;
                case MEDIA_SCREEN:
                    controller = createScreen();
                    break;
                case MEDIA_DESCRIPTION:
                    controller = createWrapper(ModuleTagName.MEDIA_DESCRIPTION.tagName());
                    break;
                default:
                    break;
            }
        }
        return controller;
    }

    /**
     * tworzy widget na podstawie wezlow xml poprzez {@link InlineBodyGenerator}
     *
     * @param elementName
     * @return
     */
    private MediaController createWrapper(String elementName) {
        MediaController moduleWrapper = null;
        if (isNodeHaveChildren(elementName)) {
            moduleWrapper = createModuleWrapper(elementName);
        } else {
            moduleWrapper = createEmptyModuleWrapper();
        }
        return moduleWrapper;
    }

    protected MediaController createEmptyModuleWrapper() {
        return new ModuleWrapper(new FlowPanel());
    }

    protected MediaController createModuleWrapperForWidget(IsWidget widget) {
        return new ModuleWrapper(widget);
    }

    private MediaController createModuleWrapper(String elementName) {
        MediaController moduleWrapper = null;
        Widget widget = generateInlineBody(elementName);
        if (widget == null) {
            moduleWrapper = createEmptyModuleWrapper();
        } else {
            moduleWrapper = createModuleWrapperForWidget(widget);
        }
        return moduleWrapper;
    }

    private Widget generateInlineBody(String elementName) {
        NodeList titleNodes = baseElement.getElementsByTagName(elementName);
        return moduleSocket.getInlineBodyGeneratorSocket().generateInlineBody(titleNodes.item(0));
    }

    private boolean isNodeHaveChildren(String elementName) {
        NodeList titleNodes = baseElement.getElementsByTagName(elementName);
        return titleNodes.getLength() > 0;
    }

    /**
     * Tworzy obiekt img + labele
     *
     * @return
     */
    private MediaController createScreen() {
        ImgContent content;
        if (isLabelledImgContent()) {
            content = createLabelledImgContent();
        } else if (isExplorableImgContent()) {
            content = createExplorableImgContent();
        } else {
            content = createDefaultImgContent();
        }
        return initContentAndCreateModuleWrapper(content);
    }

    protected ImgContent createExplorableImgContent() {
        return explorableImgContentProvider.get();
    }

    protected ImgContent createLabelledImgContent() {
        return labelledImgContentProvider.get();
    }

    protected ImgContent createDefaultImgContent() {
        ImgContent content = defaultImgContentProvider.get();
        ((PicturePlayerModule) content).setTemplate(true);
        return content;
    }

    private MediaController initContentAndCreateModuleWrapper(ImgContent content) {
        content.init(baseElement, moduleSocket);
        return createModuleWrapperForWidget(content);
    }

    private boolean isExplorableImgContent() {
        Map<String, String> styles = styleSocket.getStyles(baseElement);
        return "explorable".equalsIgnoreCase(styles.get(EMPIRIA_IMG_MODE));
    }

    private boolean isLabelledImgContent() {
        return isNodeHaveChildren("label");
    }

    private boolean isFullScreenSupported() {
        return baseElement.hasAttribute("srcFullScreen") && !baseElement.getAttribute("srcFullScreen").trim().isEmpty();
    }

    @Override
    protected boolean isModuleSupported(String moduleName) {
        boolean supported = CONTROLLERS.contains(moduleName);
        if (supported && isFullScreenButtonModule(moduleName)) {
            supported = isFullScreenSupported();
        }
        return supported;
    }

    private boolean isFullScreenButtonModule(String moduleName) {
        return ModuleTagName.getTag(moduleName) == ModuleTagName.MEDIA_FULL_SCREEN_BUTTON;
    }

}
