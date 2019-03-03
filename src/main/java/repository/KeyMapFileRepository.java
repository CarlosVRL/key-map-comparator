package repository;

import domain.KeyMapData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * A KeyMapFileRepository.
 */
public class KeyMapFileRepository
{
    /**
     * Read key map data from CSV.
     *
     * @param file the file to read
     * @param keyHeader the key header
     * @param valueHeader the value header
     *
     * @return the keyMapData
     */
    public Map<String, KeyMapData> getKeyMapData(File file, String keyHeader, String valueHeader)
    {
        Map<String, KeyMapData> keyMapDataMap = new HashMap<String, KeyMapData>();;
        try {
            Reader in = new FileReader(file);
            Iterable<CSVRecord> records =
                    CSVFormat.RFC4180.withHeader(
                            keyHeader,
                            valueHeader
                    ).withFirstRecordAsHeader().parse(in);

            Long recordCount = 0L;
            for (CSVRecord record : records)
            {
                recordCount++;
                String key = record.get(keyHeader);
                String value = record.get(valueHeader);

                KeyMapData keyMapData = new KeyMapData();
                keyMapData.setRowNumber(recordCount);
                keyMapData.setKey(key);
                keyMapData.setValue(value);

                keyMapDataMap.put(key, keyMapData);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return keyMapDataMap;
    }
}
