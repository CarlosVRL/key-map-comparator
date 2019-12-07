package service;

import domain.KeyMapData;

import java.util.*;

public class KeyMapComparator
{
    //
    // Members
    //
    private Map<String, KeyMapData> baselineData;
    private Map<String, KeyMapData> externalData;

    private Long matchesCount;
    private Long differencesCount;

    //
    // Factory
    //
    public static KeyMapComparator withBaselineAndExternalData(
            Map<String, KeyMapData> baselineData,
            Map<String, KeyMapData> externalData
    ) {
        KeyMapComparator res = new KeyMapComparator();
        res.setBaselineData(baselineData);
        res.setExternalData(externalData);
        res.setMatchesCount(0L);
        res.setDifferencesCount(0L);
        return res;
    }

    //
    // API
    //
    public void setBaselineData(Map<String, KeyMapData> baselineData)
    {
        this.baselineData = baselineData;
    }

    public Map<String, KeyMapData> getBaselineData()
    {
        return this.baselineData;
    }

    public void setExternalData(Map<String, KeyMapData> externalData)
    {
        this.externalData = externalData;
    }

    public Map<String, KeyMapData> getExternalData()
    {
        return this.externalData;
    }

    public Map<KeyMapData, KeyMapData> findAllMatches()
    {
        matchesCount = 0L;
        Map<KeyMapData, KeyMapData> matches = new HashMap<>();
        Set<String> keys = baselineData.keySet();
        for (String key : keys)
        {
            if (!externalData.containsKey(key)) { continue; }
            KeyMapData baselineValue = baselineData.get(key);
            KeyMapData externalValue = externalData.get(key);

            if (baselineValue == null || externalValue == null) { continue; }

            if (keyMapsMatch(baselineValue, externalValue)) {
                matchesCount++;
                matches.put(baselineValue, externalValue);
            }
        }
        return matches;
    }

    public Map<KeyMapData, KeyMapData> findAllDifferences()
    {
        differencesCount = 0L;
        Map<KeyMapData, KeyMapData> differences = new HashMap<>();
        Set<String> baselineKeys = baselineData.keySet();

        // Baseline to External Differences
        for (String key : baselineKeys) {
            KeyMapData baselineValue = baselineData.get(key);
            KeyMapData externalValue = externalData.get(key);

            // Baseline with no External match
            if (!externalData.containsKey(key)) {
                differencesCount++;
                differences.put(baselineValue, emptyKeyMapData());
                continue;
            }

            // Compare Baseline to External
            if (keyMapsMatch(baselineValue, externalValue)) { continue; }

            differencesCount++;
            differences.put(baselineValue, externalValue);
        }

        // External with no Baseline Matches
        Set<String> externalKeys = externalData.keySet();
        for (String key : externalKeys) {
            KeyMapData externalValue = externalData.get(key);
            if (!baselineData.containsKey(key)) {
                differencesCount++;
                differences.put(emptyKeyMapData(), externalValue);
            }
        }

        return differences;
    }

    public static boolean keyMapsMatch(KeyMapData a, KeyMapData b)
    {
        if (!a.getKey().equals(b.getKey())) { return false; }
        if (!a.getValue().equals(b.getValue())) { return false; }
        return true;
    }

    public String getReport(Map<KeyMapData, KeyMapData> matches)
    {
        String report = "";
        String separator = ", ";

        Set<KeyMapData> keys = matches.keySet();
        List<KeyMapData> sortedKeys = new ArrayList<>(keys);
        sortedKeys.sort(Comparator.comparing(KeyMapData::getRowNumber));

        report += "Baseline Row, Baseline Key, Baseline Value, External Row, External Key, External Value\n";
        for (KeyMapData baseline : sortedKeys)
        {
            KeyMapData external = matches.get(baseline);
            report += baseline.getRowNumber() + separator + baseline.getKey() + separator + baseline.getValue() + separator +
                      external.getRowNumber() + separator + external.getKey() + separator + external.getValue() + "\n";
        }
        return report;
    }

    public void printBaseline()
    {
        String report = "";
        String separator = ", ";
        Set<String> keys = baselineData.keySet();
        report += "Baseline Row, Baseline Key, Baseline Value";
        System.out.println(report);
        for (String key : keys)
        {
            KeyMapData data = baselineData.get(key);
            System.out.println(data.getRowNumber() + separator + data.getKey() + separator + data.getValue());
        }
        System.out.println();
    }

    public void printExternal()
    {
        String report = "";
        String separator = ", ";
        Set<String> keys = externalData.keySet();
        report += "Baseline Row, Baseline Key, Baseline Value";
        System.out.println(report);
        for (String key : keys)
        {
            KeyMapData data = externalData.get(key);
            System.out.println(data.getRowNumber() + separator + data.getKey() + separator + data.getValue());
        }
        System.out.println();
    }

    //
    // Accessors
    //
    public Long getMatchesCount() {
        return matchesCount;
    }

    public void setMatchesCount(Long matchesCount) {
        this.matchesCount = matchesCount;
    }

    public Long getDifferencesCount() {
        return differencesCount;
    }

    public void setDifferencesCount(Long differencesCount) {
        this.differencesCount = differencesCount;
    }

    //
    // Implementation
    //
    private KeyMapData emptyKeyMapData() {
        Long NO_ROW = 0L;
        String DASH = "-";
        return new KeyMapData().setKey(DASH).setValue(DASH).setRowNumber(NO_ROW);
    }
}
