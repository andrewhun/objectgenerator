package com.andrewhun.object.models;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class DataTypeTest {

    private final int NUMBER_OF_BASE_TYPES = 5;


    @Test void testBasicDataTypes() throws Exception {

        // The basic data types should always be available in the file
       List<DataType> types = DataType.getAllDataTypes();
        assertTrue(types.size() >= NUMBER_OF_BASE_TYPES);
    }

    @Test void testAddType() throws Exception {

        final String TEST_NAME = "test";
        DataType.addDataType(TEST_NAME);

        // The test type should be added
        List<DataType> types = new ArrayList<>(DataType.getAllDataTypes());
        int numberOfTypesWithTest = types.size();
        assertTrue(numberOfTypesWithTest >= NUMBER_OF_BASE_TYPES + 1);

        DataType.removeDataType(TEST_NAME);

        // Verify that the test type was removed
        List<DataType> productionTypes = DataType.getAllDataTypes();
        assertEquals(numberOfTypesWithTest-1, productionTypes.size());

        for(DataType type : productionTypes) {

            assertNotEquals(TEST_NAME, type.getName());
        }
    }
}
