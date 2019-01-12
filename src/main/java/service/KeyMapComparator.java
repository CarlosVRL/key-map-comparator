package service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class KeyMapComparator
{
    private Map<String, String> baselineData;
    private Map<String, String> externalData;

    public void setBaselineData(Map<String, String> baselineData)
    {
        this.baselineData = baselineData;
    }

    public Map<String, String> getBaselineData()
    {
        return this.baselineData;
    }

    public void setExternalData(Map<String, String> externalData)
    {
        this.externalData = externalData;
    }

    public Map<String, String> getExternalData()
    {
        return this.externalData;
    }

    public Map<String, String> findAllMatches()
    {
        Map<String, String> matches = new HashMap<String, String>();
        Set<String> keys = baselineData.keySet();
        for (String key : keys)
        {
            if (!externalData.containsKey(key)) { continue; }
            String baselineValue = baselineData.get(key);
            String externalValue = externalData.get(key);

            if (baselineValue == null || externalValue == null) { continue; }

            if (baselineValue.equals(externalValue))
            {
                matches.put(key, key);
            }
        }
        return matches;
    }
}
