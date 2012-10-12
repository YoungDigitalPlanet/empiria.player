package eu.ydp.empiria.player.client.controller.extensions.internal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.bookmark.IBookmarkable;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.gwtutil.client.collections.ListCreator;
import eu.ydp.gwtutil.client.collections.StackMap;

public class BookmarkProcessorExtensionTest {
	
	BookmarkProcessorExtension bookmarkProcessor;
	String SELECTED = "SELECTED";
	String SELECTABLE = "SELECTABLE";
	
	@Before
	public void setUp(){
		bookmarkProcessor = spy(new BookmarkProcessorExtension());
		doNothing().when(bookmarkProcessor).initInjection();
		bookmarkProcessor.eventsBus = mock(EventsBus.class);
		bookmarkProcessor.styleNames = mock(StyleNameConstants.class);		
		doReturn(SELECTED).when(bookmarkProcessor.styleNames).QP_BOOKMARK_SELECTED();
		doReturn(SELECTABLE).when(bookmarkProcessor.styleNames).QP_BOOKMARK_SELECTABLE();		
	}
	
	@Test
	public void containsInteractionNotHasChildren(){
		IModule module = mock(IModule.class);
		assertThat(bookmarkProcessor.containsInteraction(module), is(false));
	}
	
	@Test
	public void containsInteractionHasChildren(){
		List<IModule> childList1 = ListCreator.create(mock(IModule.class)).add(mock(IModule.class)).add(mock(IInteractionModule.class)).build();
		HasChildren module = mock(HasChildren.class);
		when(module.getChildrenModules()).thenReturn(childList1);
		assertThat(bookmarkProcessor.containsInteraction(module), is(true));
	}

	@Test
	public void checkParentsNotExists(){
		List<IBookmarkable> bookmarks = ListCreator.create(mock(IBookmarkable.class)).add(mock(IBookmarkable.class)).build();
		doReturn(bookmarks).when(bookmarkProcessor).getModulesForCurrentItem();
		assertThat(bookmarkProcessor.parentRegistered(mock(IModule.class)), is(false));
	}

	interface BookmarkableHasChildren extends IBookmarkable, HasChildren{}
	
	@Test
	public void checkParentsExists(){
		List<BookmarkableHasChildren> bookmarks = ListCreator.create(mock(BookmarkableHasChildren.class)).add(mock(BookmarkableHasChildren.class)).build();
		doReturn(bookmarks).when(bookmarkProcessor).getModulesForCurrentItem();
		IModule module = mock(IModule.class);
		when(module.getParentModule()).thenReturn(bookmarks.get(1));
		assertThat(bookmarkProcessor.parentRegistered(module), is(true));
	}
	
	@Test
	public void removeChildren(){
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
	public void isChildOfTrue(){
		HasChildren parent = mock(HasChildren.class);
		IModule child = mock(IModule.class, RETURNS_DEEP_STUBS);
		when(child.getParentModule().getParentModule()).thenReturn(parent);
		assertThat(bookmarkProcessor.isChildOf(child, parent), is(true));
	}
	
	@Test
	public void isChildOfFalse(){
		HasChildren parent = mock(HasChildren.class);
		IModule child = mock(IModule.class, RETURNS_DEEP_STUBS);
		when(child.getParentModule().getParentModule()).thenReturn(parent);
		assertThat(bookmarkProcessor.isChildOf(child, mock(HasChildren.class)), is(false));
	}

	int BOOKMARKING_INDEX = 2;
	
	@Test
	public void bookmarkModuleDoBookmark(){
		StackMap<Integer, Integer> bookmarks = new StackMap<Integer, Integer>();
		IBookmarkable b2 = mock(IBookmarkable.class);
		bookmarkProcessor.bookmarking = true;
		bookmarkModule(b2, bookmarks, true);
		assertThat(bookmarks.getKeys(), contains(1));
		verify(b2).setBookmarkingStyleName(bookmarkProcessor.styleNames.QP_BOOKMARK_SELECTED() + "-" + BOOKMARKING_INDEX);
		verify(b2, never()).removeBookmarkingStyleName();
	}

	@Test
	public void bookmarkModuleUnbookmark(){
		StackMap<Integer, Integer> bookmarks = new StackMap<Integer, Integer>();
		bookmarks.put(1, BOOKMARKING_INDEX);
		IBookmarkable b2 = mock(IBookmarkable.class);
		bookmarkProcessor.bookmarking = true;
		bookmarkModule(b2, bookmarks, false);
		assertThat(bookmarks.getKeys().size(), is(0));
		verify(b2).setBookmarkingStyleName(bookmarkProcessor.styleNames.QP_BOOKMARK_SELECTABLE());
		verify(b2, never()).removeBookmarkingStyleName();
	}
	
	@Test
	public void bookmarkModuleClear(){
		StackMap<Integer, Integer> bookmarks = new StackMap<Integer, Integer>();
		bookmarks.put(1, BOOKMARKING_INDEX);
		IBookmarkable b2 = mock(IBookmarkable.class);
		bookmarkProcessor.clearing = true;
		bookmarkModule(b2, bookmarks, false);
		assertThat(bookmarks.getKeys().size(), is(0));
		verify(b2).removeBookmarkingStyleName();
		verify(b2, never()).setBookmarkingStyleName(any(String.class));
	}
	
	private void bookmarkModule(IBookmarkable b2, StackMap<Integer, Integer> bookmarks, boolean bkm){
		IBookmarkable b1 = mock(IBookmarkable.class);
		List<IBookmarkable> modules = ListCreator.create(b1).add(b2).build();
		bookmarkProcessor.bookmarks = ListCreator.create(bookmarks).build();
		bookmarkProcessor.modules = ListCreator.create(new ArrayList<List<IBookmarkable>>()).add(modules).build();
		bookmarkProcessor.currItemIndex = 0;
		bookmarkProcessor.bookmarkIndex = BOOKMARKING_INDEX;
		bookmarkProcessor.bookmarkModule(b2, bkm);
	}
	
	@Test
	public void setClearingMode(){
		doNothing().when(bookmarkProcessor).updateModules();
		bookmarkProcessor.bookmarking = true;
		bookmarkProcessor.clearing = false;
		bookmarkProcessor.setClearingMode(true);
		assertThat(bookmarkProcessor.bookmarking, is(false));
		assertThat(bookmarkProcessor.clearing, is(true));
		verify(bookmarkProcessor).updateModules();
	}
	
	@Test
	public void startBookmarking(){
		doNothing().when(bookmarkProcessor).updateModules();
		bookmarkProcessor.bookmarking = false;
		bookmarkProcessor.clearing = true;
		bookmarkProcessor.startBookmarking(BOOKMARKING_INDEX);
		assertThat(bookmarkProcessor.bookmarking, is(true));
		assertThat(bookmarkProcessor.clearing, is(false));
		assertThat(bookmarkProcessor.bookmarkIndex, is(BOOKMARKING_INDEX));
		verify(bookmarkProcessor).updateModules();
	}
	
	@Test
	public void stopBookmarking(){
		doNothing().when(bookmarkProcessor).updateModules();
		bookmarkProcessor.bookmarking = true;
		bookmarkProcessor.clearing = false;
		bookmarkProcessor.stopBookmarking();
		assertThat(bookmarkProcessor.bookmarking, is(false));
		assertThat(bookmarkProcessor.clearing, is(false));
		verify(bookmarkProcessor).updateModules();
	}

	@Test
	public void updateModulesBookmarking(){
		IBookmarkable b1 = mock(IBookmarkable.class);
		IBookmarkable b2 = mock(IBookmarkable.class);
		List<IBookmarkable> modules = ListCreator.create(b1).add(b2).build();
		bookmarkProcessor.modules.add(modules);
		bookmarkProcessor.bookmarks.add(new StackMap<Integer, Integer>());
		bookmarkProcessor.bookmarking = true;
		bookmarkProcessor.updateModules(0);
		verify(b1).setBookmarkingStyleName(SELECTABLE);
		verify(b1, never()).removeBookmarkingStyleName();
		verify(b2).setBookmarkingStyleName(SELECTABLE);
		verify(b2, never()).removeBookmarkingStyleName();
	}
	
	@Test
	public void updateModulesNobookmarking(){
		IBookmarkable b1 = mock(IBookmarkable.class);
		IBookmarkable b2 = mock(IBookmarkable.class);
		List<IBookmarkable> modules = ListCreator.create(b1).add(b2).build();
		bookmarkProcessor.modules.add(modules);
		bookmarkProcessor.bookmarks.add(new StackMap<Integer, Integer>());
		bookmarkProcessor.bookmarking = false;
		bookmarkProcessor.updateModules(0);
		verify(b1, never()).setBookmarkingStyleName(SELECTABLE);
		verify(b1).removeBookmarkingStyleName();
		verify(b2, never()).setBookmarkingStyleName(SELECTABLE);
		verify(b2).removeBookmarkingStyleName();
	}
	
}
