package com.andrewhun.object.models;

import com.andrewhun.object.util.NamedConstants;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MethodTest {

    @Test void getMethodsFromDescription() throws Exception {

        List<String> descriptions = new ArrayList<>();
        descriptions.add("Package-private Static Final String test1");
        descriptions.add("Private Integer test2");

        List<Method> methods = Method.generateMethodsFromDescription(descriptions);
        assertEquals(2, methods.size());

        Method method = methods.get(0);
        assertEquals(AccessModifier.PACKAGE_PRIVATE, method.getAccessModifier());
        assertTrue(method.getClassElement());
        assertTrue(method.getConstant());
        assertEquals(NamedConstants.STRING, method.getDataType().getName());
        assertEquals("test1", method.getName());
    }
}
