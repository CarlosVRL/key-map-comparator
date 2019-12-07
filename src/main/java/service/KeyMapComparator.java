package service;

import domain.KeyMapData;

import java.util.*;

public class KeyMapComparator
{
    private Map<String, KeyMapData> baselineData;
    private Map<String, KeyMapData> externalData;

    public static KeyMapComparator withBaselineAndExternalData(
            Map<String, KeyMapData> baselineData,
            Map<String, KeyMapData> externalData
    ) {
        KeyMapComparator res = new KeyMapComparator();
        res.setBaselineData(baselineData);
        res.setExternalData(externalData);
        return res;
    }

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
        Map<KeyMapData, KeyMapData> matches = new HashMap<KeyMapData, KeyMapData>();
        Set<String> keys = baselineData.keySet();
        for (String key : keys)
        {
            if (!externalData.containsKey(key)) { continue; }
            KeyMapData baselineValue = baselineData.get(key);
            KeyMapData externalValue = externalData.get(key);

            if (baselineValue == null || externalValue == null) { continue; }

            if (keyMapsMatch(baselineValue, externalValue))
            {
                matches.put(baselineValue, externalValue);
            }
        }
        return matches;
    }

    public Map<KeyMapData, KeyMapData> findAllDifferences()
    {
        Map<KeyMapData, KeyMapData> differences = new HashMap<KeyMapData, KeyMapData>();
        Set<String> baselineKeys = baselineData.keySet();

        // Baseline to External Differences
        for (String key : baselineKeys)
        {
            KeyMapData baselineValue = baselineData.get(key);
            KeyMapData externalValue = externalData.get(key);

            // Baseline with no External match
            if (!externalData.containsKey(key))
            {
                differences.put(baselineValue, new KeyMapData().setKey("-").setValue("-").setRowNumber(0L));
                continue;
            }

            // Baseline does not match External
            if (keyMapsMatch(baselineValue, externalValue)) { continue; }

            differences.put(baselineValue, externalValue);
        }

        // External with no Baseline Matches
        Set<String> externalKeys = externalData.keySet();
        for (String key : externalKeys)
        {
            KeyMapData externalValue = externalData.get(key);
            if (!baselineData.containsKey(key))
            {
                differences.put(new KeyMapData().setKey("-").setValue("-").setRowNumber(0L), externalValue);
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
}
