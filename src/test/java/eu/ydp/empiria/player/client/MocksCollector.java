package eu.ydp.empiria.player.client;

import org.mockito.internal.listeners.CollectCreatedMocks;
import org.mockito.internal.progress.MockingProgress;
import org.mockito.internal.progress.ThreadSafeMockingProgress;

import java.util.LinkedList;
import java.util.List;

public class MocksCollector {
    private final List<Object> createdMocks;

    public MocksCollector() {
        createdMocks = new LinkedList<Object>();
        final MockingProgress progress = new ThreadSafeMockingProgress();
        progress.setListener(new CollectCreatedMocks(createdMocks));
    }

    public Object[] getMocks() {
        return createdMocks.toArray();
    }
}
