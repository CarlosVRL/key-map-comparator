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

    private static final String DEFAULT_ID_A = "ID_A";
    private static final String DEFAULT_ID_B = "ID_B";
    private static final String DEFAULT_ID_C = "ID_C";
    private static final String DEFAULT_ID_D = "ID_D";

    private static final String DEFAULT_VAL_A = "VAL_A";
    private static final String DEFAULT_VAL_B = "VAL_B";
    private static final String DEFAULT_VAL_C = "VAL_C";
    private static final String DEFAULT_VAL_E = "VAL_E";

    @BeforeMethod
    public void init() {
        keyMapComparator = new KeyMapComparator();
        simpleTestData();
    }

    @Test
    public void canInstantiateObject() {
        assertNotNull(keyMapComparator);
    }

    @Test
    public void canReadBaselineDataFromMap() {
        Map<String, KeyMapData> baselineData = keyMapComparator.getBaselineData();
        assertEquals(baselineData, baselineTestData);
    }

    @Test
    public void canReadExternalDataFromMap() {
        Map<String, KeyMapData> externalData = keyMapComparator.getExternalData();
        assertEquals(externalData, externalTestData);
    }

    @Test
    public void canFindMatches() {
        int EXPECTED_MATCHES_SIZE = 1;
        Map<KeyMapData, KeyMapData> matches = keyMapComparator.findAllMatches();
        Set<KeyMapData> keys = matches.keySet();
        for (KeyMapData key : keys) {
            KeyMapComparator.keyMapsMatch(key, matches.get(key));
        }
        assertEquals(keys.size(), EXPECTED_MATCHES_SIZE);
    }

    @Test
    public void canFindDifferences() {
        int EXPECTED_DIFFERENCES_SIZE = 3;
        Map<KeyMapData, KeyMapData> differences = keyMapComparator.findAllDifferences();
        Set<KeyMapData> keys = differences.keySet();
        assertEquals(keys.size(), EXPECTED_DIFFERENCES_SIZE);
    }

    @Test
    public void canPrintFormattedReport() {
        keyMapComparator.printBaseline();
        keyMapComparator.printExternal();

        Map<KeyMapData, KeyMapData> matches = keyMapComparator.findAllMatches();
        String matchesReport = keyMapComparator.getReport(matches);
        System.out.println(matchesReport);

        Map<KeyMapData, KeyMapData> differences = keyMapComparator.findAllDifferences();
        String differencesReport = keyMapComparator.getReport(differences);
        System.out.println(differencesReport);
    }

    private void simpleTestData() {
        baselineTestData = new HashMap<>();
        baselineTestData.put(DEFAULT_ID_A, new KeyMapData().setKey(DEFAULT_ID_A).setValue(DEFAULT_VAL_A).setRowNumber(1L));
        baselineTestData.put(DEFAULT_ID_B, new KeyMapData().setKey(DEFAULT_ID_B).setValue(DEFAULT_VAL_B).setRowNumber(2L));
        baselineTestData.put(DEFAULT_ID_C, new KeyMapData().setKey(DEFAULT_ID_C).setValue(DEFAULT_VAL_C).setRowNumber(3L));

        keyMapComparator.setBaselineData(baselineTestData);

        externalTestData = new HashMap<>();
        externalTestData.put(DEFAULT_ID_A, new KeyMapData().setKey(DEFAULT_ID_A).setValue(DEFAULT_VAL_A).setRowNumber(1L));
        externalTestData.put(DEFAULT_ID_B, new KeyMapData().setKey(DEFAULT_ID_B).setValue(DEFAULT_VAL_C).setRowNumber(2L));
        externalTestData.put(DEFAULT_ID_D, new KeyMapData().setKey(DEFAULT_ID_D).setValue(DEFAULT_VAL_E).setRowNumber(3L));

        keyMapComparator.setExternalData(externalTestData);

        matchesTestData = new HashMap<>();
        matchesTestData.put(new KeyMapData().setKey(DEFAULT_ID_A).setValue(DEFAULT_VAL_A).setRowNumber(1L),
                            new KeyMapData().setKey(DEFAULT_ID_A).setValue(DEFAULT_VAL_A).setRowNumber(1L));
    }
}
