package eu.ydp.empiria.player.client.module.info;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.info.InfoModuleContentTokenizer.Token;

import java.util.List;

public class VariableInterpreter {

    @Inject
    private ContentFieldRegistry fieldRegistry;

    public String replaceAllTags(List<Token> tokensFromContent, int refItemIndex) {
        return replaceItemTags(tokensFromContent, refItemIndex);
    }

    private String replaceItemTags(List<Token> tokensFromContent, final int refItemIndex) {
        return Joiner.on("").join(transformTokens(tokensFromContent, refItemIndex));
    }

    private Iterable<String> transformTokens(List<Token> tokensFromContent, final int refItemIndex) {
        return Iterables.transform(tokensFromContent, new Function<Token, String>() {
            @Override
            public String apply(Token token) {
                if (token.isFieldInfo()) {
                    return getFieldInfoValue(refItemIndex, token);
                }
                return token.getName();
            }

        });
    }

    private String getFieldInfoValue(final int refItemIndex, Token token) {
        Optional<ContentFieldInfo> fieldInfo = fieldRegistry.getFieldInfo(token.getName());
        return fieldInfo.isPresent() ? fieldInfo.get().getValue(refItemIndex) : token.getName();
    }

}
