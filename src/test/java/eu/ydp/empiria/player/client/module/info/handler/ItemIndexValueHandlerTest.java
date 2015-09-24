package eu.ydp.empiria.player.client.module.info.handler;

import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ItemIndexValueHandlerTest {

    private ItemIndexValueHandler testObj = new ItemIndexValueHandler();

    @Test
    public void shouldReturnPageNumber() {
        // given
        int itemIndex = 5;
        String pageNumber = "6";

        // when
        String result = testObj.getValue(mock(ContentFieldInfo.class), itemIndex);

        // then
        assertThat(result).isEqualTo(pageNumber);
    }
}