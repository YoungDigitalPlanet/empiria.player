package eu.ydp.empiria.player.client.module.dictionary.external.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Passwords {
    private final Map<String, List<String>> passwordsByLetter;
    private final Map<String, Integer> baseIndexes;

    public Passwords(Map<String, List<String>> passwordsByLetter, Map<String, Integer> baseIndexes) {
        this.passwordsByLetter = passwordsByLetter;
        this.baseIndexes = baseIndexes;
    }

    public Map<String, Integer> getBaseIndexes() {
        return baseIndexes;
    }

    public Set<String> getFirstLetters(){
        return passwordsByLetter.keySet();
    }

    public List<String> getPasswordsByLetter(String firstLetter){
        return passwordsByLetter.get(firstLetter);
    }
}
