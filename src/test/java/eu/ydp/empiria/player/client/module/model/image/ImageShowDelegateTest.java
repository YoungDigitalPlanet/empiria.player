package eu.ydp.empiria.player.client.module.model.image;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.gwtutil.client.util.geom.Size;
import eu.ydp.gwtutil.client.util.geom.WidgetSize;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class ImageShowDelegateTest {

    private Widget widget;
    private Style style;

    @Before
    public void setUp() throws Exception {
        widget = mock(Widget.class, Mockito.RETURNS_DEEP_STUBS);
        style = mock(Style.class);
        when(widget.getElement()
                .getStyle()).thenReturn(style);
        when(widget.asWidget()).thenReturn(widget);
    }

    @Test
    public void shouldSetImagenWidget() {
        // given
        String path = "PATH";
        int width = 100;
        int height = 222;
        Size size = new Size(width, height);
        ShowImageDTO dto = new ShowImageDTO(path, size);
        ImageShowDelegate showImage = new ImageShowDelegate(dto);

        // when
        showImage.showOnWidget(widget);

        // then
        String expectedWidth = width + WidgetSize.DIMENSIONS_UNIT;
        String expectedHeight = height + WidgetSize.DIMENSIONS_UNIT;

        verify(widget).setWidth(expectedWidth);
        verify(widget).setHeight(expectedHeight);
        verify(style).setBackgroundImage("url(PATH)");
    }

}
