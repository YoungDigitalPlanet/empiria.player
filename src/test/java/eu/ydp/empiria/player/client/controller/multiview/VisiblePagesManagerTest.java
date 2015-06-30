package eu.ydp.empiria.player.client.controller.multiview;

import com.google.common.base.Predicate;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.FlowPanel;
import eu.ydp.gwtutil.client.collections.KeyValue;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.List;
import java.util.Set;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class VisiblePagesManagerTest {

    private class CustomPanelCache extends PanelCache {

        private Predicate<Integer> isAttachedPredicate = new Predicate<Integer>() {

            @Override
            public boolean apply(Integer pageNumber) {
                return pageNumber % 2 == 0;
            }
        };

        public void setIsAttachedPredicate(Predicate<Integer> isAttachedPredicate) {
            this.isAttachedPredicate = isAttachedPredicate;
        }

        public Predicate<Integer> getIsAttachedPredicate() {
            return isAttachedPredicate;
        }

        @Override
        protected KeyValue<FlowPanel, FlowPanel> getElement(Integer index) {
            return getValue(index);
        }

        private KeyValue<FlowPanel, FlowPanel> getValue(int pageNumber) {
            FlowPanel panel = mock(FlowPanel.class);
            doReturn(isAttachedPredicate.apply(pageNumber)).when(panel).isAttached();
            return new KeyValue<FlowPanel, FlowPanel>(panel, panel);
        }
    }

    @Spy
    CustomPanelCache panelCache = new CustomPanelCache();
    @InjectMocks
    VisiblePagesManager instance;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeClass
    public static void disarm() {
        GWTMockUtilities.disarm();
    }

    @AfterClass
    public static void rearm() {
        GWTMockUtilities.restore();
    }

    @Test
    public void getPagesToDetachNoDataInCache() {
        Set<Integer> pagesToDeatach = instance.getPagesToDetach(2);
        assertThat(pagesToDeatach).isEmpty();
    }

    @Test
    public void getPagesToDetachZeroVisiblePages() {
        for (int x = 0; x < 10; ++x) {
            panelCache.getOrCreateAndPut(x);
        }
        instance.setVisiblePageCount(0);
        int currentPage = 2;
        Set<Integer> pagesToDeatach = instance.getPagesToDetach(currentPage);
        assertThat(pagesToDeatach).isNotEmpty();
        assertThat(pagesToDeatach).hasSize(5);

    }

    @Test
    public void getPagesToDetachSingleVisiblePage() {
        instance.setVisiblePageCount(1);
        for (int x = 0; x < 10; ++x) {
            panelCache.getOrCreateAndPut(x);
        }
        int actualPage = 2;
        Set<Integer> pagesToDeatach = instance.getPagesToDetach(actualPage);
        assertThat(pagesToDeatach).isNotEmpty();
        assertThat(pagesToDeatach).doesNotContain(actualPage);
        assertThat(pagesToDeatach).hasSize(4);
        for (Integer pageIndex : pagesToDeatach) {
            assertThat(panelCache.getIsAttachedPredicate().apply(pageIndex)).isTrue();
        }
    }

    @Test
    public void getPagesToDetachMultipleVisiblePages() {
        instance.setVisiblePageCount(3);
        panelCache.setIsAttachedPredicate(new Predicate<Integer>() {
            @Override
            public boolean apply(Integer arg0) {
                return true;
            }
        });

        for (int x = 0; x < 10; ++x) {
            panelCache.getOrCreateAndPut(x);
        }
        int actualPage = 2;
        Set<Integer> pagesToDeatach = instance.getPagesToDetach(actualPage);
        assertThat(pagesToDeatach).isNotEmpty();
        assertThat(pagesToDeatach).doesNotContain(actualPage - 1, actualPage, actualPage + 1);
        assertThat(pagesToDeatach).hasSize(7);
        for (Integer pageIndex : pagesToDeatach) {
            assertThat(panelCache.getIsAttachedPredicate().apply(pageIndex)).isTrue();
        }
    }

    @Test
    public void getPagesToAttacheEmptyCache() {
        List<Integer> toAttache = instance.getPagesToAttache(2);
        assertThat(toAttache).isEmpty();
    }

    @Test
    public void getPagesToAttacheSingleVisiblePage() {
        instance.setVisiblePageCount(1);
        panelCache.setIsAttachedPredicate(new Predicate<Integer>() {

            @Override
            public boolean apply(Integer arg0) {
                return false;
            }
        });
        for (int x = 0; x < 10; ++x) {
            panelCache.getOrCreateAndPut(x);
        }
        int actualPage = 2;
        List<Integer> toAttache = instance.getPagesToAttache(actualPage);
        assertThat(toAttache).isNotEmpty();
        assertThat(toAttache).hasSize(1);
        assertThat(toAttache).contains(actualPage);
    }

    @Test
    public void getPagesToAttacheMultipleVisiblePage() {
        instance.setVisiblePageCount(3);
        panelCache.setIsAttachedPredicate(new Predicate<Integer>() {

            @Override
            public boolean apply(Integer arg0) {
                return false;
            }
        });

        for (int x = 0; x < 10; ++x) {
            panelCache.getOrCreateAndPut(x);
        }
        int actualPage = 2;
        List<Integer> toAttache = instance.getPagesToAttache(actualPage);
        assertThat(toAttache).isNotEmpty();
        assertThat(toAttache).hasSize(3);
        assertThat(toAttache).contains(actualPage - 1, actualPage, actualPage + 1);
    }

    @Test
    public void getPagesToAttacheThreeVisiblePage_SecondPageIsAttached() {
        instance.setVisiblePageCount(3);
        for (int x = 0; x < 10; ++x) {
            panelCache.getOrCreateAndPut(x);
        }
        int actualPage = 2;
        List<Integer> toAttache = instance.getPagesToAttache(actualPage);
        assertThat(toAttache).isNotEmpty();
        assertThat(toAttache).hasSize(2);
        assertThat(toAttache).contains(actualPage - 1, actualPage + 1);
    }

    @Test
    public void getPagesToAttacheFiveVisiblePage_SecondAndFourthPageAreAttached() {
        instance.setVisiblePageCount(3);
        for (int x = 0; x < 10; ++x) {
            panelCache.getOrCreateAndPut(x);
        }
        int actualPage = 2;
        List<Integer> toAttache = instance.getPagesToAttache(actualPage);
        assertThat(toAttache).isNotEmpty();
        assertThat(toAttache).hasSize(2);
        assertThat(toAttache).contains(actualPage - 1, actualPage + 1);
    }

}
