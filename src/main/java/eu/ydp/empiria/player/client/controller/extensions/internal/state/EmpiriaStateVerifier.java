package eu.ydp.empiria.player.client.controller.extensions.internal.state;

import com.google.common.base.Strings;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EmpiriaStateVerifier {

    private final LessonIdentifierProvider lessonIdentifierProvider;

    @Inject
    public EmpiriaStateVerifier(LessonIdentifierProvider lessonIdentifierProvider) {
        this.lessonIdentifierProvider = lessonIdentifierProvider;
    }

    public EmpiriaState verifyState(EmpiriaState empiriaState) {
        String savedIdentifier = empiriaState.getLessonIdentifier();

        if (Strings.isNullOrEmpty(savedIdentifier)) {
            return empiriaState;
        }

        String lessonIdentifier = lessonIdentifierProvider.getLessonIdentifier();
        boolean identifiersMatches = savedIdentifier.equals(lessonIdentifier);
        if (identifiersMatches) {
            return empiriaState;
        }

        return new EmpiriaState(EmpiriaStateType.UNKNOWN, "", lessonIdentifier);
    }
}
