package util;

import report.PatternReport;

import java.util.HashMap;

public class HashMapMerger extends HashMap<String, PatternReport> {
    @Override
    public PatternReport put(String key, PatternReport value) {
        System.out.println("merging: " + key + " -> " + value.getValue());
        if (!containsKey(key)) return super.put(key, value);
        System.out.println(" MERGED and not INSERTED");
        return super.put(key, get(key).merge(value));
    }
}
