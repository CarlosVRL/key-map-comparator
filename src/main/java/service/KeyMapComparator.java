package service;

import java.util.Map;

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
}
