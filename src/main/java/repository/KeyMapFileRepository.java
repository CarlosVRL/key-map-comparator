package repository;

import domain.KeyMapData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class KeyMapFileRepository
{

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

                keyMapDataMap.put(String.valueOf(recordCount), keyMapData);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return keyMapDataMap;
    }
}