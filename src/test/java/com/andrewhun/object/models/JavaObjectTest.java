package com.andrewhun.object.models;

import com.andrewhun.object.util.NamedConstants;
import com.andrewhun.object.util.TestUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JavaObjectTest {

    @Test void testCreatingObject() throws Exception {

        JavaObject object = JavaObject.getCurrentObject();
        object.setName(TestUtil.TEST_NAME);
        object.create();

        // A metadata file should be generated
        File objectMetadata = TestUtil.getFile(object, NamedConstants.METADATA_FILE_TYPE);
        assertTrue(objectMetadata.exists());

        // The Java class itself should also be generated
        File javaClass = TestUtil.getFile(object,JavaObject.JAVA_EXTENSION);
        assertTrue(javaClass.exists());

        // The object should be available as a data type
        assertNotNull(DataType.getDataType(object.getName()));

        TestUtil.deleteObjectData(object);
    }

    @Test void testGetObject() throws Exception {

        createObject();
        assertObjectValues();

        File objectMetadata = TestUtil.getFile(JavaObject.getCurrentObject(), NamedConstants.METADATA_FILE_TYPE);
        assertTrue(objectMetadata.delete());

        File javaClass = TestUtil.getFile(JavaObject.getCurrentObject(), JavaObject.JAVA_EXTENSION);
        assertTrue(javaClass.delete());
    }

    private void createObject() throws Exception {

        JavaObject object = setUpObject();
        object.create();
    }

    private JavaObject setUpObject() throws Exception {

        List<Variable> variables = setUpVariables();
        List<Method> methods = setUpMethods();

        JavaObject object = JavaObject.getCurrentObject();
        object.setName(TestUtil.TEST_NAME);
        object.setVariables(variables);
        object.setMethods(methods);
        return object;
    }

    private List<Variable> setUpVariables() throws Exception {

        List<Variable> variables = new ArrayList<>();
        variables.add(Variable.createVariable(AccessRight.READ_WRITE, true,
                false, DataType.getDataType(NamedConstants.STRING), TestUtil.TEST_NAME));
        return variables;
    }

    private List<Method> setUpMethods() throws Exception {

        List<Method> methods = new ArrayList<>();
        methods.add(Method.createMethod(AccessModifier.PACKAGE_PRIVATE, false,
                true, DataType.getDataType(NamedConstants.INTEGER), TestUtil.TEST_NAME));
        return methods;
    }

    private void assertObjectValues() throws Exception {

        JavaObject retrievedObject = JavaObject.getObject(TestUtil.TEST_NAME);
        assertNotNull(retrievedObject);
        assertEquals(TestUtil.TEST_NAME, retrievedObject.getName());
        assertEquals(1, retrievedObject.getVariables().size());
        assertEquals(1, retrievedObject.getMethods().size());
    }
}