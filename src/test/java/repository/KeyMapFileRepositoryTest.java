package repository;

import domain.KeyMapData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import service.KeyMapComparator;

import java.io.File;
import java.net.URL;
import java.util.Map;

public class KeyMapFileRepositoryTest
{
    private KeyMapFileRepository keyMapFileRepository;
    private File baseline;
    private File external;

    private String KEY_FIELD = "Baseline Key";
    private String VALUE_FIELD = "Baseline Value";

    private KeyMapComparator keyMapComparator;

    @BeforeMethod
    public void init()
    {
        keyMapComparator = new KeyMapComparator();
        keyMapFileRepository = new KeyMapFileRepository();

        URL baselineUrl = this.getClass().getResource("/key-map-baseline.csv");
        baseline = new File(baselineUrl.getFile());

        URL externalUrl = this.getClass().getResource("/key-map-external.csv");
        external = new File(externalUrl.getFile());
    }

    @Test
    public void canReadDataFromFile()
    {
        Map<String, KeyMapData> data  = keyMapFileRepository.getKeyMapData(
                baseline, KEY_FIELD, VALUE_FIELD
        );
        Assert.assertNotNull(data);
        Assert.assertEquals(data.size(), 3);
    }

    @Test
    public void considersSameFileEqual()
    {
        Map<String, KeyMapData> data1  = keyMapFileRepository.getKeyMapData(
                baseline, KEY_FIELD, VALUE_FIELD
        );
        Map<String, KeyMapData> data2  = keyMapFileRepository.getKeyMapData(
                baseline, KEY_FIELD, VALUE_FIELD
        );

        keyMapComparator.setBaselineData(data1);
        keyMapComparator.setExternalData(data2);

        Map<KeyMapData, KeyMapData> kmd = keyMapComparator.findAllDifferences();
        Assert.assertEquals(kmd.size(), 0);
    }

    @Test
    public void considersDifferentFilesNotEqual()
    {
        Map<String, KeyMapData> data1  = keyMapFileRepository.getKeyMapData(
                baseline, KEY_FIELD, VALUE_FIELD
        );
        Map<String, KeyMapData> data2  = keyMapFileRepository.getKeyMapData(
                external, KEY_FIELD, VALUE_FIELD
        );

        keyMapComparator.setBaselineData(data1);
        keyMapComparator.setExternalData(data2);

        Map<KeyMapData, KeyMapData> kmd = keyMapComparator.findAllDifferences();
        Assert.assertNotEquals(kmd.size(), 0);

        System.out.println(keyMapComparator.getReport(kmd));
    }
}
