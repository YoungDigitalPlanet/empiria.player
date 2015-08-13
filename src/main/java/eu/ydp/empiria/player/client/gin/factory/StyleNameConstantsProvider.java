package eu.ydp.empiria.player.client.gin.factory;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickieStyleNameConstants;
import eu.ydp.empiria.player.client.controller.workmode.ModeStyleNameConstants;
import eu.ydp.empiria.player.client.module.button.ButtonStyleNameConstants;
import eu.ydp.empiria.player.client.module.colorfill.ColorfillStyleNameConstants;
import eu.ydp.empiria.player.client.module.connection.ConnectionStyleNameConstants;
import eu.ydp.empiria.player.client.module.drawing.DrawingStyleNameConstants;
import eu.ydp.empiria.player.client.module.img.ImgStyleNameConstants;
import eu.ydp.empiria.player.client.module.inlinechoice.InlineChoiceStyleNameConstants;
import eu.ydp.empiria.player.client.module.labelling.LabellingStyleNameConstants;
import eu.ydp.empiria.player.client.module.media.MediaStyleNameConstants;
import eu.ydp.empiria.player.client.module.textentry.TextEntryStyleNameConstants;
import eu.ydp.empiria.player.client.resources.PageStyleNameConstants;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.junit.mock.GWTConstantsMock;

import static org.mockito.Mockito.mock;

public class StyleNameConstantsProvider implements Module {

    @Provides
    @Singleton
    public StyleNameConstants getNameConstants() {
        return GWTConstantsMock.mockAllStringMethods(mock(StyleNameConstants.class), StyleNameConstants.class);
    }

    @Provides
    @Singleton
    public ConnectionStyleNameConstants getConnectionStyleNameConstants() {
        return GWTConstantsMock.mockAllStringMethods(mock(ConnectionStyleNameConstants.class), ConnectionStyleNameConstants.class);
    }

    @Provides
    @Singleton
    public ColorfillStyleNameConstants getColorfillStyleNameConstants() {
        return GWTConstantsMock.mockAllStringMethods(mock(ColorfillStyleNameConstants.class), ColorfillStyleNameConstants.class);
    }

    @Provides
    @Singleton
    public ImgStyleNameConstants getImgStyleNameConstants() {
        return GWTConstantsMock.mockAllStringMethods(mock(ImgStyleNameConstants.class), ImgStyleNameConstants.class);
    }

    @Provides
    @Singleton
    public MediaStyleNameConstants getMediaStyleNameConstants() {
        return GWTConstantsMock.mockAllStringMethods(mock(MediaStyleNameConstants.class), MediaStyleNameConstants.class);
    }

    @Provides
    @Singleton
    public InlineChoiceStyleNameConstants getInlineChoiceStyleNameConstants() {
        return GWTConstantsMock.mockAllStringMethods(mock(InlineChoiceStyleNameConstants.class), InlineChoiceStyleNameConstants.class);
    }

    @Provides
    @Singleton
    public LabellingStyleNameConstants getLabellingNameConstants() {
        return GWTConstantsMock.mockAllStringMethods(mock(LabellingStyleNameConstants.class), LabellingStyleNameConstants.class);
    }

    @Provides
    @Singleton
    public DrawingStyleNameConstants getDrawingStyleNameConstants() {
        return GWTConstantsMock.mockAllStringMethods(mock(DrawingStyleNameConstants.class), DrawingStyleNameConstants.class);
    }

    @Provides
    @Singleton
    public StickieStyleNameConstants getStickieStyleNameConstants() {
        return GWTConstantsMock.mockAllStringMethods(mock(StickieStyleNameConstants.class), StickieStyleNameConstants.class);
    }

    @Provides
    @Singleton
    public ButtonStyleNameConstants getButtonStyleNameConstants() {
        return GWTConstantsMock.mockAllStringMethods(mock(ButtonStyleNameConstants.class), ButtonStyleNameConstants.class);
    }

    @Provides
    @Singleton
    public PageStyleNameConstants getViewStyleNameConstants() {
        return GWTConstantsMock.mockAllStringMethods(mock(PageStyleNameConstants.class), PageStyleNameConstants.class);
    }

    @Provides
    @Singleton
    public TextEntryStyleNameConstants getTextEntryStyleNameConstants() {
        return GWTConstantsMock.mockAllStringMethods(mock(TextEntryStyleNameConstants.class), TextEntryStyleNameConstants.class);
    }

    @Provides
    @Singleton
    public ModeStyleNameConstants getModeStyleNameConstants() {
        return GWTConstantsMock.mockAllStringMethods(mock(ModeStyleNameConstants.class), ModeStyleNameConstants.class);
    }

    @Override
    public void configure(Binder binder) {

    }
}
