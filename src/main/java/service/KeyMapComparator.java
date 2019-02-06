package service;

import domain.KeyMapData;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class KeyMapComparator
{
    private Map<Long, KeyMapData> baselineData;
    private Map<Long, KeyMapData> externalData;

    public void setBaselineData(Map<Long, KeyMapData> baselineData)
    {
        this.baselineData = baselineData;
    }

    public Map<Long, KeyMapData> getBaselineData()
    {
        return this.baselineData;
    }

    public void setExternalData(Map<Long, KeyMapData> externalData)
    {
        this.externalData = externalData;
    }

    public Map<Long, KeyMapData> getExternalData()
    {
        return this.externalData;
    }

    public Map<KeyMapData, KeyMapData> findAllMatches()
    {
        Map<KeyMapData, KeyMapData> matches = new HashMap<KeyMapData, KeyMapData>();
        Set<Long> keys = baselineData.keySet();
        for (Long key : keys)
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
        report += "Baseline Row, Baseline Key, Baseline Value, External Row, External Key, External Value\n";
        for (KeyMapData baseline : keys)
        {
            KeyMapData external = matches.get(baseline);
            report += baseline.getRowNumber() + seperator + baseline.getKey() + seperator + baseline.getValue() + seperator +
                      external.getRowNumber() + seperator + external.getKey() + seperator + external.getValue() + "\n";
        }
        return report;
    }
}
