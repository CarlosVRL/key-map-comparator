package service;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

public class KeyMapComparatorTest
{
    private KeyMapComparator keyMapComparator;
    private Map<String, String> baselineTestData;
    private Map<String, String> externalTestData;

    @BeforeMethod
    public void initTest()
    {
        keyMapComparator = new KeyMapComparator();
        initTestData();
    }

    @Test
    public void canInstantiateObject()
    {
        assertNotNull(keyMapComparator);
    }

    @Test
    public void canReadBaselineDataFromMap()
    {
        keyMapComparator.setBaselineData(baselineTestData);
        Map<String, String> baselineData = keyMapComparator.getBaselineData();
        assertEquals(baselineData, baselineTestData);
    }

    @Test
    public void canReadExternalDataFromMap()
    {
        keyMapComparator.setExternalData(baselineTestData);
        Map<String, String> externalData = keyMapComparator.getExternalData();
        assertEquals(externalData, baselineTestData);
    }

    private void initTestData()
    {
        baselineTestData = new HashMap<String, String>();
        baselineTestData.put("1", "a");
        baselineTestData.put("2", "b");
        baselineTestData.put("3", "c");
        baselineTestData.put("1", "d");

        externalTestData = new HashMap<String, String>();
        externalTestData.put("1", "a");
        externalTestData.put("2", "B");
        externalTestData.put("4", "e");
    }
}