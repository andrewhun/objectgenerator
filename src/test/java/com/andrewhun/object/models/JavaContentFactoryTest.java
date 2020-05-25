package com.andrewhun.object.models;

import com.andrewhun.object.util.NamedConstants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaContentFactoryTest {

    private static final String METHOD = "Method";
    private static final String VARIABLE = "Variable";
    private static final String TEST_RESOURCE_PATH = "src/test/resources/";

    /*
    Since Travis CI is erroring out on one of the cases due to a remnant variable, I need to have this
    here as a catch-all.
    */
    @BeforeEach
    void setUp() {

        tearDown();
    }

    @AfterEach
    protected void tearDown() {

        JavaObject object = JavaObject.getCurrentObject();
        object.setName("");
        object.setVariables(new ArrayList<>());
        object.setMethods(new ArrayList<>());
    }

    @Test
    void testGeneratingStaticReadWriteStringVariable() throws Exception {

        Variable variable = Variable.createVariable(AccessRight.READ_WRITE, true, false,
                DataType.getDataType(NamedConstants.STRING), "staticReadWriteString");
        assertElementGeneration(variable, VARIABLE);
    }

    @Test void testGeneratingWriteOnlyDoubleVariable() throws Exception {

        Variable variable = Variable.createVariable(AccessRight.WRITE_ONLY, false, false,
                DataType.getDataType(NamedConstants.DOUBLE), "writeOnlyDouble");
        assertElementGeneration(variable, VARIABLE);
    }

    @Test void testGeneratingFinalReadOnlyIntegerVariable() throws Exception {

        Variable variable = Variable.createVariable(AccessRight.READ_ONLY, false, true,
                DataType.getDataType(NamedConstants.INTEGER), "finalReadOnlyInteger");
        assertElementGeneration(variable, VARIABLE);
    }

    @Test void testGeneratingPrivateBooleanVariable() throws Exception {

        Variable variable = Variable.createVariable(AccessRight.PRIVATE, false, false,
                DataType.getDataType(NamedConstants.BOOLEAN), "privateBoolean");
        assertElementGeneration(variable, VARIABLE);
    }

    @Test void testGeneratingPrivateStaticIntegerMethod() throws Exception {

        Method method = Method.createMethod(AccessModifier.PRIVATE, true,
                false, DataType.getDataType(NamedConstants.INTEGER), "privateStaticInteger");
        assertElementGeneration(method, METHOD);
    }

    @Test void testGeneratingPackagePrivateStringMethod() throws Exception {

        Method method = Method.createMethod(AccessModifier.PACKAGE_PRIVATE, false, false,
                DataType.getDataType(NamedConstants.STRING), "packagePrivateString");
        assertElementGeneration(method, METHOD);
    }

    @Test void testGeneratingPublicFinalVoidMethod() throws Exception {

        Method method = Method.createMethod(AccessModifier.PUBLIC, false, true,
                DataType.getDataType(NamedConstants.VOID), "publicFinalVoid");
        assertElementGeneration(method, METHOD);
    }

    private void assertElementGeneration(Element element, String elementType) {

        JavaObject object = createObject(element, elementType);

        String path = TEST_RESOURCE_PATH + object.getName();
        try {

            String expectedContent = new String(Files.readAllBytes(Paths.get(path)));
            assertEquals(expectedContent, new JavaContentFactory(object).generateContent());
        }
        catch (Exception e) {

            fail("An error has occurred: " + e.getMessage());
        }
    }

    private JavaObject createObject(Element element, String elementType) {

        JavaObject object = JavaObject.getCurrentObject();
        object.setName(generateObjectName(element.getName(), elementType));

        if(elementType.equals(METHOD)) {

            Method method = (Method) element;
            addMethodToObject(object, method);
        }
        else if(elementType.equals(VARIABLE)) {

            Variable variable = (Variable) element;
            addVariableToObject(object, variable);
        }

        return object;
    }

    private String generateObjectName(String elementName, String elementType) {

        String objectName = elementName;
        objectName = objectName.substring(0,1).toUpperCase() + objectName.substring(1);
        objectName += elementType;
        return objectName;
    }

    private void addMethodToObject(JavaObject object, Method method) {

        List<Method> methods = new ArrayList<>();
        methods.add(method);
        object.setMethods(methods);
    }

    private void addVariableToObject(JavaObject object, Variable variable) {

        List<Variable> variables = new ArrayList<>();
        variables.add(variable);
        object.setVariables(variables);
    }
}
