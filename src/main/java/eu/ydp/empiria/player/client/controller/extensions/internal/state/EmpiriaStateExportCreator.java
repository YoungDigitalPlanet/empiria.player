package eu.ydp.empiria.player.client.controller.extensions.internal.state;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.compressor.LzGwtWrapper;
import eu.ydp.empiria.player.client.controller.data.AssessmentDataSourceManager;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Singleton;

@Singleton
public class EmpiriaStateExportCreator {

    private final LzGwtWrapper lzGwtWrapper;
    private final LessonIdentifierProvider lessonIdentifierProvider;

    @Inject
    public EmpiriaStateExportCreator(LzGwtWrapper lzGwtWrapper, LessonIdentifierProvider lessonIdentifierProvider) {
        this.lzGwtWrapper = lzGwtWrapper;
        this.lessonIdentifierProvider = lessonIdentifierProvider;
    }

    public EmpiriaState create(String state) {
        String identifier = lessonIdentifierProvider.getLessonIdentifier();
        String compressed = lzGwtWrapper.compress(state);
        return new EmpiriaState(EmpiriaStateType.LZ_GWT, compressed, identifier);
    }
}
