package repository;

import domain.KeyMapData;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URL;
import java.util.Map;

public class KeyMapFileRepositoryTest
{
    @Test
    public void canReadDataFromFile()
    {
        KeyMapFileRepository keyMapFileRepository = new KeyMapFileRepository();
        URL url = this.getClass().getResource("/key-map-baseline.csv");
        File file = new File(url.getFile());

        Map<String, KeyMapData> data  = keyMapFileRepository.getKeyMapData(
                file, "Baseline Key", "Baseline Value"
        );

        Assert.assertNotNull(data);
        Assert.assertEquals(data.size(), 3);
    }
}
