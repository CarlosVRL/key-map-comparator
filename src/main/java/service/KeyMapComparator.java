package service;

import domain.KeyMapData;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class KeyMapComparator
{
    private Map<String, KeyMapData> baselineData;
    private Map<String, KeyMapData> externalData;

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

    public static boolean keyMapsMatch(KeyMapData a, KeyMapData b)
    {
        if (!a.getKey().equals(b.getKey())) { return false; }
        if (!a.getValue().equals(b.getValue())) { return false; }
        return true;
    }

    public String getReport(Map<KeyMapData, KeyMapData> matches)
    {
        String report = "";
        String seperator = ", ";
        Set<KeyMapData> keys = matches.keySet();
        report += "Baseline Key, Baseline Row, Baseline Value, External Key, External Row, External Value\n";
        for (KeyMapData baseline : keys)
        {
            KeyMapData external = matches.get(baseline);
            report += baseline.getKey() + seperator + baseline.getRowNumber() + seperator + baseline.getValue() + seperator +
                      external.getKey() + seperator + external.getRowNumber() + seperator + external.getValue() + "\n";
        }
        return report;
    }
}
