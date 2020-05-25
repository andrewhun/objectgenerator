package com.andrewhun.object.models;

import java.util.ArrayList;
import java.util.List;

public class Method extends Element {

    private AccessModifier accessModifier;

    private Method() {}

    public static Method createMethod(AccessModifier accessModifier, Boolean classElement, Boolean constant,
                                      DataType dataType, String name) {

        Method method = new Method();
        method.setAccessModifier(accessModifier);
        method.setClassElement(classElement);
        method.setConstant(constant);
        method.setDataType(dataType);
        method.setName(name);

        assertMethodValues(method);
        return method;
    }

    public AccessModifier getAccessModifier() {
        return accessModifier;
    }

    public void setAccessModifier(AccessModifier accessModifier) {
        this.accessModifier = accessModifier;
    }

    public static List<Method> generateMethodsFromDescription(List<String> descriptions) throws Exception {

        List<Method> methods = new ArrayList<>();
        for(String description : descriptions) {

            Method method = (Method)populateElement(new Method(), description);
            for(AccessModifier modifier : AccessModifier.values()) {

                if(description.contains(modifier.toString())) {
                    method.setAccessModifier(modifier);
                    break;
                }
            }
            assertMethodValues(method);
            methods.add(method);
        }
        return methods;
    }

    protected String getAccessDescription() {

        return accessModifier.toString();
    }

    private static void assertMethodValues(Method method) {

        assert method.getAccessModifier() != null : "Access modifier is missing";
        assert method.getClassElement() != null : "Class method flag is missing";
        assert method.getConstant() != null : "Constant flag is missing";
        assert method.getDataType() != null : "Data type is missing";
        assert method.getName() != null : "Name is missing";
    }
}
