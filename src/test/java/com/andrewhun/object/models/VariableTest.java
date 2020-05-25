package com.andrewhun.object.models;

import com.andrewhun.object.util.NamedConstants;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VariableTest {

    @Test void testGetVariablesFromDescription() throws Exception {

        List<String> descriptions = new ArrayList<>();
        descriptions.add("Read-Only Static Final String test1");
        descriptions.add("Private Integer test2");

        List<Variable> variables = Variable.generateVariablesFromDescription(descriptions);
        assertEquals(2, variables.size());

        Variable variable = variables.get(0);
        assertEquals(AccessRight.READ_ONLY, variable.getAccessRight());
        assertTrue(variable.getClassElement());
        assertTrue(variable.getConstant());
        assertEquals(NamedConstants.STRING, variable.getDataType().getName());
        assertEquals("test1", variable.getName());
    }
}
