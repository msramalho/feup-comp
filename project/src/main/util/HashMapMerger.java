package util;

import report.PatternReport;

import java.util.HashMap;
import java.util.Map;

/**
 * Maps a pattern's name to its PatternReport.
 * When adding a new PatternReport (through the put method), merges the new report with the previously stored one (if any).
 */
public class HashMapMerger extends HashMap<String, PatternReport> {
    @Override
    public PatternReport put(String key, PatternReport value) {
        if (!containsKey(key)) return super.put(key, value);
        return super.put(key, get(key).merge(value));
    }

    @Override
    public void putAll(Map<? extends String, ? extends PatternReport> m) {
        for (Entry<? extends String, ? extends PatternReport> entry : m.entrySet()){
            put(entry.getKey(), entry.getValue());
        }
    }
}
