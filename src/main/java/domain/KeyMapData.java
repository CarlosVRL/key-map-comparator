package domain;

/**
 * A Key Map Data Collection.
 */
public class KeyMapData
{
    private String key;
    private String value;
    private Long rowNumber;

    public KeyMapData() {
        this.key = "";
        this.value = "";
        this.rowNumber = -1L;
    }

    public String getKey() {
        return key;
    }

    public KeyMapData setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public KeyMapData setValue(String value) {
        this.value = value;
        return this;
    }

    public Long getRowNumber() {
        return rowNumber;
    }

    public KeyMapData setRowNumber(Long rowNumber) {
        this.rowNumber = rowNumber;
        return this;
    }
}
