package eu.ydp.empiria.player.client.module.connection;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;

import java.util.List;

public class ConnectionSurfaceStyleProvider {

    private final static String CONNECTION_LINE_PREFIX = "qp-connection-line";
    private final static String CONNECTION_LINE_CORRECT_PREFIX = "qp-connection-line-correct";
    private final static String CONNECTION_LINE_WRONG_PREFIX = "qp-connection-line-wrong";

    public List<String> getStylesForSurface(MultiplePairModuleConnectType type, int leftIndex, int rightIndex) {

        Preconditions.checkArgument(leftIndex >= 0 && rightIndex >= 0, "Index of item could not be negative");

        String normalStyle = getStyleForNormalSurface(leftIndex, rightIndex);
        List<String> styles = Lists.newArrayList(normalStyle);

        Optional<String> additionalStyle = getAdditionalStyle(type, leftIndex, rightIndex);
        if (additionalStyle.isPresent()) {
            styles.add(additionalStyle.get());
        }
        return styles;
    }

    private Optional<String> getAdditionalStyle(MultiplePairModuleConnectType type, int leftIndex, int rightIndex) {
        Optional<String> additionalStyle = Optional.absent();

        if (type == MultiplePairModuleConnectType.CORRECT) {
            additionalStyle = Optional.of(getStyleForCorrectSurface(leftIndex, rightIndex));
        } else if (type == MultiplePairModuleConnectType.WRONG) {
            additionalStyle = Optional.of(getStyleForWrongSurface(leftIndex, rightIndex));
        }
        return additionalStyle;
    }

    private String getStyleForNormalSurface(int leftIndex, int rightIndex) {
        return CONNECTION_LINE_PREFIX + getIndexesSufix(leftIndex, rightIndex);
    }

    private String getStyleForCorrectSurface(int leftIndex, int rightIndex) {
        return CONNECTION_LINE_CORRECT_PREFIX + getIndexesSufix(leftIndex, rightIndex);
    }

    private String getStyleForWrongSurface(int leftIndex, int rightIndex) {
        return CONNECTION_LINE_WRONG_PREFIX + getIndexesSufix(leftIndex, rightIndex);
    }

    private String getIndexesSufix(int leftIndex, int rightIndex) {
        return "-" + leftIndex + "-" + rightIndex;
    }
}
