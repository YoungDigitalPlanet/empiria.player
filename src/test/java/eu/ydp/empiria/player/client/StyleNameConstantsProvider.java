package eu.ydp.empiria.player.client;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickieStyleNameConstants;
import eu.ydp.empiria.player.client.controller.feedback.structure.Feedback;
import eu.ydp.empiria.player.client.controller.workmode.ModeStyleNameConstants;
import eu.ydp.empiria.player.client.module.button.ButtonStyleNameConstants;
import eu.ydp.empiria.player.client.module.colorfill.ColorfillStyleNameConstants;
import eu.ydp.empiria.player.client.module.connection.ConnectionStyleNameConstants;
import eu.ydp.empiria.player.client.module.drawing.DrawingStyleNameConstants;
import eu.ydp.empiria.player.client.module.feedback.FeedbackStyleNameConstants;
import eu.ydp.empiria.player.client.module.img.ImgStyleNameConstants;
import eu.ydp.empiria.player.client.module.inlinechoice.InlineChoiceStyleNameConstants;
import eu.ydp.empiria.player.client.module.labelling.LabellingStyleNameConstants;
import eu.ydp.empiria.player.client.module.media.MediaStyleNameConstants;
import eu.ydp.empiria.player.client.module.textentry.TextEntryStyleNameConstants;
import eu.ydp.empiria.player.client.resources.PageStyleNameConstants;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

import static org.mockito.Mockito.mock;


public class StyleNameConstantsProvider implements Module {

    @Provides
    @Singleton
    public StyleNameConstants getNameConstants() {
        return mock(StyleNameConstants.class);
    }


    @Provides
    @Singleton
    public ConnectionStyleNameConstants getConnectionStyleNameConstants() {
        return mock(ConnectionStyleNameConstants.class);
    }

    @Provides
    @Singleton
    public ColorfillStyleNameConstants getColorfillStyleNameConstants() {
        return mock(ColorfillStyleNameConstants.class);
    }

    @Provides
    @Singleton
    public ImgStyleNameConstants getImgStyleNameConstants() {
        return mock(ImgStyleNameConstants.class);
    }

    @Provides
    @Singleton
    public MediaStyleNameConstants getMediaStyleNameConstants() {
        return mock(MediaStyleNameConstants.class);
    }

    @Provides
    @Singleton
    public InlineChoiceStyleNameConstants getInlineChoiceStyleNameConstants() {
        return mock(InlineChoiceStyleNameConstants.class);
    }

    @Provides
    @Singleton
    public LabellingStyleNameConstants getLabellingNameConstants() {
        return mock(LabellingStyleNameConstants.class);
    }

    @Provides
    @Singleton
    public DrawingStyleNameConstants getDrawingStyleNameConstants() {
        return mock(DrawingStyleNameConstants.class);
    }

    @Provides
    public StickieStyleNameConstants getStickieStyleNameConstants() {
        return mock(StickieStyleNameConstants.class);
    }

    @Provides
    @Singleton
    public ButtonStyleNameConstants getButtonStyleNameConstants() {
        return mock(ButtonStyleNameConstants.class);
    }

    @Provides
    @Singleton
    public PageStyleNameConstants getViewStyleNameConstants() {
        return mock(PageStyleNameConstants.class);
    }

    @Provides
    @Singleton
    public TextEntryStyleNameConstants getTextEntryStyleNameConstants() {
        return mock(TextEntryStyleNameConstants.class);
    }

    @Provides
    @Singleton
    public ModeStyleNameConstants getModeStyleNameConstants() {
        return mock(ModeStyleNameConstants.class);
    }

    @Provides
    @Singleton
    public FeedbackStyleNameConstants getFeedbackStyleNameConstants() {
        return mock(FeedbackStyleNameConstants.class);
    }

    @Override
    public void configure(Binder binder) {

    }
}
