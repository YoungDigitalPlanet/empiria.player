package eu.ydp.empiria.player.client.controller.extensions.internal.state;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.compressor.LzGwtWrapper;

import javax.inject.Singleton;

@Singleton
public class EmpiriaStateExportCreator {

    private final LzGwtWrapper lzGwtWrapper;

    @Inject
    public EmpiriaStateExportCreator(LzGwtWrapper lzGwtWrapper) {
        this.lzGwtWrapper = lzGwtWrapper;
    }

    public EmpiriaState create(String state) {
        String compressed = lzGwtWrapper.compress(state);
        return new EmpiriaState(EmpiriaStateType.LZ_GWT, compressed);
    }
}
