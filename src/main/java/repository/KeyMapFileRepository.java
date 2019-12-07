package repository;

import domain.KeyMapData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import service.KeyMapComparator;

import java.io.*;
import java.util.*;

/**
 * A KeyMapFileRepository.
 */
public class KeyMapFileRepository {
    /**
     * Read key map data from CSV.
     *
     * @param file the file to read
     * @param keyHeader the key header
     * @param valueHeader the value header
     *
     * @return the keyMapData
     */
    public static Map<String, KeyMapData> getKeyMapData(
            File file, String keyHeader, String valueHeader
    ) {
        Map<String, KeyMapData> keyMapDataMap = new HashMap<>();
        try {
            Reader in = new FileReader(file);
            Iterable<CSVRecord> records =
                    CSVFormat
                            .RFC4180
                            .withHeader(keyHeader, valueHeader)
                            .withFirstRecordAsHeader()
                            .parse(in);

            Long recordCount = 0L;
            for (CSVRecord record : records) {
                recordCount++;
                String key = record.get(keyHeader);
                String value = record.get(valueHeader);

                KeyMapData keyMapData = new KeyMapData();
                keyMapData.setRowNumber(recordCount);
                keyMapData.setKey(key);
                keyMapData.setValue(value);

                keyMapDataMap.put(key, keyMapData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return keyMapDataMap;
    }

    public static byte[] getBytesForCsv(Map<KeyMapData, KeyMapData> data) {
        ByteArrayOutputStream res = new ByteArrayOutputStream();
        try (Writer w = new BufferedWriter(new OutputStreamWriter(res))) {
            CSVPrinter p = new CSVPrinter(w, CSVFormat.DEFAULT);
            p.printRecord(Arrays.asList(KeyMapComparator.REPORT_HEADER.split(",")));
            data.forEach((baseline, external) ->
                    printRecordContents(p, baseline, external)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res.toByteArray();
    }

    private static void printRecordContents(
            CSVPrinter p, KeyMapData baseline, KeyMapData external
    ) {
        try {
            p.printRecord(Arrays.asList(
                    baseline.getRowNumber(),
                    baseline.getKey(),
                    baseline.getValue(),
                    external.getRowNumber(),
                    external.getKey(),
                    external.getValue()
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
