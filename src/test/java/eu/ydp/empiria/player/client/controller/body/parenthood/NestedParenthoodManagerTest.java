package eu.ydp.empiria.player.client.controller.body.parenthood;

import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.HasParent;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class NestedParenthoodManagerTest {

    private NestedParenthoodManager testObj = new NestedParenthoodManager();

    @Test
    public void shouldAddChildToParent() {
        // given
        HasParent child = mock(HasParent.class);
        HasChildren parent = mock(HasChildren.class);
        testObj.addParent(parent);
        testObj.addChildToParent(child, parent);

        // when
        List<HasParent> nestedChildren = testObj.getNestedChildren(parent);

        // then
        assertThat(nestedChildren.contains(child)).isTrue();
    }

    @Test
    public void shouldAddChildToGrandParent() {
        // given
        HasParent child = mock(HasParent.class);
        HasChildren parent = mock(HasChildren.class);
        HasChildren grandParent = mock(HasChildren.class);
        testObj.addParent(grandParent);
        testObj.addChildToParent(parent, grandParent);
        testObj.addParent(parent);
        testObj.addChildToParent(child, parent);

        // when
        List<HasParent> nestedChildren = testObj.getNestedChildren(grandParent);

        // then
        assertThat(nestedChildren.contains(child)).isTrue();
    }

    @Test
    public void shouldAddParentToChild() {
        // given
        HasParent child = mock(HasParent.class);
        HasChildren parent = mock(HasChildren.class);
        testObj.addParent(parent);
        testObj.addChildToParent(child, parent);

        // when
        List<HasChildren> nestedParents = testObj.getNestedParents(child);

        // then
        assertThat(nestedParents.contains(parent)).isTrue();
    }

    @Test
    public void shouldAddGrandParentToChild() {
        // given
        HasParent child = mock(HasParent.class);
        HasChildren parent = mock(HasChildren.class);
        HasChildren grandParent = mock(HasChildren.class);
        testObj.addParent(grandParent);
        testObj.addChildToParent(parent, grandParent);
        testObj.addParent(parent);
        testObj.addChildToParent(child, parent);

        // when
        List<HasChildren> nestedParents = testObj.getNestedParents(child);

        // then
        assertThat(nestedParents.contains(grandParent)).isTrue();
    }
}