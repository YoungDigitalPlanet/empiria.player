package eu.ydp.empiria.player.client.module.connection.structure;

import eu.ydp.empiria.player.client.AbstractJAXBTestBase;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(JUnitParamsRunner.class)
public class MatchInteractionBeanJUnitTest extends AbstractJAXBTestBase<MatchInteractionBean> {

    private MatchInteractionBean bean;

    @Before
    public void setUp() {
        bean = createBeanFromXMLString(ConnectionModuleStructureMock.CONNECTION_XML);
    }

    @Test
    public void shouldReturnMatchInteraction() {

        assertThat(bean.getId(), is(equalTo("dummy1")));
        assertThat(bean.isShuffle(), is(equalTo(true)));
        assertThat(bean.getResponseIdentifier(), is(equalTo("CONNECTION_RESPONSE_1")));

        assertThat(bean.getSimpleMatchSets().size(), is(equalTo(2)));

        assertThat(bean.getSourceChoicesSet(), notNullValue());
        assertThat(bean.getTargetChoicesSet(), notNullValue());

        assertThat(bean.getSourceChoicesSet().get(0).getIdentifier(), is(equalTo("CONNECTION_RESPONSE_1_0")));
        assertThat(bean.getSourceChoicesSet().get(0).isFixed(), is(equalTo(false)));
        assertThat(bean.getSourceChoicesSet().get(0).getMatchMax(), is(equalTo(2)));
        assertThat(bean.getSourceChoicesSet().get(1).getIdentifier(), is(equalTo("CONNECTION_RESPONSE_1_3")));
    }

    @Test
    @Parameters({"0", "1"})
    public void testGetLeftIndex(int expectedIndex) {
        // given
        SimpleAssociableChoiceBean choiceBean = bean.getSourceChoicesSet().get(expectedIndex);

        // when
        int leftIndex = bean.getLeftItemIndex(choiceBean);

        // then
        assertEquals(expectedIndex, leftIndex);
    }

    @Test
    @Parameters({"0", "1"})
    public void testGetRightIndex(int expectedIndex) {
        // given
        SimpleAssociableChoiceBean choiceBean = bean.getTargetChoicesSet().get(expectedIndex);

        // when
        int rightIndex = bean.getRightItemIndex(choiceBean);

        // then
        assertEquals(expectedIndex, rightIndex);
    }

    @Test
    public void testGetLeftIndexOfNonexistingItem() {
        // given
        SimpleAssociableChoiceBean choiceBean = mock(SimpleAssociableChoiceBean.class);

        // when
        int leftIndex = bean.getLeftItemIndex(choiceBean);

        // then
        assertEquals(-1, leftIndex);
    }

    @Test
    public void testGetRightIndexOfNonexistingItem() {
        // given
        SimpleAssociableChoiceBean choiceBean = mock(SimpleAssociableChoiceBean.class);

        // when
        int rightIndex = bean.getRightItemIndex(choiceBean);

        // then
        assertEquals(-1, rightIndex);
    }

    @Test
    public void testIsLeftItem() {
        // given
        SimpleAssociableChoiceBean choiceBean = bean.getSourceChoicesSet().get(0);

        // when
        boolean isLeft = bean.isLeftItem(choiceBean);

        // then
        assertTrue(isLeft);
    }

    @Test
    public void testIsNotLeftItem() {
        // given
        SimpleAssociableChoiceBean choiceBean = bean.getTargetChoicesSet().get(0);

        // when
        boolean isLeft = bean.isLeftItem(choiceBean);

        // then
        assertFalse(isLeft);
    }

}
