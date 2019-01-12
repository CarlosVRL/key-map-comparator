package service;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class KeyMapComparatorTest {

    @Test
    public void canInstantiateObject()
    {
        KeyMapComparator keyMapComparator = new KeyMapComparator();
        assertNotNull(keyMapComparator);
    }

}