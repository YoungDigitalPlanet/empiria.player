package eu.ydp.empiria.player.client.module.textentry;

import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class DragContentControllerTest {

    private final DragContentController dragContentController = new DragContentController();

    @Test
    public void shouldReturnIdWhenIsImage() throws Exception {
        // given
        String itemId = "itemId";
        String content = "content";
        SourcelistItemValue item = new SourcelistItemValue(SourcelistItemType.IMAGE, content, itemId);

        // when
        String result = dragContentController.getTextFromItemAppropriateToType(item);

        // then
        assertThat(result).isEqualTo(itemId);
    }

    @Test
    public void shouldReturnContentWhenIsText() throws Exception {
        // given
        String itemId = "itemId";
        String content = "content";
        SourcelistItemValue item = new SourcelistItemValue(SourcelistItemType.TEXT, content, itemId);

        // when
        String result = dragContentController.getTextFromItemAppropriateToType(item);

        // then
        assertThat(result).isEqualTo(content);
    }

}
