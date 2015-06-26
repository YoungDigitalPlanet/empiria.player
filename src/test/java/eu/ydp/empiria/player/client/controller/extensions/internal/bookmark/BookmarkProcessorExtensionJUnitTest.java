package eu.ydp.empiria.player.client.controller.extensions.internal.bookmark;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.BookmarkProcessorExtension.Mode;
import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.bookmark.IBookmarkable;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.gwtutil.client.collections.ListCreator;
import eu.ydp.gwtutil.client.collections.StackMap;

public class BookmarkProcessorExtensionJUnitTest {

	BookmarkProcessorExtension bookmarkProcessor;
	String SELECTED = "SELECTED";
	String SELECTABLE = "SELECTABLE";

	@Before
	public void setUp() {
		bookmarkProcessor = spy(new BookmarkProcessorExtension());
		doNothing().when(bookmarkProcessor).initInjection();
		doNothing().when(bookmarkProcessor).editBookmark(any(IBookmarkable.class));
		bookmarkProcessor.eventsBus = mock(EventsBus.class);
		bookmarkProcessor.styleNames = mock(StyleNameConstants.class);
		bookmarkProcessor.dataSourceSupplier = mock(DataSourceDataSupplier.class);
		when(bookmarkProcessor.dataSourceSupplier.getItemTitle(any(Integer.class))).thenReturn("Mock title 1");
		doReturn(SELECTED).when(bookmarkProcessor.styleNames).QP_BOOKMARK_SELECTED();
		doReturn(SELECTABLE).when(bookmarkProcessor.styleNames).QP_BOOKMARK_SELECTABLE();
	}

	@Test
	public void containsInteractionNotHasChildren() {
		IModule module = mock(IModule.class);
		assertThat(bookmarkProcessor.containsInteraction(module), is(false));
	}

	@Test
	public void containsInteractionHasChildren() {
		List<IModule> childList1 = ListCreator.create(mock(IModule.class)).add(mock(IModule.class)).add(mock(IInteractionModule.class)).build();
		HasChildren module = mock(HasChildren.class);
		when(module.getChildrenModules()).thenReturn(childList1);
		assertThat(bookmarkProcessor.containsInteraction(module), is(true));
	}

	@Test
	public void checkParentsNotExists() {
		List<IBookmarkable> bookmarks = ListCreator.create(mock(IBookmarkable.class)).add(mock(IBookmarkable.class)).build();
		doReturn(bookmarks).when(bookmarkProcessor).getModulesForCurrentItem();
		assertThat(bookmarkProcessor.parentRegistered(mock(IModule.class)), is(false));
	}

	interface BookmarkableHasChildren extends IBookmarkable, HasChildren {
	}

	@Test
	public void checkParentsExists() {
		List<BookmarkableHasChildren> bookmarks = ListCreator.create(mock(BookmarkableHasChildren.class)).add(mock(BookmarkableHasChildren.class)).build();
		doReturn(bookmarks).when(bookmarkProcessor).getModulesForCurrentItem();
		IModule module = mock(IModule.class);
		when(module.getParentModule()).thenReturn(bookmarks.get(1));
		assertThat(bookmarkProcessor.parentRegistered(module), is(true));
	}

	@Test
	public void removeChildren() {
		IBookmarkable b1 = mock(IBookmarkable.class);
		IBookmarkable b2 = mock(IBookmarkable.class);
		IBookmarkable b3 = mock(IBookmarkable.class);
		List<IBookmarkable> bookmarks = ListCreator.create(b1).add(b2).add(b3).build();
		doReturn(bookmarks).when(bookmarkProcessor).getModulesForCurrentItem();
		HasChildren parentModule = mock(HasChildren.class);
		when(parentModule.getChildrenModules()).thenReturn(ListCreator.create(new ArrayList<IModule>()).add(b2).build());
		doReturn(false).when(bookmarkProcessor).isChildOf(any(IModule.class), any(IModule.class));
		doReturn(true).when(bookmarkProcessor).isChildOf(b2, parentModule);
		bookmarkProcessor.removeChildren(parentModule);
		assertThat(bookmarks, contains(b1, b3));
	}

	@Test
	public void isChildOfTrue() {
		HasChildren parent = mock(HasChildren.class);
		IModule child = mock(IModule.class, RETURNS_DEEP_STUBS);
		when(child.getParentModule().getParentModule()).thenReturn(parent);
		assertThat(bookmarkProcessor.isChildOf(child, parent), is(true));
	}

	@Test
	public void isChildOfFalse() {
		HasChildren parent = mock(HasChildren.class);
		IModule child = mock(IModule.class, RETURNS_DEEP_STUBS);
		when(child.getParentModule().getParentModule()).thenReturn(parent);
		assertThat(bookmarkProcessor.isChildOf(child, mock(HasChildren.class)), is(false));
	}

	int BOOKMARKING_INDEX = 2;

	@Test
	public void bookmarkModuleDoBookmark() {
		StackMap<Integer, BookmarkProperties> bookmarks = new StackMap<Integer, BookmarkProperties>();
		IBookmarkable b2 = mock(IBookmarkable.class);
		bookmarkProcessor.mode = Mode.BOOKMARKING;
		bookmarkModule(b2, bookmarks, true);
		assertThat(bookmarks.getKeys(), contains(1));
		verify(b2).setBookmarkingStyleName(bookmarkProcessor.styleNames.QP_BOOKMARK_SELECTED() + "-" + BOOKMARKING_INDEX);
		verify(b2, never()).removeBookmarkingStyleName();
	}

	@Test
	public void bookmarkModuleUnbookmark() {
		StackMap<Integer, BookmarkProperties> bookmarks = new StackMap<Integer, BookmarkProperties>();
		bookmarks.put(1, new BookmarkProperties(BOOKMARKING_INDEX, ""));
		IBookmarkable b2 = mock(IBookmarkable.class);
		bookmarkProcessor.mode = Mode.BOOKMARKING;
		bookmarkModule(b2, bookmarks, false);
		assertThat(bookmarks.getKeys().size(), is(0));
		verify(b2).setBookmarkingStyleName(bookmarkProcessor.styleNames.QP_BOOKMARK_SELECTABLE());
		verify(b2, never()).removeBookmarkingStyleName();
	}

	@Test
	public void bookmarkModuleClear() {
		StackMap<Integer, BookmarkProperties> bookmarks = new StackMap<Integer, BookmarkProperties>();
		bookmarks.put(1, new BookmarkProperties(BOOKMARKING_INDEX, ""));
		IBookmarkable b2 = mock(IBookmarkable.class);
		bookmarkProcessor.mode = Mode.CLEARING;
		bookmarkModule(b2, bookmarks, false);
		assertThat(bookmarks.getKeys().size(), is(0));
		verify(b2).removeBookmarkingStyleName();
		verify(b2, never()).setBookmarkingStyleName(any(String.class));
	}

	private void bookmarkModule(IBookmarkable b2, StackMap<Integer, BookmarkProperties> bookmarks, boolean bkm) {
		IBookmarkable b1 = mock(IBookmarkable.class);
		List<IBookmarkable> modules = ListCreator.create(b1).add(b2).build();
		bookmarkProcessor.bookmarks = ListCreator.create(bookmarks).build();
		bookmarkProcessor.modules = ListCreator.create(new ArrayList<List<IBookmarkable>>()).add(modules).build();
		bookmarkProcessor.currItemIndex = 0;
		bookmarkProcessor.bookmarkIndex = BOOKMARKING_INDEX;
		bookmarkProcessor.bookmarkModule(b2, bkm);
	}

	@Test
	public void setClearingMode() {
		doNothing().when(bookmarkProcessor).updateNotBookmarkedModules();
		bookmarkProcessor.mode = Mode.BOOKMARKING;
		bookmarkProcessor.setClearingMode(true);
		assertThat(bookmarkProcessor.mode, is(Mode.CLEARING));
		verify(bookmarkProcessor).updateNotBookmarkedModules();
	}

	@Test
	public void setEditingMode() {
		doNothing().when(bookmarkProcessor).updateNotBookmarkedModules();
		bookmarkProcessor.mode = Mode.BOOKMARKING;
		bookmarkProcessor.setEditingMode(true);
		assertThat(bookmarkProcessor.mode, is(Mode.EDITING));
		bookmarkProcessor.setEditingMode(false);
		assertThat(bookmarkProcessor.mode, is(Mode.IDLE));
		verify(bookmarkProcessor, times(2)).updateNotBookmarkedModules();
	}

	@Test
	public void startBookmarking() {
		doNothing().when(bookmarkProcessor).updateNotBookmarkedModules();
		bookmarkProcessor.mode = Mode.CLEARING;
		bookmarkProcessor.startBookmarking(BOOKMARKING_INDEX);
		assertThat(bookmarkProcessor.mode, is(Mode.BOOKMARKING));
		assertThat(bookmarkProcessor.bookmarkIndex, is(BOOKMARKING_INDEX));
		verify(bookmarkProcessor).updateNotBookmarkedModules();
	}

	@Test
	public void stopBookmarking() {
		doNothing().when(bookmarkProcessor).updateNotBookmarkedModules();
		bookmarkProcessor.mode = Mode.BOOKMARKING;
		bookmarkProcessor.stopBookmarking();
		assertThat(bookmarkProcessor.mode, is(Mode.IDLE));
		verify(bookmarkProcessor).updateNotBookmarkedModules();
	}

	@Test
	public void updateModulesBookmarking() {
		IBookmarkable b1 = mock(IBookmarkable.class);
		IBookmarkable b2 = mock(IBookmarkable.class);
		List<IBookmarkable> modules = ListCreator.create(b1).add(b2).build();
		bookmarkProcessor.modules.add(modules);
		bookmarkProcessor.bookmarks.add(new StackMap<Integer, BookmarkProperties>());
		bookmarkProcessor.mode = Mode.BOOKMARKING;
		bookmarkProcessor.updateModules(0, false);
		verify(b1).setBookmarkingStyleName(SELECTABLE);
		verify(b1, never()).removeBookmarkingStyleName();
		verify(b2).setBookmarkingStyleName(SELECTABLE);
		verify(b2, never()).removeBookmarkingStyleName();
	}

	@Test
	public void updateModulesNobookmarking() {
		IBookmarkable b1 = mock(IBookmarkable.class);
		IBookmarkable b2 = mock(IBookmarkable.class);
		List<IBookmarkable> modules = ListCreator.create(b1).add(b2).build();
		bookmarkProcessor.modules.add(modules);
		bookmarkProcessor.bookmarks.add(new StackMap<Integer, BookmarkProperties>());
		bookmarkProcessor.mode = Mode.IDLE;
		bookmarkProcessor.updateModules(0, false);
		verify(b1, never()).setBookmarkingStyleName(SELECTABLE);
		verify(b1).removeBookmarkingStyleName();
		verify(b2, never()).setBookmarkingStyleName(SELECTABLE);
		verify(b2).removeBookmarkingStyleName();
	}

	@Test
	public void clearAll() {
		bookmarkProcessor.bookmarks.add((StackMap<Integer, BookmarkProperties>) eu.ydp.gwtutil.client.collections.MapCreator
				.create(new StackMap<Integer, BookmarkProperties>()).put(1, mock(BookmarkProperties.class)).put(1, mock(BookmarkProperties.class))
				.put(3, mock(BookmarkProperties.class)).build());
		bookmarkProcessor.bookmarks.add((StackMap<Integer, BookmarkProperties>) eu.ydp.gwtutil.client.collections.MapCreator
				.create(new StackMap<Integer, BookmarkProperties>()).put(1, mock(BookmarkProperties.class)).put(1, mock(BookmarkProperties.class))
				.put(5, mock(BookmarkProperties.class)).build());
		doNothing().when(bookmarkProcessor).updateNotBookmarkedModules();
		doNothing().when(bookmarkProcessor).resetMode();
		bookmarkProcessor.currItemIndex = 0;
		bookmarkProcessor.clearAll();
		assertThat(bookmarkProcessor.bookmarks.get(0).size(), is(0));
		assertThat(bookmarkProcessor.bookmarks.get(1).size(), is(2));
		verify(bookmarkProcessor).updateNotBookmarkedModules();
		verify(bookmarkProcessor).resetMode();

	}

	@Test
	public void testParseExternalBookarksNull() {
		// given
		List<StackMap<Integer, BookmarkProperties>> bookmarks = bookmarkProcessor.bookmarks;
		doReturn(null).when(bookmarkProcessor).getExternalBookmarks();
		// when
		bookmarkProcessor.parseExternalBookarks();
		// then
		assertThat(bookmarks, notNullValue());
		assertThat(bookmarks, empty());

	}

}
