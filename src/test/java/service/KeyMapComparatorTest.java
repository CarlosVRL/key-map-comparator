package service;

import domain.KeyMapData;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.testng.Assert.*;

public class KeyMapComparatorTest
{
    private KeyMapComparator keyMapComparator;
    private Map<String, KeyMapData> baselineTestData;
    private Map<String, KeyMapData> externalTestData;
    private Map<KeyMapData, KeyMapData> matchesTestData;

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
        Map<String, KeyMapData> baselineData = keyMapComparator.getBaselineData();
        assertEquals(baselineData, baselineTestData);
    }

    @Test
    public void canReadExternalDataFromMap()
    {
        Map<String, KeyMapData> externalData = keyMapComparator.getExternalData();
        assertEquals(externalData, externalTestData);
    }

    @Test
    public void canFindMatches()
    {
        int EXPECTED_MATCHES_SIZE = 1;
        Map<KeyMapData, KeyMapData> matches = keyMapComparator.findAllMatches();
        Set<KeyMapData> keys = matches.keySet();
        for (KeyMapData key : keys)
        {
            KeyMapComparator.keyMapsMatch(key, matches.get(key));
        }
        assertEquals(keys.size(), EXPECTED_MATCHES_SIZE);
    }

    @Test
    public void canFindDifferences()
    {

    }

    @Test
    public void canFindBaselineDifferencesRelativeToExternal()
    {

    }

    @Test
    public void canFindExternalDifferencesRelativeToBaseline()
    {

    }

    private void initTestData()
    {
        baselineTestData = new HashMap<String, KeyMapData>();
        baselineTestData.put("1", new KeyMapData().setKey("1").setValue("a").setRowNumber(1L));
        baselineTestData.put("2", new KeyMapData().setKey("2").setValue("b").setRowNumber(2L));
        baselineTestData.put("3", new KeyMapData().setKey("3").setValue("c").setRowNumber(3L));

        keyMapComparator.setBaselineData(baselineTestData);

        externalTestData = new HashMap<String, KeyMapData>();
        externalTestData.put("1", new KeyMapData().setKey("1").setValue("a").setRowNumber(1L));
        externalTestData.put("2", new KeyMapData().setKey("2").setValue("B").setRowNumber(2L));
        externalTestData.put("4", new KeyMapData().setKey("4").setValue("e").setRowNumber(3L));

        keyMapComparator.setExternalData(externalTestData);

        matchesTestData = new HashMap<KeyMapData, KeyMapData>();
        matchesTestData.put(new KeyMapData().setKey("1").setValue("a").setRowNumber(1L),
                            new KeyMapData().setKey("1").setValue("a").setRowNumber(1L));
    }
}